package com.example.proyectodashboard.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun Page_Contenidos(){
    Column() {
        Text(
            text = "Contenidos",
            style = MaterialTheme.typography.h2
        )
    }
}