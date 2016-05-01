package com.prjproject.tcc.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ToggleButton;
import android.widget.ViewSwitcher;

import com.prjproject.tcc.R;
import com.prjproject.tcc.adapters.ImageAdapter;
import com.prjproject.tcc.controller.DatabaseController;
import com.prjproject.tcc.model.Activity;
import com.prjproject.tcc.model.Day;

import java.util.ArrayList;
import java.util.Date;

public class ShowActivitiesActivity extends AppCompatActivity implements View.OnClickListener{

    private boolean isFuture;
    private String profile_id;
    private String day_id;
    private String currentDay_period;
    private DatabaseController dbController;
    private ArrayList<Activity> listAll;
    private ArrayList<Activity> listFood;
    private ArrayList<Activity> listMedicine;
    private ArrayList<Activity> listFun;
    private ArrayList<Activity> listStudy;
    private ArrayList<Activity> listSocial;

    private ImageSwitcher sw;
    private RecyclerView listViewFood;
    private RecyclerView listViewMedicine;
    private RecyclerView listViewFun;
    private RecyclerView listViewStudy;
    private RecyclerView listViewSocial;

    private Button btnEdit;
    private ToggleButton tgbMorning;
    private ToggleButton tgbAfternoon;
    private ToggleButton tgbEvening;
    private ToggleButton tgbDawn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_activities);
        dbController = new DatabaseController(getApplicationContext());

        getIdsFromIntent();
        setupViews();
        setupButtons();
        setupRecycleViewLayouts();
        setViewAdapters();
        getActivitiesFromDB(isFuture, profile_id, day_id);
        togglePeriod(true, false, false, false);

    }

    private void setupViews() {
        sw = (ImageSwitcher) findViewById(R.id.imageSwitcherDayPeriod);
        sw.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView myView = new ImageView(getApplicationContext());
                myView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                myView.setLayoutParams(new ImageSwitcher.LayoutParams(ImageSwitcher.LayoutParams.WRAP_CONTENT, ImageSwitcher.LayoutParams.WRAP_CONTENT));
                return myView;
            }
        });
        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
        sw.setInAnimation(in);
        sw.setOutAnimation(out);

        listViewFood = (RecyclerView) findViewById(R.id.listViewFoodShow);
        listViewMedicine = (RecyclerView) findViewById(R.id.listViewMedicineShow);
        listViewFun = (RecyclerView) findViewById(R.id.listViewFunShow);
        listViewStudy = (RecyclerView) findViewById(R.id.listViewStudyShow);
        listViewSocial = (RecyclerView) findViewById(R.id.listViewSocialShow);


    }

    private void getActivitiesFromDB(boolean isFuture, String profile_id, String day_id) {
        if(isFuture){
            day_id = ""+dbController.getDayFuture(profile_id);
            if(day_id.equals("-1")){
                day_id = ""+dbController.insertDay(new Day(Integer.valueOf(profile_id), null, true));
            }
        }
        listAll = dbController.readActivitiesPerDayId(day_id, profile_id, isFuture);
        this.day_id = day_id;
    }

    private void distributeActivitiesPerList() {
        if(listAll != null){
            clearLists();

            for(Activity a : listAll){
                if(a.getDayPeriod().equals(this.currentDay_period))
                    switch(a.getCategory_id()){
                        case 1:
                            listFood.add(a);
                            break;
                        case 2:
                            listSocial.add(a);
                            break;
                        case 3:
                            listMedicine.add(a);
                            break;
                        case 4:
                            listFun.add(a);
                            break;
                        case 5:
                            listStudy.add(a);
                            break;
                    }

            }

            addListsToAdapters();

        }
    }

    private void addListsToAdapters() {

        ImageAdapter foodAdapter = (ImageAdapter)listViewFood.getAdapter();
        ImageAdapter medicineAdapter = (ImageAdapter)listViewMedicine.getAdapter();
        ImageAdapter diversaoAdapter = (ImageAdapter)listViewFun.getAdapter();
        ImageAdapter studyAdapter = (ImageAdapter)listViewStudy.getAdapter();
        ImageAdapter socialAdapter = (ImageAdapter)listViewSocial.getAdapter();

        foodAdapter.setItemsList(listFood);
        medicineAdapter.setItemsList(listMedicine);
        diversaoAdapter.setItemsList(listFun);
        studyAdapter.setItemsList(listStudy);
        socialAdapter.setItemsList(listSocial);

        foodAdapter.notifyDataSetChanged();
        medicineAdapter.notifyDataSetChanged();
        diversaoAdapter.notifyDataSetChanged();
        studyAdapter.notifyDataSetChanged();
        socialAdapter.notifyDataSetChanged();


    }

    private void clearLists() {
        if(listFood == null) listFood = new ArrayList<>();
        else listFood.clear();
        if(listMedicine == null) listMedicine = new ArrayList<>();
        else listMedicine.clear();
        if(listFun == null) listFun = new ArrayList<>();
        else listFun.clear();
        if(listStudy == null) listStudy = new ArrayList<>();
        else listStudy.clear();
        if(listSocial == null) listSocial = new ArrayList<>();
        else listSocial.clear();
    }

    private void getIdsFromIntent() {
        Bundle extras = getIntent().getExtras();
        isFuture = extras.getBoolean("isFuture");
        profile_id = extras.getString("profile_id");
        day_id = extras.getString("day_id");
    }

    private void setupButtons() {
        btnEdit = (Button)findViewById(R.id.btnEditDay);
        tgbMorning = (ToggleButton) findViewById(R.id.btnToggleMorning);
        tgbAfternoon= (ToggleButton) findViewById(R.id.btnToggleAfternoon);
        tgbEvening = (ToggleButton) findViewById(R.id.btnToggleEvening);
        tgbDawn= (ToggleButton) findViewById(R.id.btnToggleDawn);

        btnEdit.setOnClickListener(this);
        tgbMorning.setOnClickListener(this);
        tgbAfternoon.setOnClickListener(this);
        tgbEvening.setOnClickListener(this);
        tgbDawn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEditDay:
                btnEditClick();
                break;
            case R.id.btnToggleMorning:
                togglePeriod(true, false, false, false);
                break;
            case R.id.btnToggleAfternoon:
                togglePeriod(false, true, false, false);
                break;
            case R.id.btnToggleEvening:
                togglePeriod(false, false, true, false);
                break;
            case R.id.btnToggleDawn:
                togglePeriod(false, false, false, true);
                break;
        }
    }

    private void btnEditClick() {
        Intent intent = new Intent();
        intent.putExtra("day_id", day_id);
        intent.putExtra("isFuture", true);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void togglePeriod(boolean morning, boolean afternoon, boolean evening, boolean dawn) {

        tgbMorning.setChecked(morning);
        tgbAfternoon.setChecked(afternoon);
        tgbEvening.setChecked(evening);
        tgbDawn.setChecked(dawn);

        int rId = 0;
        if(morning) rId = R.drawable.manha;
        else if(afternoon) rId = R.drawable.tarde;
        else if(evening) rId = R.drawable.noite;
        else if(dawn) rId = R.drawable.madrugada;

        try {
            Drawable dw = getResources().getDrawable(rId);
            sw.setImageDrawable(null);
            sw.setImageDrawable(dw);

        }catch(Exception e){
            e.printStackTrace();
        }
        if(morning) currentDay_period = "morning";
        if(afternoon) currentDay_period = "afternoon";
        if(evening) currentDay_period = "evening";
        if(dawn) currentDay_period = "dawn";

        distributeActivitiesPerList();

    }

    private void setupRecycleViewLayouts(){


        listViewFood.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listViewMedicine.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listViewFun.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listViewStudy.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listViewSocial.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


    }

    private void setViewAdapters(){

        listViewFood.setAdapter(new ImageAdapter(listFood));//dbController.readActivities()
        listViewMedicine.setAdapter(new ImageAdapter(listMedicine));
        listViewFun.setAdapter(new ImageAdapter(listFun));
        listViewStudy.setAdapter(new ImageAdapter(listStudy));
        listViewSocial.setAdapter(new ImageAdapter(listSocial));


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recycleActivityList(listFood);
        recycleActivityList(listMedicine);
        recycleActivityList(listSocial);
        recycleActivityList(listFun);
        recycleActivityList(listStudy);
        recycleActivityList(listAll);


        listViewFood = null;
        listViewMedicine = null;
        listViewSocial = null;
        listViewFun = null;
        listViewStudy = null;

        listAll = null;
        listFood = null;
        listMedicine = null;
        listSocial = null;
        listFun = null;
        listStudy = null;

        dbController = null;

        btnEdit = null;
        tgbMorning = null;
        tgbAfternoon = null;
        tgbEvening= null;
        tgbDawn= null;

        sw.clearAnimation();
        sw = null;

        finish();


    }

    private void recycleActivityList(ArrayList<Activity> list) {
        for(Activity a : list){
            if(a.getActivityImage()!=null) {
                a.getActivityImage().recycle();
                a.setActivityImage(null);
            }
        }
        list.clear();

    }
}
