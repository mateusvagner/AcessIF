package com.mv.acessif.domain.useCase

import com.mv.acessif.domain.AccessToken
import com.mv.acessif.domain.SignUp
import com.mv.acessif.domain.returnModel.DataError
import com.mv.acessif.domain.returnModel.Result

interface SignUpUseCase {
    suspend fun execute(signUp: SignUp): Result<AccessToken, DataError.Network>
}
