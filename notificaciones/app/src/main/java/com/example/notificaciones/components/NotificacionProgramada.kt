package com.example.notificaciones.components

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.notificaciones.MainActivity
import com.example.notificaciones.R
import com.example.notificaciones.utils.Constants.channelId

class NotificacionProgramada: BroadcastReceiver(){

    //Objeto que no requiere ser instanciado de una clase
    companion object{
        const val NOTIFICATION_ID = 5
    }

    override fun onReceive(context: Context, intent: Intent?) {
        crearNotification(context)
    }

    private fun crearNotification(context: Context){
        val intent = Intent(
            context,
            MainActivity::class.java
        ).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(
            context,
            channelId
        )
            .setSmallIcon(R.drawable.ic_shopping_cart_24)
            .setContentTitle("Notificación Programada")
            .setContentText("Saludos! Aprendiendo a programar notificaciones")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Saludos! esta es una prueba de notificación " +
                            "programada; aparece despues de un tiempo, incluso " +
                            "si la APP esta cerrada. Podemos utilizar las otras " +
                            "características de una notificación que ya se han utilizado. " +
                            "Da click para abrir la App"
                    )
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        manager.notify(
            NOTIFICATION_ID,
            notification
        )
    }
}