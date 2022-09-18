package com.astrear.composeplayground.di

import com.astrear.composeplayground.data.repository.services.GithubApi
import com.astrear.composeplayground.data.repository.services.GithubRawApi
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single<GithubApi> { provideGithubApi(get()) }
    single<GithubRawApi> { provideGithubRawApi(get()) }
}

private fun provideGithubApi(retrofit: Retrofit): GithubApi {
    return retrofit.create(GithubApi::class.java)
}

private fun provideGithubRawApi(retrofit: Retrofit): GithubRawApi {
    return retrofit.create(GithubRawApi::class.java)
}