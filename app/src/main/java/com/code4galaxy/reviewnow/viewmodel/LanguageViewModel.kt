
package com.code4galaxy.reviewnow.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code4galaxy.reviewnow.model.data.repository.settings.LanguageRepository
import com.code4galaxy.reviewnow.view.feature.user.util.saveLanguageCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val languageRepository: LanguageRepository
) : ViewModel() {

    val language = languageRepository.selectedLanguage.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Locale.getDefault().language
    )

    fun changeLanguage(context: Context, languageCode: String) {
        viewModelScope.launch {
            saveLanguageCode(context, languageCode)
            languageRepository.setLanguage(languageCode)
        }
    }
}