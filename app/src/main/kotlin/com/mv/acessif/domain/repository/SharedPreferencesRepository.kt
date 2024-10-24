package com.mv.acessif.domain.repository

import com.mv.acessif.domain.returnModel.DataError
import com.mv.acessif.domain.returnModel.Result

interface SharedPreferencesRepository {
    suspend fun saveAccessToken(acessToken: String): Result<Unit, DataError.Local>

    suspend fun saveRefreshToken(refreshToken: String): Result<Unit, DataError.Local>

    suspend fun saveTokens(
        accessToken: String,
        refreshToken: String,
    ): Result<Unit, DataError.Local>

    suspend fun getAccessToken(): Result<String, DataError.Local>

    suspend fun getRefreshToken(): Result<String, DataError.Local>

    suspend fun clearTokens(): Result<Unit, DataError.Local>
}
