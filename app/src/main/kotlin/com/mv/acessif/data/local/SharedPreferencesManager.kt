package com.mv.acessif.data.local

interface SharedPreferencesManager {
    fun saveAccessToken(token: String)

    fun getAccessToken(): String?

    fun clearAccessToken()
}
