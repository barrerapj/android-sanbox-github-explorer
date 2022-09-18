package com.astrear.composeplayground.presentation.compose

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.astrear.composeplayground.R
import com.astrear.composeplayground.presentation.flow.login.LoginContract
import com.astrear.composeplayground.presentation.flow.login.LoginViewModel
import com.astrear.composeplayground.presentation.flow.login.LoginViewModel.LoginViewState
import com.astrear.composeplayground.presentation.utils.toast
import com.astrear.composeplayground.ui.theme.ComposePlaygroundTheme
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    contract: LoginContract<LoginViewState> = koinViewModel<LoginViewModel>(),
    onNavigateToHome: () -> Unit
) {
    val context = LocalContext.current
    val activity = try {
        context as Activity
    } catch (error: ClassCastException) {
        null
    }

    LaunchedEffect(Unit) {
        contract.viewState
            .collectLatest {
                when (it) {
                    LoginViewState.HasError -> {
                        toast(context, R.string.text_error_login)
                    }
                    is LoginViewState.HasValidSession -> {
                        onNavigateToHome()
                    }
                }
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff171c18))
            .padding(horizontal = 50.dp)
    ) {
        val constraints = ConstraintSet {
            val centerGdl = createGuidelineFromTop(0.5f)

            val image = createRefFor("logo")
            val button = createRefFor("button")

            constrain(image) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(centerGdl)
            }

            constrain(button) {
                top.linkTo(centerGdl)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        }
        ConstraintLayout(constraintSet = constraints, modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier
                    .layoutId("logo")
                    .fillMaxWidth(),
                painter = painterResource(id = R.drawable.logo_alt),
                contentDescription = null,
            )

            Button(
                modifier = Modifier
                    .layoutId("button")
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                onClick = {
                    contract.authenticate(activity)
                }
            ) {
                val image = painterResource(id = R.drawable.ic_github_120px)
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterStart)
                    ) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            painter = image,
                            modifier = Modifier
                                .size(18.dp),
                            contentDescription = null
                        )
                    }
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = stringResource(id = R.string.label_button_login_github),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    val previewContract = object : LoginContract<LoginViewState> {
        override val viewState: SharedFlow<LoginViewState> = MutableSharedFlow()

        override fun authenticate(activity: Activity?) {
            // Nothing
        }
    }
    ComposePlaygroundTheme {
        LoginScreen(contract = previewContract) {}
    }
}