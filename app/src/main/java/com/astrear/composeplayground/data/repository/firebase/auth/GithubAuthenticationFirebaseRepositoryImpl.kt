package com.astrear.composeplayground.data.repository.firebase.auth

import android.app.Activity
import com.astrear.composeplayground.data.models.Outcome
import com.astrear.composeplayground.data.utils.CoroutineDispatcherProvider
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import timber.log.Timber

class GithubAuthenticationFirebaseRepositoryImpl(
    private val provider: CoroutineDispatcherProvider,
    private val firebaseAuth: FirebaseAuth,
) : GithubAuthenticationFirebaseRepository {
    override suspend fun authenticate(activity: Activity?): Outcome<AuthResult> {
        return withContext(provider.ioDispatcher()) {
            val provider = OAuthProvider.newBuilder("github.com")

            try {
                requireNotNull(activity)
                val authTask: Task<AuthResult> =
                    // There's something already here! Finish the sign-in for your user.
                    firebaseAuth.pendingAuthResult
                        ?: firebaseAuth.startActivityForSignInWithProvider(
                            activity,
                            provider.build()
                        )


                val authResult = authTask.await()
                return@withContext Outcome.Success(authResult)
            } catch (error: Exception) {
                Timber.e(error, "Error in login")
                return@withContext Outcome.Error(error)
            }
        }
    }
}