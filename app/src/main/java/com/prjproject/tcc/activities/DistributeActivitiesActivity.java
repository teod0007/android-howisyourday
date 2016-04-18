package com.prjproject.tcc.activities;


import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;

import com.prjproject.tcc.R;
import com.prjproject.tcc.adapters.GridAdapter;
import com.prjproject.tcc.adapters.ImageAdapter;
import com.prjproject.tcc.adapters.ImageDragAdapter;
import com.prjproject.tcc.controller.DatabaseController;
import com.prjproject.tcc.listeners.RecyclerItemClickListener;
import com.prjproject.tcc.model.Activity;

import java.util.ArrayList;

public class DistributeActivitiesActivity extends AppCompatActivity implements View.OnClickListener, View.OnDragListener{
    private Resources resources;
    private DatabaseController dbController;
    private ArrayList<Activity> listFood;
    private ArrayList<Activity> listMedicine;
    private ArrayList<Activity> listMisc;
    private ArrayList<Activity> listMorning;
    private ArrayList<Activity> listAfternoon;
    private ArrayList<Activity> listEvening;
    private ArrayList<Activity> listDawn;

    private int[] arrayUndistributedIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distribute_activities);
        dbController = new DatabaseController(getApplicationContext());
        listFood = new ArrayList<>();
        listMedicine = new ArrayList<>();
        listMisc = new ArrayList<>();
        listMorning = new ArrayList<>();
        listAfternoon = new ArrayList<>();
        listEvening = new ArrayList<>();
        listDawn = new ArrayList<>();
        getIdsFromIntent();
        setupButtons();
        setupRecycleViewLayouts();
        setViewAdapters();
        setRecycleViewListeners();
        setDragListeners();
        resources = getResources();



        /*Drawable drawable = resources.getDrawable(R.drawable.image_macarrao);
        Bitmap bitmap =  ((BitmapDrawable)drawable).getBitmap();
        dbController.insertActivity(new Activity(1, "Macarrão 1", bitmap ));
        dbController.insertActivity(new Activity(1, "Macarrão 2", bitmap ));
        dbController.insertActivity(new Activity(1, "Macarrão 3", bitmap ));*/

    }

    private void setDragListeners() {
        (findViewById(R.id.layout_morning)).setOnDragListener(this);
        (findViewById(R.id.layout_afternoon)).setOnDragListener(this);
        (findViewById(R.id.layout_evening)).setOnDragListener(this);
        (findViewById(R.id.layout_dawn)).setOnDragListener(this);
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

        RecyclerView listViewMorning = (RecyclerView) findViewById(R.id.listViewMorning);
        RecyclerView listViewAfternoon = (RecyclerView) findViewById(R.id.listViewAfternoon);
        RecyclerView listViewEvening = (RecyclerView) findViewById(R.id.listViewEvening);
        RecyclerView listViewDawn = (RecyclerView) findViewById(R.id.listViewDawn);

        ImageDragAdapter listFoodAdapter = (ImageDragAdapter)listViewFood.getAdapter();
        ImageDragAdapter listMedicineAdapter = (ImageDragAdapter)listViewMedicine.getAdapter();
        ImageDragAdapter listMiscAdapter = (ImageDragAdapter)listViewMisc.getAdapter();

        ImageDragAdapter listMorningAdapter = (ImageDragAdapter)listViewMorning.getAdapter();
        ImageDragAdapter listAfternoonAdapter = (ImageDragAdapter)listViewAfternoon.getAdapter();
        ImageDragAdapter listEveningAdapter = (ImageDragAdapter)listViewEvening.getAdapter();
        ImageDragAdapter listDawnAdapter = (ImageDragAdapter)listViewDawn.getAdapter();

        ImageDragAdapter.OnLongItemClickListener commonLongClickListener = new ImageDragAdapter.OnLongItemClickListener() {
            @Override
            public void onLongItemClick(View itemView, int position) {
                //ClipData.Item item = new ClipData.Item((CharSequence)itemView.getTag());
                //String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                //ClipData dragData = new ClipData(itemView.getTag().toString(),
                //        mimeTypes, item);
                ClipData dragData = ClipData.newPlainText("position", ""+position);
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(itemView);

                itemView.startDrag(dragData, shadowBuilder, itemView, 0);
                //itemView.setVisibility(View.INVISIBLE);
            }
        };
        listFoodAdapter.setOnLongItemClickListener(commonLongClickListener);
        listMedicineAdapter.setOnLongItemClickListener(commonLongClickListener);
        listMiscAdapter.setOnLongItemClickListener(commonLongClickListener);

        listMorningAdapter.setOnLongItemClickListener(commonLongClickListener);
        listAfternoonAdapter.setOnLongItemClickListener(commonLongClickListener);
        listEveningAdapter.setOnLongItemClickListener(commonLongClickListener);
        listDawnAdapter.setOnLongItemClickListener(commonLongClickListener);


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
        listViewFood.setAdapter(new ImageDragAdapter(listFood));//dbController.readActivities()
        RecyclerView listViewMedicine = (RecyclerView) findViewById(R.id.listViewMedicineDist);
        listViewMedicine.setAdapter(new ImageDragAdapter(listMedicine));
        RecyclerView listViewMisc = (RecyclerView) findViewById(R.id.listViewMiscDist);
        listViewMisc.setAdapter(new ImageDragAdapter(listMisc));


        RecyclerView listViewMorning = (RecyclerView) findViewById(R.id.listViewMorning);
        RecyclerView listViewAfternoon = (RecyclerView) findViewById(R.id.listViewAfternoon);
        RecyclerView listViewEvening = (RecyclerView) findViewById(R.id.listViewEvening);
        RecyclerView listViewDawn = (RecyclerView) findViewById(R.id.listViewDawn);
        listViewMorning.setAdapter(new ImageDragAdapter(listMorning));
        listViewAfternoon.setAdapter(new ImageDragAdapter(listAfternoon));
        listViewEvening.setAdapter(new ImageDragAdapter(listEvening));
        listViewDawn.setAdapter(new ImageDragAdapter(listDawn));

    }

    private void setupRecycleViewLayouts(){

        RecyclerView listViewFood = (RecyclerView) findViewById(R.id.listViewFoodDist);
        listViewFood.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        RecyclerView listViewMedicine = (RecyclerView) findViewById(R.id.listViewMedicineDist);
        listViewMedicine.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        RecyclerView listViewMisc = (RecyclerView) findViewById(R.id.listViewMiscDist);
        listViewMisc.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        RecyclerView listViewMorning = (RecyclerView) findViewById(R.id.listViewMorning);
        RecyclerView listViewAfternoon = (RecyclerView) findViewById(R.id.listViewAfternoon);
        RecyclerView listViewEvening = (RecyclerView) findViewById(R.id.listViewEvening);
        RecyclerView listViewDawn = (RecyclerView) findViewById(R.id.listViewDawn);
        listViewMorning.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        listViewAfternoon.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        listViewEvening.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        listViewDawn.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


    }

    @Override
    public boolean onDrag(View v, DragEvent e) {

        if (e.getAction()==DragEvent.ACTION_DROP) {
            View view = (View) e.getLocalState();
            ClipData dragData = e.getClipData();
            int fromItemIndex = Integer.parseInt(dragData.getItemAt(0).getText().toString());
            RecyclerView from = (RecyclerView) view.getParent();
            ImageDragAdapter fromAdapter = (ImageDragAdapter)from.getAdapter();
            Object activity = fromAdapter.getItem(fromItemIndex);
            RecyclerView to = null;
            //fromAdapter.removeItem(0);
            //fromAdapter.notifyItemRemoved(0);
            int vId = v.getId();
            switch(v.getId()){
                case R.id.layout_morning:
                    to = (RecyclerView) findViewById(R.id.listViewMorning);
                    break;
                case R.id.layout_afternoon:
                    to = (RecyclerView) findViewById(R.id.listViewAfternoon);
                    break;
                case R.id.layout_evening:
                    to = (RecyclerView) findViewById(R.id.listViewEvening);
                    break;
                case R.id.layout_dawn:
                    to = (RecyclerView) findViewById(R.id.listViewDawn);
                    break;
            }
            try {
                //RecyclerView to = (RecyclerView) findViewById(R.id.listViewMorning);
                ((ImageDragAdapter) to.getAdapter()).addItem((Activity) activity);
                ((ImageDragAdapter) to.getAdapter()).notifyItemInserted(to.getAdapter().getItemCount() - 1);
                ((ImageDragAdapter)from.getAdapter()).removeItem(fromItemIndex);
                ((ImageDragAdapter)from.getAdapter()).notifyItemRemoved(fromItemIndex);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }

        }
        return true;
    }
}