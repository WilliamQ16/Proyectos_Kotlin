package com.example.prototipo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.prototipo.pages.Navegacion
import com.example.prototipo.ui.theme.PrototipoTheme

// Clase MainActivity, esta construye el aplicativo en el dispositivo donde se va a utilizar.
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContent{
            PrototipoTheme {
                Navegacion()
            }
        }
    }
}