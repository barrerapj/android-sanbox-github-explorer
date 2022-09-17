package com.astrear.composeplayground.di

import com.astrear.composeplayground.domain.usecases.AuthenticateUserUseCase
import com.astrear.composeplayground.domain.usecases.AuthenticateUserUseCaseImpl
import com.astrear.composeplayground.domain.usecases.SearchRepositoriesUseCase
import com.astrear.composeplayground.domain.usecases.SearchRepositoriesUseCaseImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val useCaseModule = module {
    single<AuthenticateUserUseCase> {
        AuthenticateUserUseCaseImpl(get(), get(), get(), get(named(MapperIds.AUTH)))
    }
    single<SearchRepositoriesUseCase> {
        SearchRepositoriesUseCaseImpl(get(), get(), get(named(MapperIds.SEARCH_RESPONSE)))
    }
}