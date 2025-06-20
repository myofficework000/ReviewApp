package com.code4galaxy.reviewnow.model.data.local.preferences

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class UserPreferenceManager(
    private val context: Context
) {

    private val prefs by lazy {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        EncryptedSharedPreferences.create(
            context,
            "user_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    //user_type can be just user or admin
    // TODO use sealed or enum class
    fun saveUserType(userType: String) {
        prefs.edit().putString("user_type", userType).apply()

    }

    fun saveId(id: String) {
        prefs.edit().putString("id", id).apply()
    }

    fun removeKey(key: String) {
        prefs.edit().remove(key).apply()
    }


    fun getUserType(): String {
        return prefs.getString("user_type", "no user") ?: "no user"
    }

    fun getId(): String {
        return prefs.getString("id", "no id") ?: "no id"
    }


    // this is will be used after logging out
    fun clear() {
        prefs.edit().clear().apply()
    }
}
