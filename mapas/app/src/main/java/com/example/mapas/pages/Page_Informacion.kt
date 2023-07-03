package com.example.mapas.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun Page_Informacion(){
    Column() {
        Text(
            text = "Información",
            style = MaterialTheme.typography.h2
        )
    }
}