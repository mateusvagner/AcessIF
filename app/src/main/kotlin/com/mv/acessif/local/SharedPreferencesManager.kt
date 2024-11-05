package com.mv.acessif.local

interface SharedPreferencesManager {
    fun saveAccessToken(token: String)

    fun getAccessToken(): String?

    fun clearAccessToken()

    fun saveRefreshToken(token: String)

    fun getRefreshToken(): String?

    fun clearRefreshToken()
}
