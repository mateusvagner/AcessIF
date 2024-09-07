package com.mv.acessif.domain.useCase.impl

import com.mv.acessif.data.local.SharedPreferencesManager
import com.mv.acessif.domain.AccessToken
import com.mv.acessif.domain.Login
import com.mv.acessif.domain.repository.AuthRepository
import com.mv.acessif.domain.returnModel.DataError
import com.mv.acessif.domain.returnModel.Result
import com.mv.acessif.domain.useCase.LoginUseCase
import javax.inject.Inject

class LoginUseCaseImpl
    @Inject
    constructor(
        private val authRepository: AuthRepository,
        private val sharedPreferencesManager: SharedPreferencesManager,
    ) : LoginUseCase {
        override suspend fun execute(login: Login): Result<AccessToken, DataError.Network> {
            when (val result = authRepository.login(login)) {
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
