package com.prjproject.tcc.persist;
import android.content.ContentValues;
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
import com.prjproject.tcc.controller.DatabaseController;
import com.prjproject.tcc.model.Activity;
import com.prjproject.tcc.model.Category;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by Emanuel on 2016-03-16.
 */

public class CreateDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tcc.db";
    private static final int VERSION = 11;
    private Resources resources;



    public CreateDatabase(Context context){
        super(context, DATABASE_NAME ,null, VERSION);
        resources = context.getResources();
        //dbController = new DatabaseController(context);
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
            "day_date INTEGER," +
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

        insertData(db);

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

    private void insertData(SQLiteDatabase db){
        /*Drawable drawable = resources.getDrawable(R.drawable.image_fruta);
        Bitmap bitmap =  ((BitmapDrawable)drawable).getBitmap();
        dbController.insertActivity(new Activity(1, "Macarrão 1", bitmap ));*/
        /*dbController.insertCategory(new Category("comida"));
        dbController.insertCategory(new Category("social"));
        dbController.insertCategory(new Category("med_hig"));
        dbController.insertCategory(new Category("diversao"));
        dbController.insertCategory(new Category("estudo"));*/
        db.execSQL("INSERT INTO category (name) values ('comida')");
        db.execSQL("INSERT INTO category (name) values ('social')");
        db.execSQL("INSERT INTO category (name) values ('med_hig')");
        db.execSQL("INSERT INTO category (name) values ('diversao')");
        db.execSQL("INSERT INTO category (name) values ('estudo')");


        manualInsert(db,1, R.drawable.image_fruta, "fruta");
        manualInsert(db,1,R.drawable.image_cha, "chá");
        manualInsert(db,1,R.drawable.image_macarrao, "macarrão");
        manualInsert(db,1,R.drawable.image_pao, "pão");
        //manualInsert(1,R.drawable.image_guarana, "guaraná");
        manualInsert(db,1,R.drawable.image_suco, "suco");
        manualInsert(db,1,R.drawable.image_bolacha, "bolacha");
        manualInsert(db,1,R.drawable.image_leite, "leite");
        manualInsert(db,1,R.drawable.image_arroz, "arroz");
        manualInsert(db,1,R.drawable.image_feijao, "feijão");
        manualInsert(db,1,R.drawable.image_carne, "carne");
        manualInsert(db,1,R.drawable.image_salada, "salada");
        manualInsert(db,1,R.drawable.image_pizza, "pizza");
        manualInsert(db,1,R.drawable.image_sorvete, "sorvete");

        manualInsert(db,2,R.drawable.image_mae, "mãe");
        manualInsert(db,2,R.drawable.image_pai, "pai");
        manualInsert(db,2,R.drawable.image_irmao, "irmão");
        manualInsert(db,2,R.drawable.image_tio, "tio");
        manualInsert(db,2,R.drawable.image_avos, "avos");
        manualInsert(db,2,R.drawable.image_amigo_adulto, "amigo adulto");
        manualInsert(db,2,R.drawable.image_amigo_crianca, "amigo criança");

        manualInsert(db,3,R.drawable.image_medico, "medico");
        manualInsert(db,3,R.drawable.image_enfermeiro, "enfermeira");
        manualInsert(db,3,R.drawable.image_medicacao, "medicação");
        manualInsert(db,3,R.drawable.image_exame, "exame");
        manualInsert(db,3,R.drawable.image_soro, "soro");
        manualInsert(db,3,R.drawable.image_banho, "banho");
        manualInsert(db,3,R.drawable.image_repouso, "repouso");
        manualInsert(db,3,R.drawable.image_pegar_veia, "pegar veia");
        manualInsert(db,3,R.drawable.image_escovar_dentes, "escovar os dentes");
        manualInsert(db,3,R.drawable.image_pentear_cabelo, "pentear os cabelos");
        manualInsert(db,3,R.drawable.image_vestir_roupa, "vestir roupa");
        manualInsert(db,3,R.drawable.image_calcar_sapato, "calçar sapato");
        manualInsert(db,3,R.drawable.image_raio_x, "raio x");
        manualInsert(db,3,R.drawable.image_tomografia, "tomografia");
        manualInsert(db,3,R.drawable.image_fisioterapia, "fisioterapia");
        manualInsert(db,3,R.drawable.image_tirar_sangue, "tirar sangue");
        manualInsert(db,3,R.drawable.image_sonda, "Sonda");

        manualInsert(db,4,R.drawable.image_tablet, "tablet");
        manualInsert(db,4,R.drawable.image_celular, "celular");
        manualInsert(db,4,R.drawable.image_tv, "TV");
        manualInsert(db,4,R.drawable.image_jogos, "jogos");
        manualInsert(db,4,R.drawable.image_livros, "livros");
        manualInsert(db,4,R.drawable.image_dormir, "dormir");
        manualInsert(db,4,R.drawable.image_computador, "computador");
        manualInsert(db,4,R.drawable.image_meu_jogo, "jogo");
        manualInsert(db,4,R.drawable.image_bicicleta, "bicicleta");
        manualInsert(db,4,R.drawable.image_futebol, "futebol");
        manualInsert(db,4,R.drawable.image_patins, "patins");
        manualInsert(db,4,R.drawable.image_surfar, "surfar");
        //manualInsert(4,R.drawable.image_mae, "patins");
        manualInsert(db,4,R.drawable.image_skate, "skate");
        manualInsert(db,4,R.drawable.image_viajar, "viajar");
        manualInsert(db,4,R.drawable.image_cinema, "cinema");
        manualInsert(db,4,R.drawable.image_parque, "parque");
        manualInsert(db,4,R.drawable.image_zoo, "zoológico");
        manualInsert(db,4,R.drawable.image_shopping, "shopping");

        manualInsert(db,5,R.drawable.image_portugues, "português");
        manualInsert(db,5,R.drawable.image_matematica, "matemática");
        manualInsert(db,5,R.drawable.image_ciencias, "ciências");
        manualInsert(db,5,R.drawable.image_artes, "artes");
        manualInsert(db,5,R.drawable.image_hist_geo, "história e geografia");
        manualInsert(db,5,R.drawable.image_ativ_prof, "atividade com o(a) professor(a)");
        manualInsert(db,5,R.drawable.image_ed_fis, "educação física");
        manualInsert(db,5,R.drawable.image_linguas, "línguas");

    }

    private void manualInsert(SQLiteDatabase db, int cat,int id, String name){
        ContentValues values;
        long result;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        byte[] bArray = bos.toByteArray();
        Drawable drawable = resources.getDrawable(id);
        Bitmap bitmap =  ((BitmapDrawable)drawable).getBitmap();
        Activity a = new Activity(cat, name , bitmap );
        values = new ContentValues();
        values.put("category_id", a.getCategory_id());
        values.put("name", a.getName());
        a.getActivityImage().compress(Bitmap.CompressFormat.JPEG, 100, bos);
        values.put("activity_image", bos.toByteArray());
        result = db.insert("activity", null, values);

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