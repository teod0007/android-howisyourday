package com.prjproject.tcc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.prjproject.tcc.R;

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

        btnNewProfile.setOnClickListener(this);
        btnChangeProfile.setOnClickListener(this);
        btnBeginRoutine.setOnClickListener(this);
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


}
