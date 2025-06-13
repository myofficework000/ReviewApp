package com.code4galaxy.reviewnow.model.data.repository.settings

import kotlinx.coroutines.flow.Flow

interface LanguageRepository {
    val selectedLanguage: Flow<String>
    suspend fun setLanguage(languageCode: String)
}