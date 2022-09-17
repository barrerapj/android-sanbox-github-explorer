package com.astrear.composeplayground.di

import com.astrear.composeplayground.data.repository.services.models.GithubSearchResponse
import com.astrear.composeplayground.data.repository.services.models.GithubSearchResponseItem
import com.astrear.composeplayground.data.utils.Mapper
import com.astrear.composeplayground.domain.mapper.AuthResultToUserDataMapper
import com.astrear.composeplayground.domain.mapper.GithubSearchResponseItemToGithubRepositoryMapper
import com.astrear.composeplayground.domain.mapper.GithubSearchResponseToGithubPageMapper
import com.astrear.composeplayground.domain.models.GithubPage
import com.astrear.composeplayground.domain.models.GithubRepository
import com.astrear.composeplayground.domain.models.UserData
import com.google.firebase.auth.AuthResult
import org.koin.core.qualifier.named
import org.koin.dsl.module

val mapperModule = module {
    single<Mapper<AuthResult, UserData>>(named(MapperIds.AUTH)) { AuthResultToUserDataMapper() }
    single<Mapper<GithubSearchResponseItem, GithubRepository>>(
        named(MapperIds.SEARCH_RESPONSE_ITEM)
    ) {
        GithubSearchResponseItemToGithubRepositoryMapper()
    }
    single<Mapper<GithubSearchResponse, GithubPage>>(named(MapperIds.SEARCH_RESPONSE)) {
        GithubSearchResponseToGithubPageMapper(get(named(MapperIds.SEARCH_RESPONSE_ITEM)))
    }
}

object MapperIds {
    const val AUTH = "authMapper"
    const val SEARCH_RESPONSE = "searchMapper"
    const val SEARCH_RESPONSE_ITEM = "searchItemMapper"
}