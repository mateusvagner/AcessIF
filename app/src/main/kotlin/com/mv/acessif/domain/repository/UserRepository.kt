package com.mv.acessif.domain.repository

import com.mv.acessif.domain.User
import com.mv.acessif.domain.returnModel.DataError
import com.mv.acessif.domain.returnModel.Result

interface UserRepository {
    suspend fun getUser(): Result<User, DataError>

    suspend fun logout(): Result<Unit, DataError>
}
