package com.astrear.composeplayground.data.repository.services

import com.astrear.composeplayground.data.repository.services.constants.Endpoints
import com.astrear.composeplayground.data.repository.services.models.GithubSearchResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.QueryMap

interface GithubApi {
    @GET(Endpoints.SEARCH)
    suspend fun search(
        @Header("Accept") format: String = "application/vnd.github+json",
        @Header("Authorization") auth: String,
        @QueryMap options: Map<String, String>
    ): GithubSearchResponse
}