package com.code4galaxy.reviewnow.model.data.repository.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Locale
import javax.inject.Inject

class LanguageRepositoryImpl @Inject constructor(private val dataStore: DataStore<Preferences>) :
    LanguageRepository {

    override val selectedLanguage: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[LANGUAGE_KEY] ?: Locale.getDefault().language
        }

    override suspend fun setLanguage(languageCode: String) {
        dataStore.edit { it[LANGUAGE_KEY] = languageCode }
    }

    companion object {
        val LANGUAGE_KEY = stringPreferencesKey("language")
    }
}