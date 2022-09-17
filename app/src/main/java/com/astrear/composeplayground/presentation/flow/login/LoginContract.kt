package com.astrear.composeplayground.presentation.flow.login

import android.app.Activity
import kotlinx.coroutines.flow.SharedFlow

interface LoginContract<T> {
    val viewState: SharedFlow<T>
    fun authenticate(activity: Activity?)
}