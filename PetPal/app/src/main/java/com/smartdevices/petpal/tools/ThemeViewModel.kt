package com.smartdevices.petpal.tools

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ThemeViewModel : ViewModel() {
    var darkMode by mutableStateOf(true)
        private set

    fun toggleTheme() {
        darkMode = !darkMode
    }
}