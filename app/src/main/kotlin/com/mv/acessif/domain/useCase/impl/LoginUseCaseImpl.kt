package com.mv.acessif.domain.useCase.impl

import com.mv.acessif.domain.AuthToken
import com.mv.acessif.domain.Login
import com.mv.acessif.domain.repository.AuthRepository
import com.mv.acessif.domain.repository.SharedPreferencesRepository
import com.mv.acessif.domain.returnModel.DataError
import com.mv.acessif.domain.returnModel.Result
import com.mv.acessif.domain.useCase.LoginUseCase
import javax.inject.Inject

class LoginUseCaseImpl
    @Inject
    constructor(
        private val authRepository: AuthRepository,
        private val sharedPreferencesRepository: SharedPreferencesRepository,
    ) : LoginUseCase {
        override suspend fun execute(login: Login): Result<Unit, DataError> {
            return when (val result = authRepository.login(login)) {
                is Result.Success -> {
                    handleNetworkSuccess(result)
                }

                is Result.Error -> {
                    Result.Error(result.error)
                }
            }
        }

        private fun handleNetworkSuccess(result: Result.Success<AuthToken, DataError.Network>): Result<Unit, DataError> {
            return when (
                val localResult =
                    sharedPreferencesRepository.saveTokens(
                        accessToken = result.data.accessToken,
                        refreshToken = result.data.refreshToken,
                    )
            ) {
                is Result.Success -> {
                    Result.Success(Unit)
                }

                is Result.Error -> {
                    Result.Error(localResult.error)
                }
            }
        }
    }
