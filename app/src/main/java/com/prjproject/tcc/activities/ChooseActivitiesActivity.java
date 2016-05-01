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
    private ArrayList<Activity> listSocial;
    private ArrayList<Activity> listFun;
    private ArrayList<Activity> listStudy;
    private String profile_id;
    private String day_id;

    RecyclerView listViewFood;
    RecyclerView listViewMedicine;
    RecyclerView listViewSocial;
    RecyclerView listViewFun;
    RecyclerView listViewStudy;
    GridView gridViewActivities;

    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_activities);
        dbController = new DatabaseController(getApplicationContext());
        try {
            initViews();
            getActivitiesFromDB();
            getIdsFromIntent();
            setupButtons();
            setupRecycleViewLayouts();
            setViewAdapters();
            setRecycleViewListeners();
            resources = getResources();
        }catch(Exception ex){

        }


    }

    private void initViews() {
        listViewFood = (RecyclerView) findViewById(R.id.listViewFood);
        listViewMedicine = (RecyclerView) findViewById(R.id.listViewMedicine);
        listViewSocial = (RecyclerView) findViewById(R.id.listViewSocial);
        listViewFun = (RecyclerView) findViewById(R.id.listViewFun);
        listViewStudy = (RecyclerView) findViewById(R.id.listViewStudy);
        gridViewActivities = (GridView)findViewById(R.id.gridViewActivities);

    }

    private void getIdsFromIntent() {
        Bundle extras = getIntent().getExtras();
        profile_id = extras.getString("profile_id");
        day_id = extras.getString("day_id");

    }

    private void getActivitiesFromDB() {
        listFood = dbController.readActivitiesPerCategory(1);
        listSocial = dbController.readActivitiesPerCategory(2);
        listMedicine = dbController.readActivitiesPerCategory(3);
        listFun = dbController.readActivitiesPerCategory(4);
        listStudy = dbController.readActivitiesPerCategory(5);
    }

    private void setRecycleViewListeners() {
        RecyclerItemClickListener commonListener = new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Object activity =  (((ImageAdapter) ((RecyclerView) view.getParent()).getAdapter()).getItem(position));
                        ((GridAdapter)gridViewActivities.getAdapter()).addItem(activity);
                    }
        });
        listViewFood.addOnItemTouchListener(commonListener);
        listViewMedicine.addOnItemTouchListener(commonListener);
        listViewSocial.addOnItemTouchListener(commonListener);
        listViewFun.addOnItemTouchListener(commonListener);
        listViewStudy.addOnItemTouchListener(commonListener);


    }

    private void setupButtons() {
        btnNext = (Button) findViewById(R.id.btnNext);

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
        List<Object> activities =  ((GridAdapter)gridViewActivities.getAdapter()).getItems();
        int index = 0;
        int[] idList = new int[activities.size()];
        for(Object a : activities){
            idList[index++] = ((Activity)a).get_id();
        }

        Intent intent = new Intent(getApplicationContext(), DistributeActivitiesActivity.class);
        intent.putExtra("idList",idList);
        intent.putExtra("profile_id",profile_id);
        intent.putExtra("day_id",day_id);
        startActivityForResult(intent, 1);
    }

    private void setViewAdapters(){

        listViewFood.setAdapter(new ImageAdapter(listFood));
        listViewMedicine.setAdapter(new ImageAdapter(listMedicine));
        listViewSocial.setAdapter(new ImageAdapter(listSocial));
        listViewFun.setAdapter(new ImageAdapter(listFun));
        listViewStudy.setAdapter(new ImageAdapter(listStudy));
        gridViewActivities.setAdapter(new GridAdapter(getApplicationContext()));

    }

    private void setupRecycleViewLayouts(){

        listViewFood.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listViewMedicine.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listViewSocial.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listViewFun.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listViewStudy.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                String day_id = data.getStringExtra("day_id");

                Intent intent = new Intent();
                intent.putExtra("day_id", day_id);
                setResult(RESULT_OK, intent);
                finish();

            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recycleActivityList(listFood);
        recycleActivityList(listMedicine);
        recycleActivityList(listSocial);
        recycleActivityList(listFun);
        recycleActivityList(listStudy);


        listViewFood = null;
        listViewMedicine = null;
        listViewSocial = null;
        listViewFun = null;
        listViewStudy = null;
        gridViewActivities = null;

        listFood = null;
        listMedicine = null;
        listSocial = null;
        listFun = null;
        listStudy = null;

        btnNext = null;

    }

    private void recycleActivityList(ArrayList<Activity> list) {
        for(Activity a : list){
            a.getActivityImage().recycle();
            a.setActivityImage(null);
        }
        list.clear();

    }
}
