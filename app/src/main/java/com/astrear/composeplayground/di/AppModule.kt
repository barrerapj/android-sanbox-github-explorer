package com.astrear.composeplayground.di

import android.content.SharedPreferences
import com.astrear.composeplayground.data.repository.services.constants.Endpoints
import com.astrear.composeplayground.data.repository.storage.contants.PreferencesIds
import com.astrear.composeplayground.data.repository.storage.utils.SharePreferencesFactory.getDefaultSharedPreferences
import com.astrear.composeplayground.data.repository.storage.utils.SharePreferencesFactory.getEncryptedSharedPreferences
import com.astrear.composeplayground.data.utils.CoroutineDispatcherProvider
import com.astrear.composeplayground.data.utils.CoroutineDispatcherProviderImpl
import com.google.firebase.auth.FirebaseAuth
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    single<CoroutineDispatcherProvider> { CoroutineDispatcherProviderImpl() }
    single<SharedPreferences>(named(PreferencesIds.PREFERENCES_DEFAULT)) {
        androidContext().getDefaultSharedPreferences()
    }
    single<SharedPreferences>(named(PreferencesIds.PREFERENCES_ENCRYPTED)) {
        androidContext().getEncryptedSharedPreferences()
    }
    single<FirebaseAuth> { FirebaseAuth.getInstance() }
    single<OkHttpClient> {
        OkHttpClient
            .Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    single<Retrofit> { provideRetrofit(get()) }
}

private fun provideRetrofit(client: OkHttpClient): Retrofit {
    val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    return Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(Endpoints.BASE_URL)
        .client(client)
        .build()
}
