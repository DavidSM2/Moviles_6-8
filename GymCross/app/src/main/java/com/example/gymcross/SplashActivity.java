package com.example.gymcross;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {
    FavDB favDB = new FavDB(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ArrayList<Gimnasio> gimnasios = favDB.read_all_data();

        if (gimnasios.isEmpty())
            favDB.init();

        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
    }
}