package com.astrear.composeplayground.domain.usecases

import com.astrear.composeplayground.data.models.Outcome
import com.astrear.composeplayground.data.repository.services.GithubSearchRepository
import com.astrear.composeplayground.data.repository.services.models.GithubSearchResponse
import com.astrear.composeplayground.data.repository.storage.local.SecretsRepository
import com.astrear.composeplayground.data.utils.Mapper
import com.astrear.composeplayground.domain.models.GithubPage

class SearchRepositoriesUseCaseImpl(
    private val githubSearchRepository: GithubSearchRepository,
    private val secretsRepository: SecretsRepository,
    private val responseMapper: Mapper<GithubSearchResponse, GithubPage>
) : SearchRepositoriesUseCase {
    override suspend fun getRepositories(
        options: Map<String, String>
    ): Outcome<GithubPage> {
        val result = githubSearchRepository.search(secretsRepository.sessionToken, options)

        return when (result) {
            is Outcome.Success -> {
                Outcome.Success(responseMapper.map(result.data))
            }
            is Outcome.Error -> {
                Outcome.Error(result.error)
            }
        }
    }

    companion object {
        const val WEEK_DAYS = 7
    }
}