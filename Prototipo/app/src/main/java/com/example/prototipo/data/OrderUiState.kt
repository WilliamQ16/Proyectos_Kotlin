package com.example.prototipo.data

// Constantes que se usaran en el sistema de facturación las cuales se les asignaran datos como
// el precio, cantidades, fecha, etc.
data class OrderUiState (
    val quantity:Int = 0,
    val product: String = "",
    val date: String = "",
    val price: String = "",
    val pickupOptions: List<String> = listOf()
)