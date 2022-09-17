package com.astrear.composeplayground.data.repository.storage.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.astrear.composeplayground.data.repository.storage.contants.PreferencesIds

object SharePreferencesFactory {
    fun Context.getEncryptedSharedPreferences(): SharedPreferences {
        val masterKey = MasterKey.Builder(this, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            this,
            PreferencesIds.PREFERENCES_ENCRYPTED,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun Context.getDefaultSharedPreferences(): SharedPreferences {
        return getSharedPreferences(PreferencesIds.PREFERENCES_DEFAULT, Context.MODE_PRIVATE)
    }
}