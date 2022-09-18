package com.astrear.composeplayground.presentation.navigation

import android.app.Activity
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.astrear.composeplayground.domain.models.GithubRepository
import com.astrear.composeplayground.presentation.compose.HomeScreen
import com.astrear.composeplayground.presentation.compose.LoginScreen
import com.astrear.composeplayground.presentation.compose.RepositoryDetailsScreen
import com.astrear.composeplayground.presentation.navigation.constants.ArgumentKeys
import com.astrear.composeplayground.presentation.navigation.constants.MainDestinations
import com.squareup.moshi.Moshi

@Composable
fun MainGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = MainDestinations.LOGIN_ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(MainDestinations.LOGIN_ROUTE) {
            LoginScreen() {
                navController.navigate(MainDestinations.HOME_ROUTE) {
                    launchSingleTop = true
                }
            }
        }
        composable(MainDestinations.HOME_ROUTE) {
            val context = LocalContext.current
            BackHandler() {
                // TODO: find a more suitable way of closing the app
                (context as Activity).finish()
            }

            HomeScreen(
                onDetailsNavigation = {
                    // FIXME: remove dirty way to pass data to framgnet
                    val moshi = Moshi.Builder().build()
                    val repository =
                        Uri.encode(moshi.adapter(GithubRepository::class.java).toJson(it))

                    navController.navigate(MainDestinations.REPOSITORY_DETAILS + "/$repository")
                }
            )
        }
        composable(
            MainDestinations.REPOSITORY_DETAILS + "/{repository}",
            arguments = listOf(
                navArgument(ArgumentKeys.REPOSITORY) {
                    type = GithubRepositoryType()
                }
            )
        ) {
            // FIXME: dirty way to pass data to framgnet
            val repository = it.arguments?.getSerializable(
                ArgumentKeys.REPOSITORY
            ) as GithubRepository
            RepositoryDetailsScreen(repository = repository)
        }
    }
}
