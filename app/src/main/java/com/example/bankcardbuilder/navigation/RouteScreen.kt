package com.example.bankcardbuilder.navigation

sealed class RouteScreen(val route: String) {
    data object Splash : RouteScreen("splash")
    data object LogIn : RouteScreen("logIn")
    data object MainProfile : RouteScreen("mainProfile")
    data object CardSettings : RouteScreen("cardSettings")
    data object SignUp : RouteScreen("signUp")
    data object SetPine : RouteScreen("setPine")
}