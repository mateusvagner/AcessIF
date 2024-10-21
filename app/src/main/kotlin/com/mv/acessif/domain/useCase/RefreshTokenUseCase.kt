package com.mv.acessif.domain.useCase

import com.mv.acessif.domain.User
import com.mv.acessif.domain.returnModel.DataError
import com.mv.acessif.domain.returnModel.Result

interface RefreshTokenUseCase {
    suspend fun execute(): Result<User, DataError>
}
