package com.example.prototipo.components

import com.example.prototipo.R

// Rutas e iconos que contendra la barra interior del aplicativo.

sealed class Items_Bar(
    val icon: Int,
    val ruta: String
){
    object Boton1: Items_Bar(R.drawable.home_24px, "boton1")
    object Boton2: Items_Bar(R.drawable.add_circle_24px, "boton2")
    object Boton3: Items_Bar(R.drawable.info_24px, "boton3")
}
