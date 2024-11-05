package com.mv.acessif.data.repository

import com.mv.acessif.domain.User
import com.mv.acessif.domain.result.DataError
import com.mv.acessif.domain.result.Result

interface UserRepository {
    suspend fun getUser(): Result<User, DataError>

    suspend fun logout(): Result<Unit, DataError>
}
