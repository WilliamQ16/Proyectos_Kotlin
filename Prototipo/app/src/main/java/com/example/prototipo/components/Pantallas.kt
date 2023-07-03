package com.example.prototipo.components

// Estas son rutas las cuales se asignaran a la ventana splash y al aplicativo en general.
sealed class Pantallas(val route: String){
    object SplashScreen: Pantallas("splash_screen")
    object Home: Pantallas("Home")
}
