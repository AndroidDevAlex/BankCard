package com.example.bankcardbuilder.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.bankcardbuilder.screens.auth.logIn.LogInScreen
import com.example.bankcardbuilder.screens.settingsCard.loginPinCode.LoginPinCodeScreen
import com.example.bankcardbuilder.screens.auth.splash.SplashScreen
import com.example.bankcardbuilder.screens.mainProfile.MainProfileScreen
import com.example.bankcardbuilder.screens.registration.RegistrationScreenFlow
import com.example.bankcardbuilder.screens.settingsCard.cardSettings.CardSettingsScreensFlow

@Composable
fun NavGraph(
    navHostController: NavHostController
) {
    NavHost(navController = navHostController, startDestination = RouteScreen.Splash.route) {
        composable(RouteScreen.Splash.route) {
            SplashScreen(
                goToLoginScreen = {
                    navHostController.navigate(RouteScreen.LogIn.route) {
                        popUpTo(RouteScreen.Splash.route) { inclusive = true }
                    }
                },

                goToMainProfileScreen = {
                    navHostController.navigate(RouteScreen.MainProfile.route) {
                        popUpTo(RouteScreen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(RouteScreen.LogIn.route) {
            LogInScreen(
                goToMainScreen = { navHostController.navigate(RouteScreen.MainProfile.route) },
                goToSignUp = { navHostController.navigate(RouteScreen.SignUp.route) }
            )
        }

        composable(RouteScreen.MainProfile.route) {
            MainProfileScreen(
                goToCardSettingsScreen = {
                    navHostController.navigate(RouteScreen.CardSettings.route)
                },
                logOut = {
                    navHostController.navigate(RouteScreen.LogIn.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                goToLoginPinCodeScreen = { cardNumber ->
                    navHostController.navigate(
                        NavigationRoutes.PIN_CODE_ROUTE_FROM_MAIN_SCREEN.replace(
                            "{cardNumber}",
                            cardNumber
                        )
                    )
                }
            )
        }

        composable(RouteScreen.SignUp.route) {
            RegistrationScreenFlow(
                goToMainScreen = { navHostController.navigate(RouteScreen.MainProfile.route) }
            )
        }

        composable(RouteScreen.CardSettings.route) {
            CardSettingsScreensFlow(
                goToMainScreen = { navHostController.navigate(RouteScreen.MainProfile.route) },
                goToPinCodeScreen = { navHostController.navigate(RouteScreen.SetPine.route) },
            )
        }

        composable(
            NavigationRoutes.LOG_IN_CODE_ROUTE,
            arguments = listOf(navArgument("cardNumber") { type = NavType.StringType }
            )) {
            LoginPinCodeScreen(
                goToMainScreen = {
                    navHostController.popBackStack()
                }
            )
        }
    }
}

object NavigationRoutes {
    const val LOG_IN_CODE_ROUTE = "logInPinCode/{cardNumber}"
    const val PIN_CODE_ROUTE_FROM_MAIN_SCREEN = "logInPinCode/{cardNumber}"
}