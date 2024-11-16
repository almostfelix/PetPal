package com.petpal.tools

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {

    companion object {
        private const val PREF_NAME = "PetPalPreferences"
        private const val KEY_SAVE_METHOD = "save_method"
        private const val DEFAULT_SAVE_METHOD = "NOT SET" // Default to "local"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun setSaveMethod(method: String) {
        sharedPreferences.edit().putString(KEY_SAVE_METHOD, method).apply()
    }

    fun getSaveMethod(): String {
        return sharedPreferences.getString(KEY_SAVE_METHOD, DEFAULT_SAVE_METHOD) ?: DEFAULT_SAVE_METHOD
    }
}
