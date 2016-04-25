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

public class BeginRoutineActivity extends AppCompatActivity implements View.OnClickListener{

    private String profile_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin_routine);

        setupButtons();
        getIdsFromIntent();
    }

    private void getIdsFromIntent() {
        Bundle extras = getIntent().getExtras();
        profile_id = extras.getString("profile_id");

    }

    private void setupButtons() {
        Button btnNewDay = (Button) findViewById(R.id.btnNewDay);
        Button btnFuture = (Button) findViewById(R.id.btnFuture);
        Button btnPast = (Button) findViewById(R.id.btnPastDays);

        btnNewDay.setOnClickListener(this);
        btnFuture.setOnClickListener(this);
        btnPast.setOnClickListener(this);
    }

    //Click handling
    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.btnNewDay:
                btnNewDayClick();
                break;
            case R.id.btnFuture:
                btnFutureClick();
                break;
            case R.id.btnPastDays:
                btnPastClick();
                break;
        }
    }

    private void btnPastClick() {
        Intent intent = new Intent(getApplicationContext(), ChooseDayActivity.class);
        intent.putExtra("profile_id",profile_id);
        startActivityForResult(intent, 1);
    }

    private void btnFutureClick() {
        Intent intent = new Intent(getApplicationContext(), ShowActivitiesActivity.class);
        intent.putExtra("profile_id",profile_id);
        intent.putExtra("isFuture", true);
        startActivity(intent);
    }

    private void btnNewDayClick(){
        Intent intent = new Intent(getApplicationContext(), ChooseActivitiesActivity.class);
        intent.putExtra("profile_id",profile_id);
        startActivity(intent);
    }

}
