package com.prjproject.tcc.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.prjproject.tcc.R;
import com.prjproject.tcc.utils.AndroidDatabaseManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    String current_id = "";
    String current_name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Typeface font = Typeface.createFromAsset(getAssets(), "fonts/AGENTORANGE.TTF");
        //TextView txtTitle = (TextView) findViewById(R.id.txtTitle);
        //txtTitle.setTypeface(font);

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
        startActivityForResult(intent, 1);
    }
    public void btnChangeProfileClick(){
        Intent intent = new Intent(getApplicationContext(), ChangeProfileActivity.class);
        startActivityForResult(intent, 2);
    }
    public void btnBeginRoutineClick(){
        Intent intent = new Intent(getApplicationContext(), BeginRoutineActivity.class);
        startActivity(intent);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 || requestCode == 1) {
            if(resultCode == RESULT_OK){
                current_id = data.getStringExtra("profile_id");
                current_name = data.getStringExtra("profile_name");

                TextView tvLogin = (TextView)findViewById(R.id.tvProfileLogin);
                Button btnBegin = (Button)findViewById(R.id.btnBeginRoutine);

                tvLogin.setText("Olá "+current_name);
                tvLogin.setVisibility(View.VISIBLE);
                btnBegin.setEnabled(true);
            }
        }
    }

    /////////////////////////////
    ////////DATABASE MANAGER/////
    /////////////////////////////

    public void startDatabaseManager() {

        Intent dbmanager = new Intent(getApplicationContext(),AndroidDatabaseManager.class);
        startActivity(dbmanager);
    }


}
