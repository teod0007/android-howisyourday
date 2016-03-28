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

public class ChooseActivitiesActivity extends AppCompatActivity implements View.OnClickListener{
    private Resources resources;
    private DatabaseController dbController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_activities);
        dbController = new DatabaseController(getApplicationContext());
        setupButtons();
        setupRecycleViewLayouts();
        setViewAdapters();
        setRecycleViewListeners();
        resources = getResources();

        /*Drawable drawable = resources.getDrawable(R.drawable.image_macarrao);
        Bitmap bitmap =  ((BitmapDrawable)drawable).getBitmap();
        dbController.insertActivity(new Activity(1, "Macarrão 1", bitmap ));
        dbController.insertActivity(new Activity(1, "Macarrão 2", bitmap ));
        dbController.insertActivity(new Activity(1, "Macarrão 3", bitmap ));
        */
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
        ImageButton btnNext = (ImageButton) findViewById(R.id.btnNext);

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
        Intent intent = new Intent(getApplicationContext(), ChooseActivitiesActivity.class);
        startActivity(intent);
    }

    private void setViewAdapters(){
        RecyclerView listViewFood = (RecyclerView) findViewById(R.id.listViewFood);
        listViewFood.setAdapter(new ImageAdapter(dbController.readActivities()));//dbController.readActivities()
        RecyclerView listViewMedicine = (RecyclerView) findViewById(R.id.listViewMedicine);
        listViewMedicine.setAdapter(new ImageAdapter(dbController.readActivities()));
        RecyclerView listViewMisc = (RecyclerView) findViewById(R.id.listViewMisc);
        listViewMisc.setAdapter(new ImageAdapter(dbController.readActivities()));
        GridView gridViewActivities = (GridView) findViewById(R.id.gridViewActivities);
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
