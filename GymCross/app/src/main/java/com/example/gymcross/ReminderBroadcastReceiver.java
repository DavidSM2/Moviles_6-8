package com.example.gymcross;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ReminderBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String reminderText = intent.getStringExtra("reminder_text");
        Toast.makeText(context, "Recordatorio: " + reminderText, Toast.LENGTH_SHORT).show();
    }
}
