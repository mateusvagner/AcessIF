package com.mv.acessif.data.repository

import com.mv.acessif.data.local.SharedPreferencesManager
import com.mv.acessif.data.mapper.ErrorMapper
import com.mv.acessif.domain.repository.SharedPreferencesRepository
import com.mv.acessif.domain.returnModel.DataError
import com.mv.acessif.domain.returnModel.Result
import com.mv.acessif.util.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SharedPreferencesRepositoryImpl
    @Inject
    constructor(
        private val sharedPreferencesManager: SharedPreferencesManager,
        private val dispatcherProvider: DispatcherProvider,
    ) : SharedPreferencesRepository {
        override suspend fun saveAccessToken(acessToken: String): Result<Unit, DataError.Local> {
            return withContext(dispatcherProvider.io) {
                try {
                    sharedPreferencesManager.saveAccessToken(acessToken)
                    Result.Success(Unit)
                } catch (e: Exception) {
                    Result.Error(
                        ErrorMapper.mapLocalExceptionToLocalDataError(e),
                    )
                }
            }
        }

        override suspend fun saveRefreshToken(refreshToken: String): Result<Unit, DataError.Local> {
            return withContext(dispatcherProvider.io) {
                try {
                    sharedPreferencesManager.saveRefreshToken(refreshToken)
                    Result.Success(Unit)
                } catch (e: Exception) {
                    Result.Error(
                        ErrorMapper.mapLocalExceptionToLocalDataError(e),
                    )
                }
            }
        }

        override suspend fun saveTokens(
            accessToken: String,
            refreshToken: String,
        ): Result<Unit, DataError.Local> {
            return withContext(dispatcherProvider.io) {
                try {
                    sharedPreferencesManager.saveAccessToken(accessToken)
                    sharedPreferencesManager.saveRefreshToken(refreshToken)
                    Result.Success(Unit)
                } catch (e: Exception) {
                    Result.Error(
                        ErrorMapper.mapLocalExceptionToLocalDataError(e),
                    )
                }
            }
        }

        override suspend fun getAccessToken(): Result<String, DataError.Local> {
            return withContext(dispatcherProvider.io) {
                try {
                    val accessToken = sharedPreferencesManager.getAccessToken().orEmpty()

                    if (accessToken.isEmpty()) {
                        Result.Error(DataError.Local.EMPTY_RESULT)
                    } else {
                        Result.Success(accessToken)
                    }
                } catch (e: Exception) {
                    Result.Error(
                        ErrorMapper.mapLocalExceptionToLocalDataError(e),
                    )
                }
            }
        }

        override suspend fun getRefreshToken(): Result<String, DataError.Local> {
            return withContext(dispatcherProvider.io) {
                try {
                    val refreshToken = sharedPreferencesManager.getRefreshToken().orEmpty()

                    if (refreshToken.isEmpty()) {
                        Result.Error(DataError.Local.EMPTY_RESULT)
                    } else {
                        Result.Success(refreshToken)
                    }
                } catch (e: Exception) {
                    Result.Error(
                        ErrorMapper.mapLocalExceptionToLocalDataError(e),
                    )
                }
            }
        }

        override suspend fun clearTokens(): Result<Unit, DataError.Local> {
            return withContext(dispatcherProvider.io) {
                try {
                    sharedPreferencesManager.clearAccessToken()
                    sharedPreferencesManager.clearRefreshToken()
                    Result.Success(Unit)
                } catch (e: Exception) {
                    Result.Error(
                        ErrorMapper.mapLocalExceptionToLocalDataError(e),
                    )
                }
            }
        }
    }
