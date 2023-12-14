package com.example.superherofinder

import android.content.Context
import androidx.core.content.edit

class TokenManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "MyAppPrefs"
        private const val KEY_TOKEN = "jwt_token"
    }

    fun saveToken(token: String) {
        sharedPreferences.edit {
            putString(KEY_TOKEN, token)
            apply()
            commit()
        }
    }

    fun getToken(): String? {
        return sharedPreferences.getString(KEY_TOKEN, null)
    }

    fun clearToken() {
        sharedPreferences.edit {
            remove(KEY_TOKEN)
            apply()
            commit()

        }
    }
}