package com.astrear.composeplayground.presentation.navigation

import android.os.Bundle
import androidx.navigation.NavType
import com.astrear.composeplayground.domain.models.GithubRepository
import com.squareup.moshi.Moshi

class GithubRepositoryType : NavType<GithubRepository>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): GithubRepository {
        return bundle.getSerializable(key) as GithubRepository
    }

    override fun parseValue(value: String): GithubRepository {
        return Moshi.Builder().build().adapter(GithubRepository::class.java).fromJson(value)!!
    }

    override fun put(bundle: Bundle, key: String, value: GithubRepository) {
        bundle.putSerializable(key, value)
    }
}