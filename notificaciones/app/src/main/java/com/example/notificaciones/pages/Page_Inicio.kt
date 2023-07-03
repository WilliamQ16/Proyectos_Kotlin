package com.example.notificaciones.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun Page_Inicio(){
    Column() {
        Text(
            text = "Inicio",
            style = MaterialTheme.typography.h2
        )
    }
}