package com.astrear.composeplayground.presentation.compose

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.astrear.composeplayground.presentation.navigation.MainGraph
import com.astrear.composeplayground.ui.theme.ComposePlaygroundTheme

@Composable
fun PlaygroundApp() {
    ComposePlaygroundTheme {
        val navController = rememberNavController()
        MainGraph(
            navController = navController
        )
    }
}