package com.example.eag.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    //Constructor
    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //Método para crear BD
    @Override
    public void onCreate(SQLiteDatabase BaseDeDatos) {

        //Creamos instancia a nuestra clase de BD
        BaseDeDatos.execSQL("create table articulos (codigo int primary key, nombre text, precio real)");


    }

    //Método para actualizar BD
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
