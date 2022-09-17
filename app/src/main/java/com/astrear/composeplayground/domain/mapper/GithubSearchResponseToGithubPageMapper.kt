package com.astrear.composeplayground.domain.mapper

import com.astrear.composeplayground.data.repository.services.models.GithubSearchResponse
import com.astrear.composeplayground.data.repository.services.models.GithubSearchResponseItem
import com.astrear.composeplayground.data.utils.ListMapper
import com.astrear.composeplayground.data.utils.Mapper
import com.astrear.composeplayground.domain.models.GithubPage
import com.astrear.composeplayground.domain.models.GithubRepository

class GithubSearchResponseToGithubPageMapper(
    private val itemMapper: Mapper<GithubSearchResponseItem, GithubRepository>
) : Mapper<GithubSearchResponse, GithubPage> {
    override fun map(input: GithubSearchResponse): GithubPage {
        val itemsMapper = ListMapper(itemMapper)
        val items = itemsMapper.map(input.items)

        return GithubPage(
            input.totalCount,
            input.incomplete,
            items = items
        )
    }
}