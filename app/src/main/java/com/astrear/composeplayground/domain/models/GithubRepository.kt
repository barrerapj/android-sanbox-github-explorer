package com.astrear.composeplayground.domain.models

import com.squareup.moshi.JsonClass
import java.io.Serializable

data class GithubPage(
    val total: Int,
    val incomplete: Boolean,
    val items: List<GithubRepository>
)

@JsonClass(generateAdapter = true)
data class GithubRepository(
    val id: Long,
    val ownerName: String,
    val ownerProfilePictureUrl: String,
    val name: String,
    val description: String,
    val language: String,
    val visibility: String,
    val stars: Long,
    val branch: String,
    val size: Long,
    val tags: List<String>
) : Serializable {
    companion object {
        const val serialVersionUID = 1L
    }
}