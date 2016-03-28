package com.prjproject.tcc.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.prjproject.tcc.model.Activity;
import com.prjproject.tcc.model.Category;
import com.prjproject.tcc.model.Day;
import com.prjproject.tcc.model.Profile;
import com.prjproject.tcc.persist.CreateDatabase;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.util.ArrayList;
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


    public boolean insertActivity(Activity a){
        ContentValues values;
        long result;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] bArray = bos.toByteArray();

        db = database.getWritableDatabase();
        values = new ContentValues();
        values.put("category_id", a.getCategory_id());
        values.put("name", a.getName());
        a.getActivityImage().compress(Bitmap.CompressFormat.JPEG, 100, bos);
        values.put("activity_image", bos.toByteArray());
        result = db.insert("activity", null, values);
        db.close();

        return result > 0;
    }

    public boolean insertDay(Day d) {
        ContentValues values;
        long result;
        db = database.getWritableDatabase();
        values = new ContentValues();
        values.put("profile_id", d.getProfile_id());
        values.put("isFuture", d.isFuture());
        result = db.insert("day", null, values);
        db.close();

        return result > 0;
    }

    public boolean insertCategory(Category c){
        ContentValues values;
        long result;
        db = database.getWritableDatabase();
        values = new ContentValues();
        values.put("name", c.getName());
        result = db.insert("category", null, values);
        db.close();

        return result > 0;
    }

    public boolean insertProfile(Profile p){

        ContentValues values;
        long result;
        db = database.getWritableDatabase();
        values = new ContentValues();
        values.put("name", p.getName());
        result = db.insert("profile", null, values);
        db.close();

        return result > 0;
    }

    public ArrayList<Activity> readActivities(){
        Cursor cursor;
        String[] fields = {"_id","category_id","name","activity_image"};
        db = database.getReadableDatabase();
        cursor = db.query("activity", fields, null, null, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        db.close();

        ArrayList<Activity> listActivities = new ArrayList<>();

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
            int category_id = cursor.getInt(cursor.getColumnIndexOrThrow("category_id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            byte[] image_bytes = cursor.getBlob(cursor.getColumnIndexOrThrow("activity_image"));
            BitmapFactory.Options options=new BitmapFactory.Options();// Create object of bitmapfactory's option method for further option use
            options.inPurgeable = true;
            Bitmap image = BitmapFactory.decodeByteArray(image_bytes, 0, image_bytes.length,options);
            Bitmap image2 = Bitmap.createScaledBitmap(image, 100, 100, true);
            listActivities.add(new Activity(id,category_id,name,image2));
            // The Cursor is now set to the right position
            //listActivities.add(mCursor.getWhateverTypeYouWant(WHATEVER_COLUMN_INDEX_YOU_WANT));
        }


        return listActivities;
    }

    public Cursor readActivitiesPerDayId(String day_id){
        Cursor cursor;
        String[] fields = {"_id","category_id","day_id","name","day_period","activity_image"};
        db = database.getReadableDatabase();
        cursor = db.rawQuery(
                "SELECT * FROM activity WHERE day_id=?",
                new String[]{day_id}
        );
        db.close();
        return cursor;
    }

    public Cursor readDaysPerProfileId(String profile_id){
        Cursor cursor;

        String[] fields = {"_id","profile_id","day_date","isFuture"};
        db = database.getReadableDatabase();
        cursor = db.rawQuery(
                "SELECT * FROM day WHERE profile_id=?",
                new String[] {profile_id}
        );
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

    public Cursor readProfilePerName(String name) {
        Cursor cursor;
        String[] fields = {"_id","name"};
        db = database.getReadableDatabase();
        cursor = db.rawQuery(
                "SELECT * FROM profile WHERE name=?",
                new String[] {name}
        );
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
