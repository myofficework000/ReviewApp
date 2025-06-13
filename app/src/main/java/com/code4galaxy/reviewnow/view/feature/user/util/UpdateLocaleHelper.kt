package com.code4galaxy.reviewnow.view.feature.user.util

import android.content.Context
import java.util.Locale

// Update the app's language settings
fun updateLocale(context: Context, languageCode: String): Context{
    val locale = Locale(languageCode)
    Locale.setDefault(locale)
    val config = context.resources.configuration
    config.setLocale(locale)
    return context.createConfigurationContext(config)
}