package com.astrear.composeplayground.domain.models

import java.util.*

data class GithubPage(
    val total: Int,
    val incomplete: Boolean,
    val items: List<GithubRepository>
)

data class GithubRepository(
    val ownerName: String,
    val ownerProfilePictureUrl: String,
    val name: String,
    val description: String,
    val language: String,
    val visibility: String,
    val stars: Long,
    val createdAt: Date,
    val updatedAt: Date
)