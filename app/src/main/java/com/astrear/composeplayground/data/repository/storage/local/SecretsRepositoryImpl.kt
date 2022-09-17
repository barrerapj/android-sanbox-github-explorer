package com.astrear.composeplayground.data.repository.storage.local

import android.content.SharedPreferences
import com.astrear.composeplayground.data.repository.storage.contants.PreferencesDefaults
import com.astrear.composeplayground.data.repository.storage.contants.PreferencesIds

class SecretsRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : SecretsRepository {
    override var sessionToken: String
        get() = sharedPreferences.getString(
            PreferencesIds.SESSION_TOKEN,
            PreferencesDefaults.STRING
        )
            ?: PreferencesDefaults.STRING
        set(value) {
            sharedPreferences.edit().putString(PreferencesIds.SESSION_TOKEN, value).apply()
        }
}