package com.example.gymcross;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

public class NotificationHelper {

    private static final int ALARM_REQUEST_CODE = 1;

    public static void programarNotificacion(Context context, String titulo, String mensaje, long tiempoMillis) {
        // Crear un Intent para la clase que manejará la notificación
        Intent intent = new Intent(context, ReminderBroadcastReceiver.class);
        intent.putExtra("titulo", titulo);
        intent.putExtra("mensaje", mensaje);

        // Crear un PendingIntent con el Intent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Obtener una instancia de AlarmManager
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Programar la notificación para el momento específico
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, tiempoMillis, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, tiempoMillis, pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, tiempoMillis, pendingIntent);
        }
    }
}

