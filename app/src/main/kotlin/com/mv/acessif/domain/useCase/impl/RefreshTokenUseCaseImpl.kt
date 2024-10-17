package com.mv.acessif.domain.useCase.impl

import com.mv.acessif.domain.AccessToken
import com.mv.acessif.domain.User
import com.mv.acessif.domain.repository.AuthRepository
import com.mv.acessif.domain.repository.SharedPreferencesRepository
import com.mv.acessif.domain.repository.UserRepository
import com.mv.acessif.domain.returnModel.DataError
import com.mv.acessif.domain.returnModel.Result
import com.mv.acessif.domain.useCase.RefreshTokenUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RefreshTokenUseCaseImpl
    @Inject
    constructor(
        private val authRepository: AuthRepository,
        private val userRepository: UserRepository,
        private val sharedPreferencesRepository: SharedPreferencesRepository,
    ) : RefreshTokenUseCase {
        override suspend fun execute(): Result<User, DataError> {
            return withContext(Dispatchers.IO) {
                when (val refreshTokenResult = sharedPreferencesRepository.getRefreshToken()) {
                    is Result.Success -> {
                        if (refreshTokenResult.data.isEmpty()) {
                            Result.Error(DataError.Local.EMPTY_RESULT)
                        } else {
                            handleRefreshToken(refreshTokenResult.data)
                        }
                    }

                    is Result.Error -> {
                        Result.Error(refreshTokenResult.error)
                    }
                }
            }
        }

        private suspend fun handleRefreshToken(refreshToken: String): Result<User, DataError> {
            return when (
                val result = authRepository.refreshToken(refreshToken)
            ) {
                is Result.Success -> {
                    handleNetworkSuccess(result)
                }

                is Result.Error -> {
                    Result.Error(result.error)
                }
            }
        }

        private suspend fun handleNetworkSuccess(result: Result.Success<AccessToken, DataError.Network>): Result<User, DataError> {
            return when (
                val localResult =
                    sharedPreferencesRepository.saveAccessToken(
                        result.data.accessToken,
                    )
            ) {
                is Result.Success -> {
                    getUser()
                }

                is Result.Error -> {
                    Result.Error(localResult.error)
                }
            }
        }

        private suspend fun getUser(): Result<User, DataError> {
            return when (val result = userRepository.getUser()) {
                is Result.Success -> {
                    Result.Success(result.data)
                }

                is Result.Error -> {
                    Result.Error(result.error)
                }
            }
        }
    }
