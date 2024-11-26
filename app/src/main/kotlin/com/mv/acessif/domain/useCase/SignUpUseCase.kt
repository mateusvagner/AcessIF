package com.mv.acessif.domain.useCase

import com.mv.acessif.domain.SignUp
import com.mv.acessif.domain.User
import com.mv.acessif.domain.result.DataError
import com.mv.acessif.domain.result.Result

interface SignUpUseCase {
    suspend fun execute(signUp: SignUp): Result<User, DataError>
}
