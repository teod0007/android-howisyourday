package com.prjproject.tcc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.prjproject.tcc.R;
import com.prjproject.tcc.utils.AndroidDatabaseManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupButtons();

    }

    private void setupButtons() {
        Button btnNewProfile = (Button) findViewById(R.id.btnNewProfile);
        Button btnChangeProfile = (Button) findViewById(R.id.btnChangeProfile);
        Button btnBeginRoutine = (Button) findViewById(R.id.btnBeginRoutine);
        TextView txtTitle = (TextView) findViewById(R.id.txtTitle);

        btnNewProfile.setOnClickListener(this);
        btnChangeProfile.setOnClickListener(this);
        btnBeginRoutine.setOnClickListener(this);
        txtTitle.setOnClickListener(this);

    }

    //Click handling
    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.btnNewProfile:
                btnNewProfileClick();
                break;
            case R.id.btnChangeProfile:
                btnChangeProfileClick();
                break;
            case R.id.btnBeginRoutine:
                btnBeginRoutineClick();
                break;
            case R.id.txtTitle:
                startDatabaseManager();
                break;
        }
    }

    public void btnNewProfileClick(){
        Intent intent = new Intent(getApplicationContext(), CreateProfileActivity.class);
        startActivity(intent);
    }
    public void btnChangeProfileClick(){
        Intent intent = new Intent(getApplicationContext(), ChangeProfileActivity.class);
        startActivity(intent);
    }
    public void btnBeginRoutineClick(){
        Intent intent = new Intent(getApplicationContext(), BeginRoutineActivity.class);
        startActivity(intent);
    }

    /////////////////////////////
    ////////DATABASE MANAGER/////
    /////////////////////////////

    public void startDatabaseManager() {

        Intent dbmanager = new Intent(getApplicationContext(),AndroidDatabaseManager.class);
        startActivity(dbmanager);
    }


}
