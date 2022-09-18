package com.astrear.composeplayground.data.repository.services

import com.astrear.composeplayground.data.models.Outcome
import com.astrear.composeplayground.data.utils.CoroutineDispatcherProvider
import kotlinx.coroutines.withContext
import timber.log.Timber

class GithubRawRepositoryImpl(
    private val provider: CoroutineDispatcherProvider,
    private val api: GithubRawApi
) : GithubRawRepository {
    override suspend fun getReadme(url: String): Outcome<String> {
        return withContext(provider.ioDispatcher()) {
            try {
                val result = api.getReadme(url)
                Outcome.Success(result)
            } catch (error: Exception) {
                Timber.e(error)
                Outcome.Error(error)
            }
        }
    }
}