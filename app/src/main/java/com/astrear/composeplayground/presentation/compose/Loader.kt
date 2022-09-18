package com.astrear.composeplayground.presentation.compose

import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.astrear.composeplayground.R
import com.astrear.composeplayground.ui.theme.ComposePlaygroundTheme
import com.astrear.composeplayground.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationGraphicsApi::class)
@Composable
fun Loader(modifier: Modifier = Modifier, dialogState: MutableState<Boolean>) {
    val animation = AnimatedImageVector.animatedVectorResource(id = R.drawable.animation_loader)
    var atEnd = false

    if (dialogState.value) {
        Dialog(
            onDismissRequest = {
                dialogState.value = false
                atEnd = false
            },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            Card() {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(intrinsicSize = IntrinsicSize.Min)
                        .padding(20.dp)
                ) {
                    Image(
                        painter = rememberAnimatedVectorPainter(
                            animatedImageVector = animation,
                            atEnd = !atEnd
                        ),
                        contentDescription = null
                    )

                    SideEffect {
                        atEnd = true
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(start = 20.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.text_loader_title),
                            style = Typography.titleSmall
                        )
                        Text(
                            text = stringResource(id = R.string.text_loader_content),
                            style = Typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoaderPreview() {
    val previewDialogState = remember {
        mutableStateOf(true)
    }

    ComposePlaygroundTheme(dynamicColor = false) {
        Loader(dialogState = previewDialogState)
    }
}