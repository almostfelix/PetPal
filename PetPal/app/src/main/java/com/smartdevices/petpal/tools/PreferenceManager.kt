package com.smartdevices.petpal.tools

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {

    companion object {
        private const val PREF_NAME = "PetPalPreferences"
        private const val KEY_SAVE_METHOD = "save_method"
        private const val UUID = "uuid"
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
}
