package com.prjproject.tcc.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.prjproject.tcc.persist.CreateDatabase;

import java.util.List;
import java.util.Objects;

/**
 * Created by Emanuel on 2016-03-16.
 */
public class DatabaseController {
    private SQLiteDatabase db;
    private CreateDatabase database;

    public DatabaseController(Context context){
        database = new CreateDatabase(context);
    }


    public boolean insertActivity(){
        return false;
    }

    public boolean insertDays(){
        return false;
    }

    public boolean insertCategory(){
        return false;
    }

    public boolean insertProfile(){
        return false;
    }

    public Cursor readActivities(){
        Cursor cursor;
        String[] fields = {"_id","category_id","day_id","name","day_period","activity_image"};
        db = database.getReadableDatabase();
        cursor = db.query("activity", fields, null, null, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor readDays(){
        Cursor cursor;
        String[] fields = {"_id","profile_id","day_date","isFuture"};
        db = database.getReadableDatabase();
        cursor = db.query("day", fields, null, null, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor readCategories(){
        Cursor cursor;
        String[] fields = {"_id","name"};
        db = database.getReadableDatabase();
        cursor = db.query("category", fields, null, null, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor readProfiles(){
        Cursor cursor;
        String[] fields = {"_id","name"};
        db = database.getReadableDatabase();
        cursor = db.query("profile", fields, null, null, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }


    /*
    public String insereDado(String titulo, String autor, String editora){
        ContentValues valores;
        long resultado;
        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(CreateDatabase.TITULO, titulo);
        valores.put(CreateDatabase.AUTOR, autor);
        valores.put(CreateDatabase.EDITORA, editora);
        resultado = db.insert(CreateDatabase.TABELA, null, valores);
        db.close();
        if (resultado ==-1)
            return "Erro ao inserir registro";
        else return "Registro Inserido com sucesso";
    */
}
