package com.example.notificaciones

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.notificaciones.components.MainPage
import com.example.notificaciones.ui.theme.ProyectoDashboardTheme

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