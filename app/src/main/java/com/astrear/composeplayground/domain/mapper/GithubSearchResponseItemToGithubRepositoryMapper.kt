package com.astrear.composeplayground.domain.mapper

import com.astrear.composeplayground.data.repository.services.models.GithubSearchResponseItem
import com.astrear.composeplayground.data.utils.Mapper
import com.astrear.composeplayground.domain.models.GithubRepository

class GithubSearchResponseItemToGithubRepositoryMapper :
    Mapper<GithubSearchResponseItem, GithubRepository> {
    override fun map(input: GithubSearchResponseItem): GithubRepository {
        return GithubRepository(
            input.id,
            input.owner.username,
            input.owner.avatarUrl,
            input.name,
            input.description ?: UNDEFINED_DESCRIPTION,
            input.language ?: UNDEFINED_LANGUAGE,
            input.visibility,
            input.stars,
            input.defaultBranch,
            input.size,
            input.tags ?: listOf()
        )
    }

    companion object {
        const val UNDEFINED_LANGUAGE = "Undefined"
        const val UNDEFINED_DESCRIPTION = "No description"
    }
}