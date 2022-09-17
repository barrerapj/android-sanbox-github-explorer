package com.astrear.composeplayground.di

import com.astrear.composeplayground.data.repository.services.GithubApi
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single<GithubApi> { provideGithubApi(get()) }
}

private fun provideGithubApi(retrofit: Retrofit): GithubApi {
    return retrofit.create(GithubApi::class.java)
}