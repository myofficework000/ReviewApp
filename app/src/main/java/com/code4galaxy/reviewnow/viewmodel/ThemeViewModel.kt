package com.code4galaxy.reviewnow.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.code4galaxy.reviewnow.model.data.local.preferences.ThemePreference
import com.code4galaxy.reviewnow.view.theme.AppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ThemeViewModel(application: Application) : AndroidViewModel(application) {
    private val prefs = ThemePreference(application)

    private val _theme = MutableStateFlow(prefs.getTheme())
    val theme: StateFlow<AppTheme> get() = _theme

    fun setTheme(theme: AppTheme) {
        _theme.value = theme
        prefs.saveTheme(theme)
    }
}
