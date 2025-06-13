package com.code4galaxy.reviewnow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code4galaxy.reviewnow.model.data.repository.settings.LanguageRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

class LanguageViewModel @Inject constructor(private val languageRepository: LanguageRepository) :
    ViewModel() {

    val language = languageRepository.selectedLanguage.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = Locale.getDefault().language
    )

    fun changeLanguage(languageCode: String) {
        viewModelScope.launch {
            languageRepository.setLanguage(languageCode)
        }
    }
}