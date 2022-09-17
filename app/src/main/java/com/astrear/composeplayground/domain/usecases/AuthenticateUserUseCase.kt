package com.astrear.composeplayground.domain.usecases

import android.app.Activity
import com.astrear.composeplayground.data.models.Outcome
import com.astrear.composeplayground.domain.models.UserData

interface AuthenticateUserUseCase {
    suspend fun authenticate(activity: Activity?): Outcome<UserData>
}