package com.astrear.composeplayground.data.repository.services.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GithubSearchResponse(
    @Json(name = "total_count")
    val totalCount: Int,
    @Json(name = "incomplete_results")
    val incomplete: Boolean,
    val items: List<GithubSearchResponseItem>
)

@JsonClass(generateAdapter = true)
data class GithubSearchResponseItem(
    val id: Long,
    val owner: GithubSearchResponseItemOwner,
    val name: String,
    val description: String?,
    val url: String,
    val language: String?,
    val visibility: String,
    @Json(name = "stargazers_count")
    val stars: Long,
    @Json(name = "default_branch")
    val defaultBranch: String,
    @Json(name = "created_at")
    val createdAt: String,
    @Json(name = "updated_at")
    val updatedAt: String,
)

@JsonClass(generateAdapter = true)
data class GithubSearchResponseItemOwner(
    val id: Int,
    @Json(name = "avatar_url")
    val avatarUrl: String,
    @Json(name = "login")
    val username: String
)
