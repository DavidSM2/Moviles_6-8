package com.example.gymcross;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class DetailActivity extends AppCompatActivity {
    Gimnasio gimnasio;
    Location actLocation;
    Double distanciaGimnasio = 0.0;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        gimnasio = (Gimnasio) getIntent().getSerializableExtra("gimnasio");
        TextView textMunicipio = findViewById(R.id.Municipio);
        TextView textNombre = findViewById(R.id.Name);
        TextView textCorreo = findViewById(R.id.Correo);
        TextView textComunidadAutonoma = findViewById(R.id.ComunidadAutonoma);
        TextView textDistancia = findViewById(R.id.Distancia);
        TextView textDireccion = findViewById(R.id.Direccion);

        textNombre.setText(gimnasio.getNombre());
        textMunicipio.setText(gimnasio.getMunicipio() + " (" + gimnasio.getProvincia() + ")");
        textCorreo.setText(gimnasio.getCorreo());
        textComunidadAutonoma.setText(gimnasio.getComunidad_autonoma());
        textDireccion.setText(gimnasio.getDireccion() + " (" + gimnasio.getCp() + ")");

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                actLocation = location;
            }

            @Override
            public void onLocationChanged(@NonNull List<Location> locations) {
                LocationListener.super.onLocationChanged(locations);
            }

            @Override
            public void onFlushComplete(int requestCode) {
                LocationListener.super.onFlushComplete(requestCode);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                LocationListener.super.onStatusChanged(provider, status, extras);
            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {
                LocationListener.super.onProviderEnabled(provider);
            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
                LocationListener.super.onProviderDisabled(provider);
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                actLocation = location;
                distanciaGimnasio = calcularDistancia();
            }
        }

        textDistancia.setText(distanciaGimnasio + "Km");

    }

    private double calcularDistancia() {
        double distancia = 0;

        if (actLocation != null) {
            double longitud1 = actLocation.getLongitude();
            double longitud2 = Double.parseDouble(gimnasio.getLongitud());
            double latitud1 = actLocation.getLatitude();
            double latitud2 = Double.parseDouble(gimnasio.getLatitud());

            double radioTierra = 6371;
            double dLat = Math.toRadians(latitud2 - latitud1);
            double dLon = Math.toRadians(longitud2 - longitud1);
            double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                    Math.cos(Math.toRadians(latitud1)) * Math.cos(Math.toRadians(latitud2)) *
                            Math.sin(dLon / 2) * Math.sin(dLon / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            distancia = Math.round(radioTierra * c);
        }

        return distancia;
    }
}