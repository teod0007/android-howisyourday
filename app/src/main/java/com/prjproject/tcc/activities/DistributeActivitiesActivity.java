package com.prjproject.tcc.activities;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;

import com.prjproject.tcc.R;
import com.prjproject.tcc.adapters.GridAdapter;
import com.prjproject.tcc.adapters.ImageAdapter;
import com.prjproject.tcc.controller.DatabaseController;
import com.prjproject.tcc.listeners.RecyclerItemClickListener;
import com.prjproject.tcc.model.Activity;

import java.util.ArrayList;

public class DistributeActivitiesActivity extends AppCompatActivity implements View.OnClickListener{
    private Resources resources;
    private DatabaseController dbController;
    private ArrayList<Activity> listFood;
    private ArrayList<Activity> listMedicine;
    private ArrayList<Activity> listMisc;
    private int[] arrayUndistributedIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distribute_activities);
        dbController = new DatabaseController(getApplicationContext());
        listFood = new ArrayList<>();
        listMedicine = new ArrayList<>();
        listMisc = new ArrayList<>();
        getIdsFromIntent();
        setupButtons();
        setupRecycleViewLayouts();
        setViewAdapters();
        setRecycleViewListeners();
        resources = getResources();

        /*Drawable drawable = resources.getDrawable(R.drawable.image_macarrao);
        Bitmap bitmap =  ((BitmapDrawable)drawable).getBitmap();
        dbController.insertActivity(new Activity(1, "Macarrão 1", bitmap ));
        dbController.insertActivity(new Activity(1, "Macarrão 2", bitmap ));
        dbController.insertActivity(new Activity(1, "Macarrão 3", bitmap ));*/

    }

    private void getIdsFromIntent() {
        Bundle extras = getIntent().getExtras();
        arrayUndistributedIds = extras.getIntArray("idList");

        ArrayList<Activity> activities = dbController.readActivities();

        for(int i = 0; i < arrayUndistributedIds.length; i++){
            for(Activity a : activities){
                if(a.get_id() == arrayUndistributedIds[i]){
                    switch (a.getCategory_id()){
                        case 1:
                            listFood.add(a);
                            break;
                        case 2:
                            listMedicine.add(a);
                            break;
                        case 3:
                            listMisc.add(a);
                            break;

                    }
                    break;

                }
            }
        }

    }

    private void setRecycleViewListeners() {
        RecyclerView listViewFood = (RecyclerView) findViewById(R.id.listViewFoodDist);
        RecyclerView listViewMedicine = (RecyclerView) findViewById(R.id.listViewMedicineDist);
        RecyclerView listViewMisc = (RecyclerView) findViewById(R.id.listViewMiscDist);
        /*RecyclerItemClickListener commonListener = new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
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
        */

    }

    private void setupButtons() {
        /*ImageButton btnNext = (ImageButton) findViewById(R.id.btnNext);

        btnNext.setOnClickListener(this);*/
    }

    //Click handling
    @Override
    public void onClick(View v) {
        /*
        switch (v.getId()) {
            case R.id.btnNext:
                btnNextClick();
                break;
        }*/
    }

    private void btnNextClick() {
        Intent intent = new Intent(getApplicationContext(), ChooseActivitiesActivity.class);
        startActivity(intent);
    }

    private void setViewAdapters(){
        RecyclerView listViewFood = (RecyclerView) findViewById(R.id.listViewFoodDist);
        listViewFood.setAdapter(new ImageAdapter(listFood));//dbController.readActivities()
        RecyclerView listViewMedicine = (RecyclerView) findViewById(R.id.listViewMedicineDist);
        listViewMedicine.setAdapter(new ImageAdapter(listMedicine));
        RecyclerView listViewMisc = (RecyclerView) findViewById(R.id.listViewMiscDist);
        listViewMisc.setAdapter(new ImageAdapter(listMisc));

    }

    private void setupRecycleViewLayouts(){

        RecyclerView listViewFood = (RecyclerView) findViewById(R.id.listViewFoodDist);
        listViewFood.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        RecyclerView listViewMedicine = (RecyclerView) findViewById(R.id.listViewMedicineDist);
        listViewMedicine.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        RecyclerView listViewMisc = (RecyclerView) findViewById(R.id.listViewMiscDist);
        listViewMisc.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

    }
}