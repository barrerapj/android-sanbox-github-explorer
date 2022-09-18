package com.astrear.composeplayground.domain.usecases

import com.astrear.composeplayground.data.models.Outcome

interface GetRepositoryReadmeUseCase {
    suspend fun getReadme(url: String): Outcome<String>
}