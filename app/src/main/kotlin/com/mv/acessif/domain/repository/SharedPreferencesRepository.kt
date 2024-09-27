package com.mv.acessif.domain.repository

import com.mv.acessif.domain.returnModel.DataError
import com.mv.acessif.domain.returnModel.Result

interface SharedPreferencesRepository {
    fun saveAccessToken(acessToken: String): Result<Unit, DataError.Local>

    fun saveRefreshToken(refreshToken: String): Result<Unit, DataError.Local>

    fun saveTokens(
        accessToken: String,
        refreshToken: String,
    ): Result<Unit, DataError.Local>

    fun getAccessToken(): Result<String, DataError.Local>

    fun getRefreshToken(): Result<String, DataError.Local>

    fun clearTokens(): Result<Unit, DataError.Local>
}
