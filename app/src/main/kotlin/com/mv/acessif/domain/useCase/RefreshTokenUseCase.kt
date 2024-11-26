package com.mv.acessif.domain.useCase

import com.mv.acessif.domain.User
import com.mv.acessif.domain.result.DataError
import com.mv.acessif.domain.result.Result

interface RefreshTokenUseCase {
    suspend fun execute(): Result<User, DataError>
}
