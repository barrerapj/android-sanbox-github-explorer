package com.astrear.composeplayground.presentation.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController


object MainDestinations {
    const val LOGIN_ROUTE = "login"
    const val HOME_ROUTE = "courses"
}

class MainNavigationActions(navController: NavHostController) {
    val navigateToLogin: () -> Unit = {
        navController.navigate(MainDestinations.LOGIN_ROUTE) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }
    val navigateToHome: () -> Unit = {
        navController.navigate(MainDestinations.HOME_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}