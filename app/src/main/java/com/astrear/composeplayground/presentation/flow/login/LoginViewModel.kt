package com.astrear.composeplayground.presentation.flow.login

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.astrear.composeplayground.data.models.Outcome
import com.astrear.composeplayground.domain.models.UserData
import com.astrear.composeplayground.domain.usecases.AuthenticateUserUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch


class LoginViewModel(
    private val authenticateUserUseCase: AuthenticateUserUseCase
) : ViewModel(), LoginContract<LoginViewModel.LoginViewState> {
    private var _viewState = MutableSharedFlow<LoginViewState>(replay = 0)
    override val viewState: SharedFlow<LoginViewState> = _viewState

    override fun authenticate(activity: Activity?) {
        viewModelScope.launch {
            val result = authenticateUserUseCase.authenticate(activity)
            when (result) {
                is Outcome.Success -> {
                    _viewState.emit(LoginViewState.HasValidSession(result.data))
                }

                is Outcome.Error -> {
                    _viewState.emit(LoginViewState.HasError)
                }
            }
        }
    }

    sealed class LoginViewState {
        class HasValidSession(val userData: UserData) : LoginViewState()
        object HasError : LoginViewState()
    }
}