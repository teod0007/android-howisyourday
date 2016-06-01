package com.prjproject.tcc.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.prjproject.tcc.R;
import com.prjproject.tcc.adapters.DateAdapter;
import com.prjproject.tcc.controller.DatabaseController;

public class ChooseDayActivity extends AppCompatActivity {

    private DatabaseController dbController;

    String profile_id;

    private ListView listDate;
    private DateAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbController = new DatabaseController(getApplicationContext());
        setContentView(R.layout.activity_choose_day);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/KGPrimaryWhimsy.ttf");
        TextView txtTitle = (TextView) findViewById(R.id.txtChooseDay);
        txtTitle.setTypeface(font);


        getIdsFromIntent();
        initViews();
        setupViewAdapters();
    }

    private void setupViewAdapters() {
        Context context = getApplicationContext();
        Cursor dateCursor = dbController.readDaysPerProfileId(profile_id);

        listAdapter = new DateAdapter(context, dateCursor, 0);

        listDate.setAdapter(listAdapter);

        listDate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {

                Cursor cur = (Cursor) listAdapter.getItem(position);
                cur.moveToPosition(position);
                String id = cur.getString(cur.getColumnIndexOrThrow("_id"));

                Intent intent = new Intent();
                intent.putExtra("day_id", id);
                setResult(RESULT_OK, intent);
                finish();
                //getIntent();
            }
        });
    }

    private void initViews() {
        listDate = (ListView) findViewById(R.id.lstDays);
    }

    private void getIdsFromIntent() {
        Bundle extras = getIntent().getExtras();
        profile_id = extras.getString("profile_id");

    }
}
