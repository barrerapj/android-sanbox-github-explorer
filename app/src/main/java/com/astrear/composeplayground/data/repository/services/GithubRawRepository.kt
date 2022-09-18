package com.astrear.composeplayground.data.repository.services

import com.astrear.composeplayground.data.models.Outcome

interface GithubRawRepository {
    suspend fun getReadme(url: String): Outcome<String>
}