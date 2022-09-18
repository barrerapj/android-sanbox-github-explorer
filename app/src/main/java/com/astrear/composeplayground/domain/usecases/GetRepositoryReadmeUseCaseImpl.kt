package com.astrear.composeplayground.domain.usecases

import com.astrear.composeplayground.data.models.Outcome
import com.astrear.composeplayground.data.repository.services.GithubRawRepository

class GetRepositoryReadmeUseCaseImpl(
    private val githubRawRepository: GithubRawRepository
) : GetRepositoryReadmeUseCase {
    override suspend fun getReadme(url: String): Outcome<String> {
        return githubRawRepository.getReadme(url)
    }
}