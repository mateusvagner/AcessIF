package com.mv.acessif.data.repository

import com.mv.acessif.data.local.SharedPreferencesManager
import com.mv.acessif.data.mapper.ErrorMapper
import com.mv.acessif.domain.repository.SharedPreferencesRepository
import com.mv.acessif.domain.returnModel.DataError
import com.mv.acessif.domain.returnModel.Result
import javax.inject.Inject

class SharedPreferencesRepositoryImpl
    @Inject
    constructor(
        private val sharedPreferencesManager: SharedPreferencesManager,
    ) : SharedPreferencesRepository {
        override fun saveAccessToken(acessToken: String): Result<Unit, DataError.Local> {
            try {
                sharedPreferencesManager.saveAccessToken(acessToken)
                return Result.Success(Unit)
            } catch (e: Exception) {
                return Result.Error(
                    ErrorMapper.mapLocalExceptionToLocalDataError(e),
                )
            }
        }

        override fun saveRefreshToken(refreshToken: String): Result<Unit, DataError.Local> {
            try {
                sharedPreferencesManager.saveRefreshToken(refreshToken)
                return Result.Success(Unit)
            } catch (e: Exception) {
                return Result.Error(
                    ErrorMapper.mapLocalExceptionToLocalDataError(e),
                )
            }
        }

        override fun saveTokens(
            accessToken: String,
            refreshToken: String,
        ): Result<Unit, DataError.Local> {
            try {
                sharedPreferencesManager.saveAccessToken(accessToken)
                sharedPreferencesManager.saveRefreshToken(refreshToken)
                return Result.Success(Unit)
            } catch (e: Exception) {
                return Result.Error(
                    ErrorMapper.mapLocalExceptionToLocalDataError(e),
                )
            }
        }

        override fun getAccessToken(): Result<String, DataError.Local> {
            return try {
                val accessToken = sharedPreferencesManager.getAccessToken().orEmpty()

                if (accessToken.isEmpty()) {
                    return Result.Error(DataError.Local.EMPTY_RESULT)
                }

                Result.Success(accessToken)
            } catch (e: Exception) {
                Result.Error(
                    ErrorMapper.mapLocalExceptionToLocalDataError(e),
                )
            }
        }

        override fun getRefreshToken(): Result<String, DataError.Local> {
            return try {
                val refreshToken = sharedPreferencesManager.getRefreshToken().orEmpty()

                if (refreshToken.isEmpty()) {
                    return Result.Error(DataError.Local.EMPTY_RESULT)
                }

                Result.Success(refreshToken)
            } catch (e: Exception) {
                Result.Error(
                    ErrorMapper.mapLocalExceptionToLocalDataError(e),
                )
            }
        }

        override fun clearTokens(): Result<Unit, DataError.Local> {
            try {
                sharedPreferencesManager.clearAccessToken()
                sharedPreferencesManager.clearRefreshToken()
                return Result.Success(Unit)
            } catch (e: Exception) {
                return Result.Error(
                    ErrorMapper.mapLocalExceptionToLocalDataError(e),
                )
            }
        }
    }
