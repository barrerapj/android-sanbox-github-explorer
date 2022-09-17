package com.astrear.composeplayground.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.astrear.composeplayground.presentation.compose.HomeScreen
import com.astrear.composeplayground.presentation.compose.LoginScreen

@Composable
fun MainGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = MainDestinations.HOME_ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(MainDestinations.LOGIN_ROUTE) {
            LoginScreen {
                navController.navigate(MainDestinations.HOME_ROUTE)
            }
        }
        composable(MainDestinations.HOME_ROUTE) {
            HomeScreen()
        }
    }
}