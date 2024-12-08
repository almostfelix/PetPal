package com.smartdevices.petpal.tools

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {

    companion object {
        private const val PREF_NAME = "PetPalPreferences"
        private const val KEY_SAVE_METHOD = "save_method"
        private const val UUID = "uuid"
        private const val USER_NAME = "user_name"
        private const val USER_EMAIL = "user_email"
        private const val USER_PHOTO = "user_photo"
        private const val THEME = "theme"
        private const val DEfAULT_THEME = false
        private const val DEFAULT_NAME = ""
        private const val DEFAULT_EMAIL = ""
        private const val DEFAULT_PHOTO = ""
        private const val DEFAULT_UUID = "0"
        private const val DEFAULT_SAVE_METHOD = "NOT SET" // Default to "local"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    // Modified method to accept a callback
    fun setSaveMethod(method: String, callback: OnSaveMethodCompleteListener?) {
        sharedPreferences.edit().putString(KEY_SAVE_METHOD, method).apply()

        // Notify the listener that the operation is complete
        callback?.onSaveMethodComplete()
    }

    fun getSaveMethod(): String {
        return sharedPreferences.getString(KEY_SAVE_METHOD, DEFAULT_SAVE_METHOD) ?: DEFAULT_SAVE_METHOD
    }

    fun setUUID(uuid: String) {
        sharedPreferences.edit().putString(UUID, uuid).apply()
    }

    fun getUUID(): String {
        return sharedPreferences.getString(UUID, DEFAULT_UUID) ?: DEFAULT_UUID
    }

    fun setUserName(userName: String) {
        sharedPreferences.edit().putString(USER_NAME, userName).apply()
    }

    fun getUserName(): String {
        return sharedPreferences.getString(USER_NAME, DEFAULT_NAME) ?: DEFAULT_NAME
    }

    fun setUserEmail(userEmail: String) {
        sharedPreferences.edit().putString(USER_EMAIL, userEmail).apply()
    }

    fun getUserEmail(): String {
        return sharedPreferences.getString(USER_EMAIL, DEFAULT_EMAIL) ?: DEFAULT_EMAIL
    }

    fun setUserPhoto(userPhoto: String) {
        sharedPreferences.edit().putString(USER_PHOTO, userPhoto).apply()
    }

    fun getUserPhoto(): String {
        return sharedPreferences.getString(USER_PHOTO, DEFAULT_PHOTO) ?: DEFAULT_PHOTO
    }

    fun setTheme(isDarkMode: Boolean) {
        sharedPreferences.edit().putBoolean(THEME, isDarkMode).apply()
    }

    fun getTheme(): Boolean {
        return sharedPreferences.getBoolean(THEME, true) // Default is false (light mode)
    }
}
