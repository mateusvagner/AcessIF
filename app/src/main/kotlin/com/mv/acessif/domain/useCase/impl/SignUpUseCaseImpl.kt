package com.mv.acessif.domain.useCase.impl

import com.mv.acessif.domain.AuthToken
import com.mv.acessif.domain.SignUp
import com.mv.acessif.domain.repository.AuthRepository
import com.mv.acessif.domain.repository.SharedPreferencesRepository
import com.mv.acessif.domain.returnModel.DataError
import com.mv.acessif.domain.returnModel.Result
import com.mv.acessif.domain.useCase.SignUpUseCase
import javax.inject.Inject

class SignUpUseCaseImpl
    @Inject
    constructor(
        private val authRepository: AuthRepository,
        private val sharedPreferencesRepository: SharedPreferencesRepository,
    ) : SignUpUseCase {
        override suspend fun execute(signUp: SignUp): Result<Unit, DataError> {
            return when (val result = authRepository.signUp(signUp)) {
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
                        result.data.accessToken,
                        result.data.refreshToken,
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
