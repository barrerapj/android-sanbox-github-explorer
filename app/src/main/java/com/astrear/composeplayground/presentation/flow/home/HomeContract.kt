package com.astrear.composeplayground.presentation.flow.home

import com.astrear.composeplayground.domain.models.GithubRepository
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface HomeContract<T> {
    val viewState: SharedFlow<T>
    val repositories: StateFlow<List<GithubRepository>>
    fun getRepositories(query: String = "")
}