package com.code4galaxy.reviewnow.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.code4galaxy.reviewnow.model.data.repository.settings.LanguageRepository
import com.code4galaxy.reviewnow.model.data.repository.settings.LanguageRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun providePreferenceDataStore(@ApplicationContext context: Context): DataStore<Preferences>{
        return PreferenceDataStoreFactory.create {
            context.preferencesDataStoreFile("user_preferences")
        }
    }

    @Provides
    @Singleton
    fun provideLanguageRepository(dataStore: DataStore<Preferences>):LanguageRepository{
        return LanguageRepositoryImpl(dataStore)
    }


}