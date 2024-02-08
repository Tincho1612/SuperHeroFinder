package com.example.superherofinder

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class UserManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()

    companion object {
        private const val PREFS_NAME = "UserPrefs"
        private const val KEY_USER = "userActual"
    }

    fun saveUser(user: UserDetails) {
        val userJson = gson.toJson(user)
        sharedPreferences.edit().putString(KEY_USER, userJson).apply()
    }

    fun getUser(): UserDetails? {
        val userJson = sharedPreferences.getString(KEY_USER, null)
        return if (userJson != null) {
            gson.fromJson(userJson, UserDetails::class.java)
        } else {
            null
        }
    }

    fun clearUser() {
        sharedPreferences.edit().remove(KEY_USER).apply()
    }
}