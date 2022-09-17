package com.astrear.composeplayground.data.repository.services

import com.astrear.composeplayground.data.models.Outcome
import com.astrear.composeplayground.data.repository.services.models.GithubSearchResponse
import com.astrear.composeplayground.data.utils.CoroutineDispatcherProvider
import kotlinx.coroutines.withContext
import timber.log.Timber

class GithubSearchRepositoryImpl(
    private val provider: CoroutineDispatcherProvider,
    private val api: GithubApi
) : GithubSearchRepository {
    override suspend fun search(
        token: String,
        options: Map<String, String>
    ): Outcome<GithubSearchResponse> {
        return withContext(provider.ioDispatcher()) {
            try {
                val result = api.search(
                    auth = "Bearer $token",
                    options = options
                )

                Outcome.Success(result)
            } catch (error: Exception) {
                Timber.e(error)
                Outcome.Error(error)
            }
        }
    }
}