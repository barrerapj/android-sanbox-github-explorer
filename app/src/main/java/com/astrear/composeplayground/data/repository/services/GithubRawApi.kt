package com.astrear.composeplayground.data.repository.services

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Url

interface GithubRawApi {
    @GET
    @Headers("Content-Type: text/plain; charset=UTF-8")
    suspend fun getReadme(@Url url: String): String
}