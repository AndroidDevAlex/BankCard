package com.example.bankcardbuilder.navigation

sealed class RouteScreen(val route: String) {
    data object Splash : RouteScreen("splash")
    data object LogIn : RouteScreen("logIn")
    data object MainProfile : RouteScreen("mainProfile")
    data object CardSettings : RouteScreen("cardSettings")
    data object SignUp : RouteScreen("signUp")
    data object SecurityQuestion : RouteScreen("securityQuestion")
    data object Profile : RouteScreen("profile")
    data object PhoneNumber : RouteScreen("phoneNumber")
}