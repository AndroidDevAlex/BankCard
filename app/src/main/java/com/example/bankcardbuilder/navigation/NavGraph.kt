package com.example.bankcardbuilder.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.bankcardbuilder.screens.settingsCard.mainCardSettings.CardSettingsScreen
import com.example.bankcardbuilder.screens.auth.logIn.LogInScreen
import com.example.bankcardbuilder.screens.settingsCard.loginPinCode.LoginPinCodeScreen
import com.example.bankcardbuilder.screens.settingsCard.setPinCode.PinCodeScreen
import com.example.bankcardbuilder.screens.settingsAccount.profile.ProfileScreen
import com.example.bankcardbuilder.screens.settingsAccount.securityQuestion.SecurityQuestionScreen
import com.example.bankcardbuilder.screens.auth.signUp.SignUpScreen
import com.example.bankcardbuilder.screens.auth.splash.SplashScreen
import com.example.bankcardbuilder.screens.settingsAccount.main.MainProfileScreen
import com.example.bankcardbuilder.screens.settingsAccount.phoneNumber.PhoneNumberScreen

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
                },
                goToProfileScreen = {
                    navHostController.navigate(RouteScreen.Profile.route)

                },
                goToPhoneNumberScreen = {
                    navHostController.navigate(RouteScreen.PhoneNumber.route)
                },

                goToSecurityQuestionScreen = {
                    navHostController.navigate(RouteScreen.SecurityQuestion.route)
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
                goToLogIn = {
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

        composable(RouteScreen.CardSettings.route) {
            CardSettingsScreen(
                goToMainScreen = { navHostController.popBackStack() },
                goToPinCodeScreen = { cardNumber ->
                    navHostController.navigate(
                        NavigationRoutes.PIN_CODE_ROUTE_FROM_SETTINGS.replace(
                            "{cardNumber}",
                            cardNumber
                        )
                    )
                }
            )
        }

        composable(
            NavigationRoutes.PIN_CODE_ROUTE,
            arguments = listOf(
                navArgument("cardNumber") { type = NavType.StringType }
            )) {
            PinCodeScreen(
                goToCardSettingsScreen = { navHostController.popBackStack() },
                goToMainScreen = { navHostController.navigate(RouteScreen.MainProfile.route) })
        }

        composable(RouteScreen.SignUp.route) {
            SignUpScreen(
                goToProfileScreen = {
                    navHostController.navigate(RouteScreen.Profile.route) {
                        popUpTo(RouteScreen.SignUp.route) { inclusive = true }
                    }
                }
            )
        }
        composable(RouteScreen.SecurityQuestion.route) {
            SecurityQuestionScreen(
                goBackToPhoneNumberScreen = { navHostController.popBackStack() },
                goToMainScreen = { navHostController.navigate(RouteScreen.MainProfile.route) }
            )
        }
        composable(RouteScreen.Profile.route) {
            ProfileScreen(
                goToLoginScreen = { navHostController.popBackStack() },
                goToPhoneNumberScreen = { navHostController.navigate(RouteScreen.PhoneNumber.route) }
            )
        }

        composable(RouteScreen.PhoneNumber.route) {
            PhoneNumberScreen(
                goBackToProfileScreen = { navHostController.popBackStack() },
                goToSecurityQuestionScreen = { navHostController.navigate(RouteScreen.SecurityQuestion.route) }
            )
        }

        composable(
            NavigationRoutes.LOG_IN_CODE_ROUTE,
            arguments = listOf(navArgument("cardNumber") { type = NavType.StringType }
            )) {
            LoginPinCodeScreen(goToMainScreen = { navHostController.popBackStack() })
        }
    }
}

object NavigationRoutes {
    const val PIN_CODE_ROUTE = "pinCode/{cardNumber}"
    const val PIN_CODE_ROUTE_FROM_SETTINGS = "pinCode/{cardNumber}"
    const val LOG_IN_CODE_ROUTE = "logInPinCode/{cardNumber}"
    const val PIN_CODE_ROUTE_FROM_MAIN_SCREEN = "logInPinCode/{cardNumber}"
}