package com.example.gymcross;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class ReminderActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "mi_canal";
    private DatePicker datePicker;
    private TimePicker timePicker;
    private EditText editText;
    private Button setReminderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);
        editText = findViewById(R.id.editText);
        setReminderButton = findViewById(R.id.setReminderButton);

        setReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createReminder();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Mi Canal", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void createReminder() {

        // Obtener la fecha y hora seleccionadas por el usuario
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        // Crear un objeto Calendar con la fecha y hora seleccionadas
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);

        String reminderText = editText.getText().toString();

        NotificationHelper.programarNotificacion(ReminderActivity.this, reminderText, "", calendar.getTimeInMillis());

        /*

        // Crear el intent para la alarma
        Intent intent = new Intent(this, ReminderBroadcastReceiver.class);
        intent.putExtra("reminder_text", reminderText);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Obtener el sistema de alarmas del dispositivo
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Configurar la alarma con el tiempo seleccionado
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

         */

        // Valores necesarios para añadir el evento al calendario
        ContentResolver cr = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, calendar.getTimeInMillis());
        values.put(CalendarContract.Events.DTEND, calendar.getTimeInMillis() + 60 * 60 * 1000);
        values.put(CalendarContract.Events.TITLE, reminderText);
        values.put(CalendarContract.Events.CALENDAR_ID, 1);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, Calendar.getInstance().getTimeZone().getID());

        // Uri para comprobar si el evento se ha introducido correctamente
        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);

        if (uri != null) {
            Toast.makeText(this, "Recordatorio creado", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al crear el recordatorio", Toast.LENGTH_SHORT).show();
        }
    }
}