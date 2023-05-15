package com.example.gymcross;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface{
    RecyclerView recyclerView;
    EditText editTextBusqueda;
    ArrayList<Gimnasio> gimnasios;
    ArrayList<Gimnasio> gimnasios_filter;
    Spinner spinner;
    GimnasiosAdapter adapter;
    FavDB favDB = new FavDB(this);

    FloatingActionButton reminderButton;

    String[] opciones_spinner = {"Ordenar por...", "Nombre ascendente", "Nombre descendente"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_gimnasios);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        editTextBusqueda = findViewById(R.id.textoBusqueda);
        spinner = findViewById(R.id.gim_spinner);
        gimnasios = new ArrayList<Gimnasio>();
        gimnasios = favDB.read_all_data();
        reminderButton = findViewById(R.id.buttonFloating);
        setupData(gimnasios);
        ArrayAdapter<String> adapter_string = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones_spinner);
        adapter_string.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter_string);
        spinner.setSelection(0);

        editTextBusqueda.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String textoBusqueda = charSequence.toString().toLowerCase();

                if ("".equals(textoBusqueda)){
                    setupData(gimnasios);
                }

                else {
                    gimnasios_filter = new ArrayList<>();
                    for (Gimnasio gimnasio : gimnasios) {
                        if (gimnasio.getNombre().toLowerCase().contains(textoBusqueda.toLowerCase())) {
                            gimnasios_filter.add(gimnasio);

                        }
                    }

                    setupData(gimnasios_filter);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        reminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ReminderActivity.class);
                startActivity(intent);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (parent.getItemAtPosition(position).toString()){
                    case "Nombre ascendente":
                        Collections.sort(gimnasios, Gimnasio.comparadorNombreAscendente);
                        break;
                    case "Nombre descendente":
                        Collections.sort(gimnasios, Gimnasio.comparadorNombreDescendente);
                        break;
                    default:
                        gimnasios = favDB.read_all_data();
                        break;
                }

                setupData(gimnasios);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setupData(ArrayList<Gimnasio> _gimnasios){
        adapter = new GimnasiosAdapter(_gimnasios, getApplicationContext(),this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        Gimnasio gimnasio = gimnasios.get(position);
        intent.putExtra("gimnasio",gimnasio);
        startActivity(intent);
    }
}