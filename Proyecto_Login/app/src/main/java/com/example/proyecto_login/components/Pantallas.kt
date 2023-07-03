package com.example.proyecto_login.components

sealed class Pantallas(val route: String){
    object SplashScreen: Pantallas("splash_screen")
    object PantallaLogin: Pantallas("pantalla_login")
}
