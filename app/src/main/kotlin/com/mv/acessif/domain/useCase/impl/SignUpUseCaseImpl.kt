package com.mv.acessif.domain.useCase.impl

import com.mv.acessif.data.local.SharedPreferencesManager
import com.mv.acessif.domain.AccessToken
import com.mv.acessif.domain.SignUp
import com.mv.acessif.domain.repository.AuthRepository
import com.mv.acessif.domain.returnModel.DataError
import com.mv.acessif.domain.returnModel.Result
import com.mv.acessif.domain.useCase.SignUpUseCase
import javax.inject.Inject

class SignUpUseCaseImpl
    @Inject
    constructor(
        private val authRepository: AuthRepository,
        private val sharedPreferencesManager: SharedPreferencesManager,
    ) : SignUpUseCase {
        override suspend fun execute(signUp: SignUp): Result<AccessToken, DataError.Network> {
            when (val result = authRepository.signUp(signUp)) {
                is Result.Success -> {
                    sharedPreferencesManager.saveAccessToken(result.data.accessToken)
                    return result
                }

                is Result.Error -> {
                    return result
                }
            }
        }
    }
