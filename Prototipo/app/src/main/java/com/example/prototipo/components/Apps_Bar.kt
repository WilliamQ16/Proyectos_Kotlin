package com.example.prototipo.components

//Clase para crear rutas extras como el login y la facturación de productos.
sealed class Apps_Bar(
    val ruta: String
){
    object app1: Apps_Bar("app1")
    object app3: Apps_Bar("app3")
}
