package com.example.proyectodashboard.components

import com.example.proyectodashboard.R

sealed class Items_Bar(
    val icon: Int,
    val title: String,
    val ruta: String
){
    object Boton1: Items_Bar(R.drawable.ic_bike_24, "Inicio", "boton1")
    object Boton2: Items_Bar(R.drawable.ic_moto_24, "Contenidos", "boton2")
    object Boton3: Items_Bar(R.drawable.ic_recycling_24, "Información", "boton3")
}
