package com.example.prototipo.pages

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.prototipo.components.MainPage
import com.example.prototipo.components.Pantallas

// En este archivo se asigna la navegación de solo la pantalla splash y la aplicación completa
// no se realizo toda la navegación en el archivo Navigation_Host porque la pantalla splash
// tambien iba a incluir los menús superiores, inferiores, drawer y el botón elevado.
@Composable
fun Navegacion() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController, startDestination = Pantallas.SplashScreen.route
    ){
        composable(Pantallas.SplashScreen.route){
            SplashScreen(navController)
        }

        composable(Pantallas.Home.route){
            MainPage()
        }
    }
}