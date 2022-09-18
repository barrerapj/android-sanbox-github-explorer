package com.astrear.composeplayground.di

import com.astrear.composeplayground.data.repository.firebase.auth.GithubAuthenticationFirebaseRepository
import com.astrear.composeplayground.data.repository.firebase.auth.GithubAuthenticationFirebaseRepositoryImpl
import com.astrear.composeplayground.data.repository.services.GithubRawRepository
import com.astrear.composeplayground.data.repository.services.GithubRawRepositoryImpl
import com.astrear.composeplayground.data.repository.services.GithubSearchRepository
import com.astrear.composeplayground.data.repository.services.GithubSearchRepositoryImpl
import com.astrear.composeplayground.data.repository.storage.contants.PreferencesIds
import com.astrear.composeplayground.data.repository.storage.local.PreferencesRepository
import com.astrear.composeplayground.data.repository.storage.local.PreferencesRepositoryImpl
import com.astrear.composeplayground.data.repository.storage.local.SecretsRepository
import com.astrear.composeplayground.data.repository.storage.local.SecretsRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {
    single<GithubAuthenticationFirebaseRepository> {
        GithubAuthenticationFirebaseRepositoryImpl(get(), get())
    }
    single<PreferencesRepository> {
        PreferencesRepositoryImpl(get(named(PreferencesIds.PREFERENCES_DEFAULT)))
    }
    single<SecretsRepository> {
        SecretsRepositoryImpl(get(named(PreferencesIds.PREFERENCES_ENCRYPTED)))
    }
    single<GithubSearchRepository> {
        GithubSearchRepositoryImpl(get(), get())
    }
    single<GithubRawRepository> {
        GithubRawRepositoryImpl(get(), get())
    }
}