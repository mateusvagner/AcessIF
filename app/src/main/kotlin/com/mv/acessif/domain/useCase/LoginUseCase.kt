package com.mv.acessif.domain.useCase

import com.mv.acessif.domain.Login
import com.mv.acessif.domain.User
import com.mv.acessif.domain.returnModel.DataError
import com.mv.acessif.domain.returnModel.Result

interface LoginUseCase {
    suspend fun execute(login: Login): Result<User, DataError>
}
