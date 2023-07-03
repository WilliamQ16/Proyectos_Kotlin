package com.example.prototipo.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.prototipo.pages.*

// Se define la navegación en la aplicación.
@Composable
fun Navigation_Host(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Items_Bar.Boton1.ruta
    ){
        //Menú Drawer
        //Página principal
        composable(MenuItem.Page01.ruta){
            Page_Principal()
        }
        //Página Flores
        composable(MenuItem.Page02.ruta){
            Page_Flores()
        }
        //Página frutas y verduras
        composable(MenuItem.Page03.ruta){
            Page_Frutas_Verduras()
        }
        //Página Lacteos
        composable(MenuItem.Page04.ruta){
            Page_Lacteos()
        }
        //Página Carnicos
        composable(MenuItem.Page05.ruta){
            Page_Carnicos()
        }
        //Página ver más
        composable(MenuItem.Page06.ruta){
            Page_Ver_Mas()
        }
        //Menu Inferior
        //Página Inicio
        composable(Items_Bar.Boton1.ruta){
            Page_Inicio()
        }
        //Página Contenidos
        composable(Items_Bar.Boton2.ruta){
            Page_Contenidos()
        }
        //Página Información
        composable(Items_Bar.Boton3.ruta){
            Page_Informacion()
        }
        //Rutas adicionales
        //Página Login
        composable(Apps_Bar.app1.ruta){
            TiendaApp(navController, getVideoUri2())
        }
        //Página Facturación
        composable(Apps_Bar.app3.ruta){
            ProductApp()
        }
    }
}

@Composable
fun Current_Route(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}