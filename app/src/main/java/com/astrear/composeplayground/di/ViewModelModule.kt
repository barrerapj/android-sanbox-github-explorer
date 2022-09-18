package com.astrear.composeplayground.di

import com.astrear.composeplayground.presentation.flow.details.RepositoryDetailsViewModel
import com.astrear.composeplayground.presentation.flow.home.HomeViewModel
import com.astrear.composeplayground.presentation.flow.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        LoginViewModel(get())
    }
    viewModel {
        HomeViewModel(get())
    }
    viewModel {
        RepositoryDetailsViewModel(get())
    }
}