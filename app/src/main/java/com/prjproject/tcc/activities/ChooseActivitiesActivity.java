package com.prjproject.tcc.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;

import com.prjproject.tcc.R;
import com.prjproject.tcc.adapters.GridAdapter;
import com.prjproject.tcc.adapters.ImageAdapter;
import com.prjproject.tcc.controller.DatabaseController;
import com.prjproject.tcc.listeners.RecyclerItemClickListener;
import com.prjproject.tcc.model.Activity;
import com.prjproject.tcc.model.Category;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChooseActivitiesActivity extends AppCompatActivity implements View.OnClickListener{
    private Resources resources;
    private DatabaseController dbController;
    private ArrayList<Activity> listFood;
    private ArrayList<Activity> listMedicine;
    private ArrayList<Activity> listMisc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_activities);
        dbController = new DatabaseController(getApplicationContext());
        try {
            getActivitiesFromDB();
            setupButtons();
            setupRecycleViewLayouts();
            setViewAdapters();
            setRecycleViewListeners();
            resources = getResources();
        }catch(Exception ex){

        }
        /*Drawable drawable = resources.getDrawable(R.drawable.image_fruta);
        Bitmap bitmap =  ((BitmapDrawable)drawable).getBitmap();
        dbController.insertActivity(new Activity(1, "Macarrão 1", bitmap ));*/
        /*dbController.insertCategory(new Category("comida"));
        dbController.insertCategory(new Category("social"));
        dbController.insertCategory(new Category("med_hig"));
        dbController.insertCategory(new Category("diversao"));
        dbController.insertCategory(new Category("estudo"));


        manualInsert(1, R.drawable.image_fruta, "fruta");
        manualInsert(1,R.drawable.image_cha, "chá");
        manualInsert(1,R.drawable.image_macarrao, "macarrão");
        manualInsert(1,R.drawable.image_pao, "pão");
        //manualInsert(1,R.drawable.image_guarana, "guaraná");
        manualInsert(1,R.drawable.image_suco, "suco");
        manualInsert(1,R.drawable.image_bolacha, "bolacha");
        manualInsert(1,R.drawable.image_leite, "leite");
        manualInsert(1,R.drawable.image_arroz, "arroz");
        manualInsert(1,R.drawable.image_feijao, "feijão");
        manualInsert(1,R.drawable.image_carne, "carne");
        manualInsert(1,R.drawable.image_salada, "salada");
        manualInsert(1,R.drawable.image_pizza, "pizza");
        manualInsert(1,R.drawable.image_sorvete, "sorvete");

        manualInsert(2,R.drawable.image_mae, "mãe");
        manualInsert(2,R.drawable.image_pai, "pai");
        manualInsert(2,R.drawable.image_irmao, "irmão");
        manualInsert(2,R.drawable.image_tio, "tio");
        manualInsert(2,R.drawable.image_avos, "avos");
        manualInsert(2,R.drawable.image_amigo_adulto, "amigo adulto");
        manualInsert(2,R.drawable.image_amigo_crianca, "amigo criança");

        manualInsert(3,R.drawable.image_medico, "medico");
        manualInsert(3,R.drawable.image_enfermeiro, "enfermeira");
        manualInsert(3,R.drawable.image_medicacao, "medicação");
        manualInsert(3,R.drawable.image_exame, "exame");
        manualInsert(3,R.drawable.image_soro, "soro");
        manualInsert(3,R.drawable.image_banho, "banho");
        manualInsert(3,R.drawable.image_repouso, "repouso");
        manualInsert(3,R.drawable.image_pegar_veia, "pegar veia");
        manualInsert(3,R.drawable.image_escovar_dentes, "escovar os dentes");
        manualInsert(3,R.drawable.image_pentear_cabelo, "pentear os cabelos");
        manualInsert(3,R.drawable.image_vestir_roupa, "vestir roupa");
        manualInsert(3,R.drawable.image_calcar_sapato, "calçar sapato");
        manualInsert(3,R.drawable.image_raio_x, "raio x");
        manualInsert(3,R.drawable.image_tomografia, "tomografia");
        manualInsert(3,R.drawable.image_fisioterapia, "fisioterapia");
        manualInsert(3,R.drawable.image_tirar_sangue, "tirar sangue");
        manualInsert(3,R.drawable.image_sonda, "Sonda");

        manualInsert(4,R.drawable.image_tablet, "tablet");
        manualInsert(4,R.drawable.image_celular, "celular");
        manualInsert(4,R.drawable.image_tv, "TV");
        manualInsert(4,R.drawable.image_jogos, "jogos");
        manualInsert(4,R.drawable.image_livros, "livros");
        manualInsert(4,R.drawable.image_dormir, "dormir");
        manualInsert(4,R.drawable.image_computador, "computador");
        manualInsert(4,R.drawable.image_meu_jogo, "jogo");
        manualInsert(4,R.drawable.image_bicicleta, "bicicleta");
        manualInsert(4,R.drawable.image_futebol, "futebol");
        manualInsert(4,R.drawable.image_patins, "patins");
        manualInsert(4,R.drawable.image_surfar, "surfar");
        //manualInsert(4,R.drawable.image_mae, "patins");
        manualInsert(4,R.drawable.image_skate, "skate");
        manualInsert(4,R.drawable.image_viajar, "viajar");
        manualInsert(4,R.drawable.image_cinema, "cinema");
        manualInsert(4,R.drawable.image_parque, "parque");
        manualInsert(4,R.drawable.image_zoo, "zoológico");
        manualInsert(4,R.drawable.image_shopping, "shopping");

        manualInsert(5,R.drawable.image_portugues, "português");
        manualInsert(5,R.drawable.image_matematica, "matemática");
        manualInsert(5,R.drawable.image_ciencias, "ciências");
        manualInsert(5,R.drawable.image_artes, "artes");
        manualInsert(5,R.drawable.image_hist_geo, "história e geografia");
        manualInsert(5,R.drawable.image_ativ_prof, "atividade com o(a) professor(a)");
        manualInsert(5,R.drawable.image_ed_fis, "educação física");
        manualInsert(5,R.drawable.image_linguas, "línguas");*/


    }

    private void getActivitiesFromDB() {
        listFood = dbController.readActivitiesPerCategory(1);
        listMedicine = dbController.readActivitiesPerCategory(2);
        listMisc = dbController.readActivitiesPerCategory(3);
    }

    private void manualInsert(int cat,int id, String name){
        Drawable drawable = resources.getDrawable(id);
        Bitmap bitmap =  ((BitmapDrawable)drawable).getBitmap();
        dbController.insertActivity(new Activity(cat, name , bitmap ));

    }

    private void setRecycleViewListeners() {
        RecyclerView listViewFood = (RecyclerView) findViewById(R.id.listViewFood);
        RecyclerView listViewMedicine = (RecyclerView) findViewById(R.id.listViewMedicine);
        RecyclerView listViewMisc = (RecyclerView) findViewById(R.id.listViewMisc);
        RecyclerItemClickListener commonListener = new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Object activity =  (((ImageAdapter) ((RecyclerView) view.getParent()).getAdapter()).getItem(position));
                        GridView gridViewActivities = (GridView)findViewById(R.id.gridViewActivities);
                        ((GridAdapter)gridViewActivities.getAdapter()).addItem(activity);
                    }
        });
        listViewFood.addOnItemTouchListener(commonListener);
        listViewMedicine.addOnItemTouchListener(commonListener);
        listViewMisc.addOnItemTouchListener(commonListener);


    }

    private void setupButtons() {
        Button btnNext = (Button) findViewById(R.id.btnNext);

        btnNext.setOnClickListener(this);
    }

    //Click handling
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnNext:
                btnNextClick();
                break;
        }
    }

    private void btnNextClick() {
        GridView gridViewActivities = (GridView)findViewById(R.id.gridViewActivities);
        List<Object> activities =  ((GridAdapter)gridViewActivities.getAdapter()).getItems();
        int index = 0;
        int[] idList = new int[activities.size()];
        for(Object a : activities){
            idList[index++] = ((Activity)a).get_id();
        }

        Intent intent = new Intent(getApplicationContext(), DistributeActivitiesActivity.class);
        intent.putExtra("idList",idList);
        startActivity(intent);
    }

    private void setViewAdapters(){
        RecyclerView listViewFood = (RecyclerView) findViewById(R.id.listViewFood);//dbController.readActivities()
        RecyclerView listViewMedicine = (RecyclerView) findViewById(R.id.listViewMedicine);
        RecyclerView listViewMisc = (RecyclerView) findViewById(R.id.listViewMisc);
        GridView gridViewActivities = (GridView) findViewById(R.id.gridViewActivities);

        listViewFood.setAdapter(new ImageAdapter(listFood));
        listViewMedicine.setAdapter(new ImageAdapter(listMedicine));
        listViewMisc.setAdapter(new ImageAdapter(listMisc));
        gridViewActivities.setAdapter(new GridAdapter(getApplicationContext()));

    }

    private void setupRecycleViewLayouts(){

        RecyclerView listViewFood = (RecyclerView) findViewById(R.id.listViewFood);
        listViewFood.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        RecyclerView listViewMedicine = (RecyclerView) findViewById(R.id.listViewMedicine);
        listViewMedicine.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        RecyclerView listViewMisc = (RecyclerView) findViewById(R.id.listViewMisc);
        listViewMisc.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

    }
}
