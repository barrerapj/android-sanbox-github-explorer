package com.astrear.composeplayground.data.repository.storage.local

import android.content.SharedPreferences
import com.astrear.composeplayground.data.repository.storage.contants.PreferencesDefaults
import com.astrear.composeplayground.data.repository.storage.contants.PreferencesIds

class PreferencesRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : PreferencesRepository {
    override var username: String
        get() = sharedPreferences.getString(PreferencesIds.USERNAME, PreferencesDefaults.STRING)
            ?: PreferencesDefaults.STRING
        set(value) {
            sharedPreferences.edit().putString(PreferencesIds.USERNAME, value).apply()
        }
    override var email: String
        get() = sharedPreferences.getString(PreferencesIds.EMAIL, PreferencesDefaults.STRING)
            ?: PreferencesDefaults.STRING
        set(value) {
            sharedPreferences.edit().putString(PreferencesIds.EMAIL, value).apply()
        }
}