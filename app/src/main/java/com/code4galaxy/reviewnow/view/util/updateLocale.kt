package com.code4galaxy.reviewnow.view.util

import android.content.Context
import android.os.Build
import java.util.Locale

fun updateLocale(context: Context, languageCode: String): Context {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)

    val resources = context.resources
    val configuration = resources.configuration

    configuration.setLocale(locale)
    configuration.setLayoutDirection(locale)

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        context.createConfigurationContext(configuration)
    } else {
        @Suppress("DEPRECATION")
        resources.updateConfiguration(configuration, resources.displayMetrics)
        context
    }
}
