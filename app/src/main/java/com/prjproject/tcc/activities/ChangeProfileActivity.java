package com.prjproject.tcc.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.prjproject.tcc.R;
import com.prjproject.tcc.adapters.ProfileAdapter;
import com.prjproject.tcc.controller.DatabaseController;
import com.prjproject.tcc.model.Profile;

public class ChangeProfileActivity extends AppCompatActivity {
    ListView lstProfiles;
    ProfileAdapter profileAdapter;
    private DatabaseController dbController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);
        dbController = new DatabaseController(getApplicationContext());

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/KGPrimaryWhimsy.ttf");
        TextView txtTitle = (TextView) findViewById(R.id.txtChangeProfile);
        txtTitle.setTypeface(font);

        initViews();
    }

    private void initViews() {
        lstProfiles = (ListView) findViewById(R.id.lstProfiles);
        profileAdapter= new ProfileAdapter(this, dbController.readProfiles() , 0);
        lstProfiles.setAdapter(profileAdapter);

        lstProfiles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {

                Cursor cur = (Cursor) profileAdapter.getItem(position);
                cur.moveToPosition(position);
                String id = cur.getString(cur.getColumnIndexOrThrow("_id"));
                String name = cur.getString(cur.getColumnIndexOrThrow("name"));

                Intent intent = new Intent();
                intent.putExtra("profile_id", id);
                intent.putExtra("profile_name", name);
                setResult(RESULT_OK, intent);
                finish();
                //getIntent();
            }
        });

    }

}
