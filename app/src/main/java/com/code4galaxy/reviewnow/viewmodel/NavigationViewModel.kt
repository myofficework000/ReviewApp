package com.code4galaxy.reviewnow.viewmodel

import androidx.lifecycle.ViewModel
import com.code4galaxy.reviewnow.view.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NavigationViewModel : ViewModel() {
    private val _currentScreen = MutableStateFlow<Screen?>(null)
    val currentScreen: StateFlow<Screen?> = _currentScreen

    fun navigateTo(screen: Screen) {
        _currentScreen.value = screen
    }

    fun navigateBack() {
        if (_currentScreen.value != Screen.Home) {
            _currentScreen.value = Screen.Home
        }
    }
}