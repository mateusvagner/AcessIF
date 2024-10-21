package com.mv.acessif.data.local

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreferencesManagerImpl
    @Inject
    constructor(
        @ApplicationContext context: Context,
    ) :
    SharedPreferencesManager {
        private val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        companion object {
            private const val PREFS_NAME = "my_prefs"
            private const val ACCESS_TOKEN = "access_token"
            private const val REFRESH_TOKEN = "refresh_token"
        }

        override fun saveAccessToken(token: String) {
            println("\nLOGGUE saveAccessToken: $token")
            sharedPreferences.edit().putString(ACCESS_TOKEN, token).apply()
        }

        override fun getAccessToken(): String? {
            println("\nLOGGUE getAcessToken: ${sharedPreferences.getString(ACCESS_TOKEN, null)}")
            return sharedPreferences.getString(ACCESS_TOKEN, null)
        }

        override fun clearAccessToken() {
            sharedPreferences.edit().remove(ACCESS_TOKEN).apply()
        }

        override fun saveRefreshToken(token: String) {
            sharedPreferences.edit().putString(REFRESH_TOKEN, token).apply()
        }

        override fun getRefreshToken(): String? {
            return sharedPreferences.getString(REFRESH_TOKEN, null)
        }

        override fun clearRefreshToken() {
            sharedPreferences.edit().remove(REFRESH_TOKEN).apply()
        }
    }
