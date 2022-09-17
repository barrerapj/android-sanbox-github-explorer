package com.astrear.composeplayground.data.repository.firebase.auth

import android.app.Activity
import com.astrear.composeplayground.data.models.Outcome
import com.google.firebase.auth.AuthResult

interface GithubAuthenticationFirebaseRepository {
    suspend fun authenticate(activity: Activity?): Outcome<AuthResult>
}