package com.astrear.composeplayground.domain.usecases

import android.app.Activity
import com.astrear.composeplayground.data.models.Outcome
import com.astrear.composeplayground.data.repository.firebase.auth.GithubAuthenticationFirebaseRepository
import com.astrear.composeplayground.data.repository.storage.local.PreferencesRepository
import com.astrear.composeplayground.data.repository.storage.local.SecretsRepository
import com.astrear.composeplayground.data.utils.Mapper
import com.astrear.composeplayground.domain.models.UserData
import com.google.firebase.auth.AuthResult

class AuthenticateUserUseCaseImpl(
    private val authRepository: GithubAuthenticationFirebaseRepository,
    private val preferences: PreferencesRepository,
    private val secrets: SecretsRepository,
    private val authMapper: Mapper<AuthResult, UserData>
) : AuthenticateUserUseCase {
    override suspend fun authenticate(activity: Activity?): Outcome<UserData> {
        val result = authRepository.authenticate(activity)

        return when (result) {
            is Outcome.Success -> {
                val userData = authMapper.map(result.data)
                preferences.username = userData.username
                preferences.email = userData.email
                secrets.sessionToken = userData.accesToken

                Outcome.Success(userData)
            }

            is Outcome.Error -> {
                Outcome.Error(result.error)
            }
        }
    }
}