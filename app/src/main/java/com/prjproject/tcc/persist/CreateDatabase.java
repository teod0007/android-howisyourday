package com.prjproject.tcc.persist;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.prjproject.tcc.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by Emanuel on 2016-03-16.
 */

public class CreateDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tcc.db";
    private static final int VERSION = 2;


    public CreateDatabase(Context context){
        super(context, DATABASE_NAME ,null, VERSION);
    }

    @Override public void onCreate(SQLiteDatabase db)
    {
        String sql = "CREATE TABLE profile(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT UNIQUE" +
                ")";
        db.execSQL(sql);
        sql = "CREATE TABLE activity(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "category_id INTEGER," +
                "name TEXT," +
                "activity_image BLOB," +
                "FOREIGN KEY(category_id) REFERENCES category(_id) ON DELETE CASCADE" +
                ")";
        db.execSQL(sql);

        sql = "CREATE TABLE category(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT UNIQUE" +
                ")";
        db.execSQL(sql);
        sql = "CREATE TABLE day(" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "profile_id INTEGER," +
            "isFuture BOOLEAN," +
            "day_date DATE DEFAULT CURRENT_DATE," +
            "FOREIGN KEY(profile_id) REFERENCES profile(_id) ON DELETE CASCADE ON UPDATE CASCADE" +
            ")";
        db.execSQL(sql);
        sql = "CREATE TABLE day_activity(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "activity_id INTEGER," +
                "day_id INTEGER," +
                "day_period TEXT," +
                "FOREIGN KEY(activity_id) REFERENCES activity(_id) ON DELETE CASCADE," +
                "FOREIGN KEY(day_id) REFERENCES day(_id) ON DELETE CASCADE" +
                ")";
        db.execSQL(sql);

    }

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS profile");
        db.execSQL("DROP TABLE IF EXISTS activity");
        db.execSQL("DROP TABLE IF EXISTS category");
        db.execSQL("DROP TABLE IF EXISTS day");
        db.execSQL("DROP TABLE IF EXISTS day_activity");
        onCreate(db);
    }


    ////////////////////////////////////////////
    ////////////////////////////////////////////
    //////////SQL HELPER MANAGER METHOD/////////
    ////////////////////////////////////////////
    ////////////////////////////////////////////

    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "mesage" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);


        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);


            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {


                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){

            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }


    }


}