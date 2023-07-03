package com.example.prototipo.components

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.prototipo.MainActivity
import com.example.prototipo.R
import com.example.prototipo.utils.Constants.channelId

// Clase la cual va armar la notificación programada esta hereda del la clase BroadcastReceiver.
class NotificacionProgramada: BroadcastReceiver() {
    //objeto que no requiere ser instanciado de una clase.

    companion object{
        const val NOTIFICATION_ID = 5
    }

    override fun onReceive(
        context: Context,
        intent: Intent?
    ){
        crearNotification(context)
    }

    // Función la cual va crear la notificación programada.
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
            .setContentTitle("¡Va a realizar una compra!")
            .setContentText("Seguir las instrucciones para adquirir su producto.")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("¡Hola! éste es el sistema de facturación de productos "+
                            "CBA; le recomendamos seguir las instrucciones, así " +
                            "su compra se realizará de forma eficiente. En caso que el servicio " +
                            "no cumpla con sus necesidades comuníquese al centro de aprendizaje. " +
                            "Da click para abrir la APP"
                    )
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE)
        as NotificationManager
        manager.notify(
            NOTIFICATION_ID,
            notification
        )
    }
}