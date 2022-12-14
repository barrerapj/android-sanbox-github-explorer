package com.astrear.composeplayground.data.repository.services

import com.astrear.composeplayground.data.models.Outcome
import com.astrear.composeplayground.data.repository.services.models.GithubSearchResponse

interface GithubSearchRepository {
    suspend fun search(options: Map<String, String>): Outcome<GithubSearchResponse>
}