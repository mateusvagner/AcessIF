package com.mv.acessif.domain.useCase.impl

import com.mv.acessif.data.repository.AuthRepository
import com.mv.acessif.data.repository.SharedPreferencesRepository
import com.mv.acessif.data.repository.UserRepository
import com.mv.acessif.domain.AuthToken
import com.mv.acessif.domain.Login
import com.mv.acessif.domain.User
import com.mv.acessif.domain.result.DataError
import com.mv.acessif.domain.result.Result
import com.mv.acessif.domain.useCase.LoginUseCase
import javax.inject.Inject

class LoginUseCaseImpl
    @Inject
    constructor(
        private val authRepository: AuthRepository,
        private val userRepository: UserRepository,
        private val sharedPreferencesRepository: SharedPreferencesRepository,
    ) : LoginUseCase {
        override suspend fun execute(login: Login): Result<User, DataError> {
            return when (val result = authRepository.login(login)) {
                is Result.Success -> {
                    handleNetworkSuccess(result)
                }

                is Result.Error -> {
                    Result.Error(result.error)
                }
            }
        }

        private suspend fun handleNetworkSuccess(result: Result.Success<AuthToken, DataError.Network>): Result<User, DataError> {
            return when (
                val localResult =
                    sharedPreferencesRepository.saveTokens(
                        accessToken = result.data.accessToken,
                        refreshToken = result.data.refreshToken,
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
