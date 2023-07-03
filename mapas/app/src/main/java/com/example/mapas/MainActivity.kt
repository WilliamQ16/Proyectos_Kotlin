package com.example.mapas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mapas.components.MainPage
import com.example.mapas.ui.theme.ProyectoDashboardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContent{
            ProyectoDashboardTheme {
                MainPage()
            }
        }
    }
}