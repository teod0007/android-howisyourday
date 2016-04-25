package com.prjproject.tcc.activities;

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

import java.util.ArrayList;

public class ShowActivitiesActivity extends AppCompatActivity implements View.OnClickListener{

    private boolean isFuture;
    private String profile_id;
    private String day_id;
    private String currentDay_period;
    private DatabaseController dbController;
    private ArrayList<Activity> listAll;
    private ArrayList<Activity> listFood;
    private ArrayList<Activity> listMedicine;
    private ArrayList<Activity> listDiversao;
    private ArrayList<Activity> listStudy;
    private ArrayList<Activity> listSocial;

    private ImageSwitcher sw;
    private RecyclerView listViewFood;
    private RecyclerView listViewMedicine;
    private RecyclerView listViewDiversao;
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
                myView.setLayoutParams(new ImageSwitcher.LayoutParams(ImageSwitcher.LayoutParams.WRAP_CONTENT,ImageSwitcher.LayoutParams.WRAP_CONTENT));
                return myView;
            }
        });
        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
        sw.setInAnimation(in);
        sw.setOutAnimation(out);

        listViewFood = (RecyclerView) findViewById(R.id.listViewFoodShow);
        listViewMedicine = (RecyclerView) findViewById(R.id.listViewMedicineShow);
        listViewDiversao = (RecyclerView) findViewById(R.id.listViewDiversaoShow);
        listViewStudy = (RecyclerView) findViewById(R.id.listViewStudyShow);
        listViewSocial = (RecyclerView) findViewById(R.id.listViewSocialShow);


    }

    private void getActivitiesFromDB(boolean isFuture, String profile_id, String day_id) {
        listAll = dbController.readActivitiesPerDayId(day_id, profile_id, isFuture);
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
                            listDiversao.add(a);
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
        ImageAdapter diversaoAdapter = (ImageAdapter)listViewDiversao.getAdapter();
        ImageAdapter studyAdapter = (ImageAdapter)listViewStudy.getAdapter();
        ImageAdapter socialAdapter = (ImageAdapter)listViewSocial.getAdapter();

        foodAdapter.setItemsList(listFood);
        medicineAdapter.setItemsList(listMedicine);
        diversaoAdapter.setItemsList(listDiversao);
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
        if(listDiversao == null) listDiversao = new ArrayList<>();
        else listDiversao.clear();
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

    private void togglePeriod(boolean morning, boolean afternoon, boolean evening, boolean dawn) {

        tgbMorning.setChecked(morning);
        tgbAfternoon.setChecked(afternoon);
        tgbEvening.setChecked(evening);
        tgbDawn.setChecked(dawn);

        int rId = 0;
        if(morning) rId = R.drawable.arrow_right;
        else if(afternoon) rId = R.drawable.arrow_right;
        else if(evening) rId = R.drawable.arrow_right;
        else if(dawn) rId = R.drawable.arrow_right;

        sw.setImageResource(rId);

        if(morning) currentDay_period = "morning";
        if(afternoon) currentDay_period = "afternoon";
        if(evening) currentDay_period = "evening";
        if(dawn) currentDay_period = "dawn";

        distributeActivitiesPerList();

    }

    private void setupRecycleViewLayouts(){


        listViewFood.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listViewMedicine.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listViewDiversao.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listViewStudy.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listViewSocial.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


    }

    private void setViewAdapters(){

        listViewFood.setAdapter(new ImageAdapter(listFood));//dbController.readActivities()
        listViewMedicine.setAdapter(new ImageAdapter(listMedicine));
        listViewDiversao.setAdapter(new ImageAdapter(listDiversao));
        listViewStudy.setAdapter(new ImageAdapter(listStudy));
        listViewSocial.setAdapter(new ImageAdapter(listSocial));


    }
}
