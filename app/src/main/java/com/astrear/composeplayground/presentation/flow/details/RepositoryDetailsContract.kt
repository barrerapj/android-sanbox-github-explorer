package com.astrear.composeplayground.presentation.flow.details

import com.astrear.composeplayground.domain.models.GithubRepository
import kotlinx.coroutines.flow.SharedFlow

interface RepositoryDetailsContract<T> {
    val viewState: SharedFlow<T>
    fun getRepositoryReadme(repository: GithubRepository)
}