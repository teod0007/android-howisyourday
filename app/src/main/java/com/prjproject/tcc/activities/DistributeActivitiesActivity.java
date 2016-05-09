package com.prjproject.tcc.activities;


import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.prjproject.tcc.R;
import com.prjproject.tcc.adapters.GridAdapter;
import com.prjproject.tcc.adapters.ImageAdapter;
import com.prjproject.tcc.adapters.ImageDragAdapter;
import com.prjproject.tcc.controller.DatabaseController;
import com.prjproject.tcc.listeners.RecyclerItemClickListener;
import com.prjproject.tcc.model.Activity;
import com.prjproject.tcc.model.Day;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DistributeActivitiesActivity extends AppCompatActivity implements View.OnClickListener, View.OnDragListener{
    private Resources resources;
    private DatabaseController dbController;
    private ArrayList<Activity> listFood;
    private ArrayList<Activity> listMedicine;
    private ArrayList<Activity> listSocial;
    private ArrayList<Activity> listFun;
    private ArrayList<Activity> listStudy;

    private ArrayList<Activity> listMorning;
    private ArrayList<Activity> listAfternoon;
    private ArrayList<Activity> listEvening;
    private ArrayList<Activity> listDawn;
    private String profile_id;
    private String day_id;

    private int[] arrayUndistributedIds;

    RecyclerView listViewFood;
    RecyclerView listViewMedicine;
    RecyclerView listViewSocial;
    RecyclerView listViewFun;
    RecyclerView listViewStudy;

    RecyclerView listViewMorning;
    RecyclerView listViewAfternoon;
    RecyclerView listViewEvening;
    RecyclerView listViewDawn;

    ImageView imageViewMorning;
    ImageView imageViewAfternoon;
    ImageView imageViewEvening;
    ImageView imageViewDawn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distribute_activities);
        dbController = new DatabaseController(getApplicationContext());
        listFood = new ArrayList<>();
        listMedicine = new ArrayList<>();
        listSocial = new ArrayList<>();
        listFun = new ArrayList<>();
        listStudy = new ArrayList<>();

        listMorning = new ArrayList<>();
        listAfternoon = new ArrayList<>();
        listEvening = new ArrayList<>();
        listDawn = new ArrayList<>();
        getIdsFromIntent();
        setupViews();
        setupImages();
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

    private void setupImages() {
        imageViewMorning.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.manha, 200, 200));
        imageViewAfternoon.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.tarde, 200, 200));
        imageViewEvening.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.noite, 200, 200));
        imageViewDawn.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.madrugada, 200, 200));
    }



    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        options.inPurgeable = true;
        return BitmapFactory.decodeResource(res, resId, options);
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

    private void setupViews() {
        listViewFood = (RecyclerView) findViewById(R.id.listViewFoodDist);
        listViewMedicine = (RecyclerView) findViewById(R.id.listViewMedicineDist);
        listViewSocial = (RecyclerView) findViewById(R.id.listViewSocialDist);
        listViewFun = (RecyclerView) findViewById(R.id.listViewFunDist);
        listViewStudy = (RecyclerView) findViewById(R.id.listViewStudyDist);


        listViewMorning = (RecyclerView) findViewById(R.id.listViewMorning);
        listViewAfternoon = (RecyclerView) findViewById(R.id.listViewAfternoon);
        listViewEvening = (RecyclerView) findViewById(R.id.listViewEvening);
        listViewDawn = (RecyclerView) findViewById(R.id.listViewDawn);

        listViewFood.setHasFixedSize(true);
        listViewMedicine.setHasFixedSize(true);
        listViewSocial.setHasFixedSize(true);
        listViewFun.setHasFixedSize(true);
        listViewStudy.setHasFixedSize(true);

        listViewMorning.setHasFixedSize(true);
        listViewAfternoon.setHasFixedSize(true);
        listViewEvening.setHasFixedSize(true);
        listViewDawn.setHasFixedSize(true);

        imageViewMorning = (ImageView) findViewById(R.id.image_morning);
        imageViewAfternoon = (ImageView) findViewById(R.id.image_afternoon);
        imageViewEvening = (ImageView) findViewById(R.id.image_evening);
        imageViewDawn= (ImageView) findViewById(R.id.image_dawn);

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
        profile_id = extras.getString("profile_id");
        day_id = extras.getString("day_id");

        ArrayList<Activity> activities = dbController.readActivities();

        for(int i = 0; i < arrayUndistributedIds.length; i++){
            for(Activity a : activities){
                if(a.get_id() == arrayUndistributedIds[i]){
                    switch (a.getCategory_id()){
                        case 1:
                            listFood.add(new Activity(a.get_id(),a.getActivityImage()));
                            break;
                        case 2:
                            listSocial.add(new Activity(a.get_id(),a.getActivityImage()));
                            break;
                        case 3:
                            listMedicine.add(new Activity(a.get_id(),a.getActivityImage()));
                            break;
                        case 4:
                            listFun.add(new Activity(a.get_id(),a.getActivityImage()));
                            break;
                        case 5:
                            listStudy.add(new Activity(a.get_id(),a.getActivityImage()));
                            break;

                    }
                    break;

                }
            }
        }

    }

    private void setRecycleViewListeners() {

        ImageDragAdapter listFoodAdapter = (ImageDragAdapter)listViewFood.getAdapter();
        ImageDragAdapter listMedicineAdapter = (ImageDragAdapter)listViewMedicine.getAdapter();
        ImageDragAdapter listSocialAdapter = (ImageDragAdapter)listViewSocial.getAdapter();
        ImageDragAdapter listFunAdapter = (ImageDragAdapter)listViewFun.getAdapter();
        ImageDragAdapter listStudyAdapter = (ImageDragAdapter)listViewStudy.getAdapter();

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
        listSocialAdapter.setOnLongItemClickListener(commonLongClickListener);
        listFunAdapter.setOnLongItemClickListener(commonLongClickListener);
        listStudyAdapter.setOnLongItemClickListener(commonLongClickListener);

        listMorningAdapter.setOnLongItemClickListener(commonLongClickListener);
        listAfternoonAdapter.setOnLongItemClickListener(commonLongClickListener);
        listEveningAdapter.setOnLongItemClickListener(commonLongClickListener);
        listDawnAdapter.setOnLongItemClickListener(commonLongClickListener);




    }

    private void setupButtons() {
        Button btnSave = (Button) findViewById(R.id.btnSaveDay);

        btnSave.setOnClickListener(this);
    }

    //Click handling
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnSaveDay:
                btnSaveClick();
                break;
        }
    }

    private void btnSaveClick() {

        List<Activity> saveList = new ArrayList<>();
        saveList.addAll(((ImageDragAdapter)listViewMorning.getAdapter()).getItems());
        saveList.addAll(((ImageDragAdapter)listViewAfternoon.getAdapter()).getItems());
        saveList.addAll(((ImageDragAdapter)listViewEvening.getAdapter()).getItems());
        saveList.addAll(((ImageDragAdapter) listViewDawn.getAdapter()).getItems());

        Day dayToSave = new Day(Integer.parseInt(profile_id), new Date(System.currentTimeMillis()),false);
        dayToSave.setListActivities(saveList);
        if(day_id != null)
            dayToSave.set_id(Integer.valueOf(day_id));

        int day_id = dbController.insertDay(dayToSave);

        Intent intent = new Intent();
        intent.putExtra("day_id", String.valueOf(day_id));

        setResult(RESULT_OK, intent);
        finish();

        /*Intent intent = new Intent(getApplicationContext(), ChooseActivitiesActivity.class);
        startActivity(intent);*/
    }

    private void setViewAdapters(){
        listViewFood.setAdapter(new ImageDragAdapter(listFood));//dbController.readActivities()
        listViewMedicine.setAdapter(new ImageDragAdapter(listMedicine));
        listViewSocial.setAdapter(new ImageDragAdapter(listSocial));
        listViewFun.setAdapter(new ImageDragAdapter(listFun));
        listViewStudy.setAdapter(new ImageDragAdapter(listStudy));


        listViewMorning.setAdapter(new ImageDragAdapter(listMorning));
        listViewAfternoon.setAdapter(new ImageDragAdapter(listAfternoon));
        listViewEvening.setAdapter(new ImageDragAdapter(listEvening));
        listViewDawn.setAdapter(new ImageDragAdapter(listDawn));

    }

    private void setupRecycleViewLayouts(){

        listViewFood.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listViewMedicine.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listViewSocial.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listViewFun.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listViewStudy.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

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
                    ((Activity) activity).setDayPeriod("morning");
                    to = (RecyclerView) findViewById(R.id.listViewMorning);
                    break;
                case R.id.layout_afternoon:
                    ((Activity) activity).setDayPeriod("afternoon");
                    to = (RecyclerView) findViewById(R.id.listViewAfternoon);
                    break;
                case R.id.layout_evening:
                    ((Activity) activity).setDayPeriod("evening");
                    to = (RecyclerView) findViewById(R.id.listViewEvening);
                    break;
                case R.id.layout_dawn:
                    ((Activity) activity).setDayPeriod("dawn");
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