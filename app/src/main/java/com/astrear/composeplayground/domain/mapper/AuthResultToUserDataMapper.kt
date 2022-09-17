package com.astrear.composeplayground.domain.mapper

import com.astrear.composeplayground.data.utils.Mapper
import com.astrear.composeplayground.domain.models.UserData
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.OAuthCredential

class AuthResultToUserDataMapper : Mapper<AuthResult, UserData> {
    override fun map(input: AuthResult): UserData {
        val extraInfo = input.additionalUserInfo?.profile ?: mapOf()

        val username = getSafeString(extraInfo["login"])
        val email = getSafeString(input.user?.email)
        val profileUrl = getSafeString(extraInfo["html_url"])
        val profileImageUrl = getSafeString(extraInfo["avatar_url"])
        val accessToken = getSafeString((input.credential as OAuthCredential?)?.accessToken)

        return UserData(
            username,
            email,
            profileUrl,
            profileImageUrl,
            accessToken
        )
    }

    private fun getSafeString(source: Any?): String {
        return try {
            source as String
        } catch (error: Exception) {
            ""
        }
    }
}