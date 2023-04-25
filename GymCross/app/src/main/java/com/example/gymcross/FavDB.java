package com.example.gymcross;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.MutableContextWrapper;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FavDB extends SQLiteOpenHelper {

    Gimnasio[] gimnasiosBase = new Gimnasio[]{
            new Gimnasio("1", "VITAL FITNESS C.A.F. Y R. S.L.", "A CORUÑA" , "A POBRA DO CARAMIÑAL",
                                 "15940", "vitalfitness.vtf@gmail.com", "SAN ROQUE, 22 BJ",
                                 "GALICIA", "42.6035461426", "-8.9414758682"),

            new Gimnasio("2", "OZONO BOIRO S.L.", "A CORUÑA", "BOIRO",
                                 "15930", "ayuda@ueni.com", "AVENIDA CONSTITUCIÓN, 81 SOTANO",
                                 "GALICIA", "42.6444969177", "-8.8894739151"),

            new Gimnasio("3", "BEMEQUER S.L.", "A CORUÑA", "SANTIAGO DE COMPOSTELA",
                                 "15702", "bemequer@bemequer.com", "LUIS IGLESIAS IGLESIAS, 7 BAJO",
                                 "GALICIA", "42.8653301", "-8.5442773"),

            new Gimnasio("4", "CLUB CAMM FITNESS & FIGHT S.L.", "A CORUÑA", "SANTIAGO DE COMPOSTELA",
                                 "15706", "info@clubcamm.es", "AVENIDA SYRA ALONSO (URBANIZACIÓN SANTA MARTA DE ABAIXO), 2 BAJO",
                                 "GALICIA", "42.865069045", "-8.563838645"),

            new Gimnasio("5", "CURVES S.L.", "A CORUÑA", "SANTIAGO DE COMPOSTELA",
                                 "15701", "curves.santiago@hotmail.com", "RUA A ROSA, 42 BAJO",
                                 "GALICIA", "42.870821556", "-8.549290371"),

            new Gimnasio("6", "FACTORYPILATES S.L.", "A CORUÑA", "SANTIAGO DE COMPOSTELA",
                                 "15701", "factorypilates@gmail.com", "DIEGO DE MUROS, 15 BAJO",
                                 "GALICIA", "42.872959137", "-8.5487270355"),

            new Gimnasio("7", "LAUDIO WELLNES S.L.", "ÁLAVA", "LAUDIO/LLODIO",
                                 "01400", "laudiowellnes@gmail.com", "AVENIDA ZUMALAKARREGI, 25 A",
                                 "PAÍS VASCO", "43.144176195", "-2.961708273"),

            new Gimnasio("8", "CURVES GIMNASIO FEMENINO S.L.", "ÁLAVA", "VITORIA-GASTEIZ",
                                 "01012", "curveslegal@curves.eu", "AVENIDA GASTEIZ, 82",
                                 "PAÍS VASCO", "42.85344325", "-2.679889907"),

            new Gimnasio("9", "GYM FITNES GASTEIZ S.L.", "ÁLAVA", "VITORIA-GASTEIZ",
                                 "01002", "karatefitnessgasteiz@gmail.com", "CALLE LOS HERRÁN, 98",
                                 "PAÍS VASCO", "42.853891489", "-2.667004373")
    };




    private static int DB_VERSION = 1;
    private static String DATABASE_NAME = "GymsDB";
    private static String TABLE_NAME = "GymTable";
    public static String KEY_ID = "id";
    public static String GYM_NAME = "gymName";
    public static String PROVINCIA = "provincia";
    public static String MUNICIPIO = "municipio";
    public static String CP = "cp";
    public static String CORREO = "correo";
    public static String DIRECCION = "direccion";
    public static String COMUNIDAD_AUTONOMA = "comunidad_autonoma";
    public static String LATITUD = "latitud";
    public static String LONGITUD = "longitud";
    private static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
                            KEY_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                            GYM_NAME + " TEXT, " +
                            PROVINCIA + " TEXT, " +
                            MUNICIPIO + " TEXT, " +
                            CP + " TEXT, " +
                            CORREO + " TEXT," +
                            DIRECCION + " TEXT," +
                            COMUNIDAD_AUTONOMA + " TEXT," +
                            LATITUD + " TEXT," +
                            LONGITUD + " TEXT)";

    private static String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public FavDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        init();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_TABLE);
        onCreate(db);
    }

    public void init(){
        if (read_all_data() == null){
            for(Gimnasio gim : gimnasiosBase)
            {
                insertGimnasio(gim);
            }
        }
    }

    public void insertGimnasio (Gimnasio gimnasio) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, gimnasio.getId());
        values.put(GYM_NAME, gimnasio.getNombre());
        values.put(PROVINCIA, gimnasio.getProvincia());
        values.put(MUNICIPIO, gimnasio.getMunicipio());
        values.put(CP, gimnasio.getCp());
        values.put(CORREO, gimnasio.getCorreo());
        values.put(DIRECCION, gimnasio.getDireccion());
        values.put(COMUNIDAD_AUTONOMA, gimnasio.getComunidad_autonoma());
        values.put(LATITUD, gimnasio.getLatitud());
        values.put(LONGITUD, gimnasio.getLongitud());
        db.insert(TABLE_NAME,null,values);

        db.close();
    }
    public void deleteGimnasio (int id) {
        // Define 'where' part of query.
        String selection = KEY_ID + " LIKE ?";
        String idcast = id +"";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {idcast};

        // Issue SQL statement.
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete(TABLE_NAME, selection,selectionArgs);

        db.close();
    }

    public ArrayList<Gimnasio>  read_all_data () {
        ArrayList<Gimnasio> GimnasioList = new ArrayList<Gimnasio>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Gimnasio gimnasio = new Gimnasio(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9)
                );

                // Adding contact to list
                GimnasioList.add(gimnasio);
            } while (cursor.moveToNext());
        }

        // return contact list
        return GimnasioList;
    }
}
