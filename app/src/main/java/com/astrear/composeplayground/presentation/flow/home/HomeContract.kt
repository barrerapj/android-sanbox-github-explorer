package com.astrear.composeplayground.presentation.flow.home

import kotlinx.coroutines.flow.SharedFlow

interface HomeContract<T> {
    val viewState: SharedFlow<T>
    fun getRepositories(query: String = "")
}