package com.astrear.composeplayground.domain.mapper

import com.astrear.composeplayground.data.repository.services.models.GithubSearchResponseItem
import com.astrear.composeplayground.data.utils.Mapper
import com.astrear.composeplayground.domain.models.GithubRepository
import org.joda.time.DateTime

class GithubSearchResponseItemToGithubRepositoryMapper :
    Mapper<GithubSearchResponseItem, GithubRepository> {
    override fun map(input: GithubSearchResponseItem): GithubRepository {
        val createdAt = DateTime(input.createdAt).toDate()
        val updatedAt = DateTime(input.updatedAt).toDate()

        return GithubRepository(
            input.owner.username,
            input.owner.avatarUrl,
            input.name,
            input.description ?: UNDEFINED_DESCRIPTION,
            input.language ?: UNDEFINED_LANGUAGE,
            input.visibility,
            input.stars,
            createdAt,
            updatedAt
        )
    }

    companion object {
        const val UNDEFINED_LANGUAGE = "Undefined"
        const val UNDEFINED_DESCRIPTION = "No description"
    }
}