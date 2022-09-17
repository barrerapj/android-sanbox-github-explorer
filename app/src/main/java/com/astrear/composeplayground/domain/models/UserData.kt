package com.astrear.composeplayground.domain.models

data class UserData(
    val username: String,
    val email: String,
    val profileUrl: String,
    val profileImageUrl: String,
    val accesToken: String
)