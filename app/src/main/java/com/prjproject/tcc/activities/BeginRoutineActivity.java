package com.prjproject.tcc.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.prjproject.tcc.R;

public class BeginRoutineActivity extends AppCompatActivity implements View.OnClickListener{

    private String profile_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin_routine);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/KGPrimaryWhimsy.ttf");
        TextView txtTitle = (TextView) findViewById(R.id.txtTitle2);
        txtTitle.setTypeface(font);

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
        startActivityForResult(intent, 2);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
    }

    private void btnNewDayClick(){
        Intent intent = new Intent(getApplicationContext(), ChooseActivitiesActivity.class);
        intent.putExtra("profile_id",profile_id);
        startActivityForResult(intent, 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                String day_id = data.getStringExtra("day_id");

                Intent intent = new Intent(getApplicationContext(), ShowActivitiesActivity.class);
                intent.putExtra("profile_id",profile_id);
                intent.putExtra("day_id", day_id);
                intent.putExtra("isFuture", false);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent,2);

            }
        }
        if (requestCode == 2) {
            if(resultCode == RESULT_OK){
                String day_id = data.getStringExtra("day_id");

                Intent intent = new Intent(getApplicationContext(), ChooseActivitiesActivity.class);
                intent.putExtra("profile_id",profile_id);
                intent.putExtra("day_id", day_id);
                startActivityForResult(intent,1);

            }
        }
    }

}
