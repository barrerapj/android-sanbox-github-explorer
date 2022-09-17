package com.astrear.composeplayground.domain.usecases

import com.astrear.composeplayground.data.models.Outcome
import com.astrear.composeplayground.domain.models.GithubPage

interface SearchRepositoriesUseCase {
    suspend fun getRepositories(options: Map<String, String>): Outcome<GithubPage>
}