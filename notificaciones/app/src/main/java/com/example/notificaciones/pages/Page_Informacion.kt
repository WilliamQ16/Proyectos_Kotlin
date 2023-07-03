package com.example.notificaciones.pages

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.notificaciones.R
import com.example.notificaciones.components.CreateChannelNotification
import com.example.notificaciones.components.notificacionExtensa
import com.example.notificaciones.components.notificacionImagen
import com.example.notificaciones.components.notificacionProgramada
import com.example.notificaciones.components.notificacionSencilla

@Composable
fun Page_Informacion(){

    val idNotification: Int = 0
    val context: Context = LocalContext.current
    val idChannel: String = stringResource(R.string.canalTienda)
    val name = stringResource(R.string.canalTienda)
    val descriptionText = stringResource(R.string.canalNotificacion)

    val textShort: String = "Ejemplo de notificación sencilla con prioridad por omisión (default)"
    val textLong: String = "Saludos! Esta es una prueba de notificaciones. Debe aparecer " +
            "un icono a la derecha y el texto puede tener una longitud de 200 caracteres. " +
            "El tamaño de la notificación puede colapsar y/o expandirse. " +
            "Gracias y hasta pronto"

    val iconoBig = BitmapFactory.decodeResource(
        context.resources,
        R.drawable.sena
    )

    val imagenBig = BitmapFactory.decodeResource(
        context.resources,
        R.drawable.bg_tienda_cba
    )

    //Función de creación propia como corrutina
    LaunchedEffect(Unit){
        CreateChannelNotification(
            idChannel,
            context,
            name,
            descriptionText
        )
    }

    val modifier = Modifier
        .padding(18.dp)
        .fillMaxWidth()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(18.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Información de Notificaciones",
            style = MaterialTheme.typography.h2,
            modifier = Modifier.padding(bottom = 100.dp)
        )
        Button(onClick = {
            notificacionSencilla(
                context,
                idChannel,
                idNotification,
                "Notificación Sencilla",
                textShort
            )
        },
        modifier = modifier) {
            Text(text = "Notificaciones Sencillas")
        }
        Button(onClick = {
            notificacionExtensa(
                context,
                idChannel,
                idNotification + 1,
                "Notificación Extensa",
                textLong,
                iconoBig
            )
        },
            modifier = modifier) {
            Text(text = "Notificaciones Extensas")
        }
        Button(onClick = {
            notificacionImagen(
                context,
                idChannel,
                idNotification + 2,
                "Notificación con Imagen",
                textLong,
                iconoBig,
                imagenBig
            )
        },
            modifier = modifier) {
            Text(text = "Notificaciones con Imagenes")
        }
        Button(onClick = {
            notificacionProgramada(
                context
            )
        },
            modifier = modifier) {
            Text(text = "Notificaciones Programadas")
        }
    }
}