package com.prjproject.tcc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.prjproject.tcc.R;

public class ChooseActivitiesActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_activities);
        setupButtons();
    }

    private void setupButtons() {
        //Button btnNewDay = (Button) findViewById(R.id.btnNewDay);

        //btnNewDay.setOnClickListener(this);
    }

    //Click handling
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnNewDay:
                btnNewDayClick();
                break;
        }
    }

    private void btnNewDayClick() {
        Intent intent = new Intent(getApplicationContext(), ChooseActivitiesActivity.class);
        startActivity(intent);
    }
}
