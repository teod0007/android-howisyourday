package com.prjproject.tcc.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import com.prjproject.tcc.model.Activity;
import com.prjproject.tcc.model.Category;
import com.prjproject.tcc.model.Day;
import com.prjproject.tcc.model.Profile;
import com.prjproject.tcc.persist.CreateDatabase;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    public int insertDay(Day d) {
        ContentValues values;


        if(d.get_id() == -1 && d.getDay_date() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            String shortDate = sdf.format(d.getDay_date());
            try {
                d.setDay_date(sdf.parse(shortDate));
            } catch (Exception e) {
                e.printStackTrace();
            }
            d.set_id(getDayId(d.getDay_date().getTime(), ""+d.getProfile_id()));

        }

        if(d.get_id() == -1) {
            db = database.getWritableDatabase();
            values = new ContentValues();
            values.put("profile_id", d.getProfile_id());
            values.put("isFuture", d.isFuture());
            values.put("day_date", d.getDay_date() == null ? -1 : d.getDay_date().getTime());
            db.insert("day", null, values);

            db.close();

            d.set_id(getDayId(d.getDay_date() == null ? -1 : d.getDay_date().getTime(), d.getProfile_id() + ""));

            if(!d.isFuture()) {
                db = database.getWritableDatabase();

                for (Activity a : d.getListActivities()) {
                    values.clear();
                    values.put("activity_id", a.get_id());
                    values.put("day_period", a.getDayPeriod());
                    values.put("day_id", d.get_id());
                    db.insert("day_activity", null, values);
                }
                db.close();
            }

            return d.get_id();
        }
        else{
            values = new ContentValues();


            db = database.getWritableDatabase();
            db.delete("day_activity", "day_id = " + d.get_id(), null);

            for(Activity a : d.getListActivities()){
                values.clear();
                values.put("activity_id", a.get_id());
                values.put("day_period", a.getDayPeriod());
                values.put("day_id", d.get_id());
                db.insert("day_activity", null, values);
            }
            db.close();

            return d.get_id();
        }



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

        ArrayList<Activity> listActivities = new ArrayList<>();
        try{
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
            int category_id = cursor.getInt(cursor.getColumnIndexOrThrow("category_id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            byte[] image_bytes = cursor.getBlob(cursor.getColumnIndexOrThrow("activity_image"));
            /*
            BitmapFactory.Options options=new BitmapFactory.Options();// Create object of bitmapfactory's option method for further option use
            options.inPurgeable = true;
            Bitmap image = BitmapFactory.decodeByteArray(image_bytes, 0, image_bytes.length,options);
            Bitmap image2 = Bitmap.createScaledBitmap(image, 100, 100, true);
            */
            Bitmap image2 = decodeSampledBitmapFromByteArray(image_bytes, 80, 80);
            Bitmap image3 = getRoundedCornerBitmap(image2, 10);
            listActivities.add(new Activity(id,category_id,name,image3));
            // The Cursor is now set to the right position
            //listActivities.add(mCursor.getWhateverTypeYouWant(WHATEVER_COLUMN_INDEX_YOU_WANT));
        }
            cursor.close();

            db.close();
        }catch(Exception e){e.printStackTrace();}

        return listActivities;
    }

    public static Bitmap decodeSampledBitmapFromByteArray(byte[] bytes,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap m  = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        options.inPurgeable = true;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public ArrayList<Activity> readActivitiesPerCategory(int category){
        Cursor cursor;
        String[] fields = {"_id","category_id","name","activity_image"};
        db = database.getReadableDatabase();
        cursor = db.rawQuery(
                "SELECT * FROM activity WHERE category_id=?",
                new String[]{"" + category}
        );

        ArrayList<Activity> listActivities = new ArrayList<>();
        try{
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                int category_id = cursor.getInt(cursor.getColumnIndexOrThrow("category_id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                byte[] image_bytes = cursor.getBlob(cursor.getColumnIndexOrThrow("activity_image"));
                /*
                BitmapFactory.Options options=new BitmapFactory.Options();// Create object of bitmapfactory's option method for further option use
                options.inPurgeable = true;
                Bitmap image = BitmapFactory.decodeByteArray(image_bytes, 0, image_bytes.length,options);
                Bitmap image2 = Bitmap.createScaledBitmap(image, 100, 100, true);
                */
                Bitmap image2 = decodeSampledBitmapFromByteArray(image_bytes, 80, 80);
                Bitmap image3 = getRoundedCornerBitmap(image2, 10);
                listActivities.add(new Activity(id,category_id,name,image3));
                // The Cursor is now set to the right position
                //listActivities.add(mCursor.getWhateverTypeYouWant(WHATEVER_COLUMN_INDEX_YOU_WANT));
            }

            if(cursor != null){
                cursor.moveToFirst();
            }
            cursor.close();

            db.close();
        }catch(Exception e){e.printStackTrace();}

        return listActivities;
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public int getDayId(long date, String profile_id){
        Cursor cursor;
        db = database.getReadableDatabase();
        cursor = db.rawQuery(
                "SELECT _id FROM day WHERE day_date=? AND profile_id=?",
                new String[]{date+"",profile_id}
        );
        if(cursor.moveToFirst()){
            return cursor.getInt(cursor.getColumnIndex("_id"));
        }
        db.close();

        return -1;
    }

    public int getDayFuture(String profile_id){
        Cursor cursor;
        db = database.getReadableDatabase();
        cursor = db.rawQuery(
                "SELECT _id FROM day WHERE isFuture=? AND profile_id=?",
                new String[]{"1" , profile_id}
        );
        if(cursor.moveToFirst()){
            return cursor.getInt(cursor.getColumnIndex("_id"));
        }
        db.close();

        return -1;
    }

    public ArrayList<Activity> readActivitiesPerDayId(String day_id, String profile_id, boolean isFuture){
        Cursor cursor;
        db = database.getReadableDatabase();
        String sql = "";
        String[] values;
        if(!isFuture) {
            sql = "SELECT a._id as _id, a.category_id as category_id, a.name as name, a.activity_image as activity_image, " +
                    "da.day_period as day_period FROM activity a, day_activity da WHERE a._id = da.activity_id AND day_id=?";
            values = new String[]{day_id};
        }else{
            sql = "SELECT a._id as _id, a.category_id as category_id, a.name as name, a.activity_image as activity_image, " +
                    "da.day_period as day_period FROM activity a, day_activity da, day d WHERE a._id = da.activity_id AND da.day_id = d._id AND d.profile_id=? AND d.isFuture = ?";
            values = new String[]{profile_id, isFuture ? "1" : "0"};
        }
        try {
            cursor = db.rawQuery(
                    sql,
                    values
            );

        ArrayList<Activity> listActivities = new ArrayList<>();
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                int category_id = cursor.getInt(cursor.getColumnIndexOrThrow("category_id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                byte[] image_bytes = cursor.getBlob(cursor.getColumnIndexOrThrow("activity_image"));
                String day_period = cursor.getString(cursor.getColumnIndexOrThrow("day_period"));
                /*
                BitmapFactory.Options options=new BitmapFactory.Options();// Create object of bitmapfactory's option method for further option use
                options.inPurgeable = true;
                Bitmap image = BitmapFactory.decodeByteArray(image_bytes, 0, image_bytes.length,options);
                Bitmap image2 = Bitmap.createScaledBitmap(image, 100, 100, true);
                */
                Bitmap image2 = decodeSampledBitmapFromByteArray(image_bytes, 80, 80);
                Bitmap image3 = getRoundedCornerBitmap(image2, 10);
                listActivities.add(new Activity(id,category_id,name,image3,day_period));
                // The Cursor is now set to the right position
                //listActivities.add(mCursor.getWhateverTypeYouWant(WHATEVER_COLUMN_INDEX_YOU_WANT));
            }

            if(cursor != null){
                cursor.moveToFirst();
            }
            cursor.close();

            db.close();


            return listActivities;
        }catch(Exception e){e.printStackTrace();}
        return null;
    }

    public Cursor readDaysPerProfileId(String profile_id){
        Cursor cursor;

        String[] fields = {"_id","profile_id","day_date","isFuture"};
        db = database.getReadableDatabase();
        cursor = db.rawQuery(
                "SELECT * FROM day WHERE profile_id=? AND isFuture = 0",
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
