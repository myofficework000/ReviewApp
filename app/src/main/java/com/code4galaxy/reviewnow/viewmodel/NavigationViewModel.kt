package com.code4galaxy.reviewnow.viewmodel

import androidx.lifecycle.ViewModel
import com.code4galaxy.reviewnow.model.NavigationItem
import com.code4galaxy.reviewnow.model.data.local.preferences.UserPreferenceManager
import com.code4galaxy.reviewnow.view.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val userPrefs: UserPreferenceManager
): ViewModel() {
    private val _currentScreen = MutableStateFlow<Screen?>(null)
    val currentScreen: StateFlow<Screen?> = _currentScreen


    fun getUserType():String{
        return userPrefs.getUserType()
    }
    fun saveUserType(userType:String){
          userPrefs.saveUserType(userType)
    }

    fun navigateTo(screen: Screen) {
        _currentScreen.value = screen
    }

    fun navigateBack() {
        if (_currentScreen.value != Screen.Home) {
            _currentScreen.value = Screen.Home
        }
    }
}