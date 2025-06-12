package com.code4galaxy.reviewnow.di

import android.content.Context
import com.code4galaxy.reviewnow.model.data.local.preferences.UserPreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideUserPreferenceManager(
        @ApplicationContext context: Context
    ):UserPreferenceManager{
        return UserPreferenceManager(context)
    }

}