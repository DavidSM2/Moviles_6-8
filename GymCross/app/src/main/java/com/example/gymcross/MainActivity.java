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
    Spinner spinner;
    GimnasiosAdapter adapter;
    FavDB favDB = new FavDB(this);

    String[] opciones_spinner = {"Ordenar por...", "Nombre ascendente", "Nombre descendente", "Categoria"};

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
        ArrayAdapter<String> adapter_string = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones_spinner);
        adapter_string.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter_string);
        spinner.setSelection(0);

        /*
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (parent.getItemAtPosition(position).toString()){
                    case "Nombre ascendente":
                        Collections.sort(campings, Camping.comparadorNombreAscendente);
                        break;
                    case "Nombre descendente":
                        Collections.sort(campings, Camping.comparadorNombreDescendente);
                        break;
                    case "Categoria":
                        Collections.sort(campings, Camping.comparadorCategoria);
                        break;
                    default:
                        httpConnector = new HTTPConnector();
                        httpConnector.execute();
                        break;
                }

                setupData(campings);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        */
    }

    private void setupData(ArrayList<Gimnasio> gimnasios){
        this.gimnasios = gimnasios;
        adapter = new GimnasiosAdapter(gimnasios, getApplicationContext(),this);
        recyclerView.setAdapter(adapter);
    }
    /*
    public void Ordernar_Descendente(View view) {
        campings_filter = new ArrayList<>();
        Collections.sort(campings, Camping.comparadorNombreDescendente);
        setupData(campings);
    }
    public void Ordernar_Ascendente(View view) {
        campings_filter = new ArrayList<>();
        Collections.sort(campings, Camping.comparadorNombreAscendente);
        setupData(campings);
    }
    */

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        Gimnasio gimnasio = gimnasios.get(position);
        intent.putExtra("gimnasio",gimnasio);
        startActivity(intent);
    }
}