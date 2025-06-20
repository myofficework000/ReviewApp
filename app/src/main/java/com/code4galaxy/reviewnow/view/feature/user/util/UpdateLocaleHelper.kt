package com.code4galaxy.reviewnow.view.feature.user.util

import android.content.Context
import android.os.Build
import java.util.Locale

fun updateLocale(context: Context, languageCode: String): Context {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)

    val resources = context.resources
    val config = resources.configuration
    config.setLocale(locale)
    config.setLayoutDirection(locale)

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        context.createConfigurationContext(config)
    } else {
        @Suppress("DEPRECATION")
        resources.updateConfiguration(config, resources.displayMetrics)
        context
    }
}

fun saveLanguageCode(context: Context, languageCode: String) {
    val prefs = context.getSharedPreferences("app_settings", Context.MODE_PRIVATE)
    prefs.edit().putString("language_code", languageCode).apply()
}

fun getSavedLanguageCode(context: Context): String {
    val prefs = context.getSharedPreferences("app_settings", Context.MODE_PRIVATE)
    return prefs.getString("language_code", Locale.getDefault().language) ?: "en"
}