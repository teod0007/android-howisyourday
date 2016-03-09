package com.prjproject.tcc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupButtons();

    }

    private void setupButtons() {
        Button btnCreateProfile = (Button) findViewById(R.id.btnNewProfile);
        Button btnChangeProfile = (Button) findViewById(R.id.btnChangeProfile);
        Button btnBeginRoutine = (Button) findViewById(R.id.btnBeginRoutine);

        btnCreateProfile.setOnClickListener(this);
    }

    public void btnCreateProfileClick(){
        Intent intent = new Intent(getApplicationContext(), CreateProfileActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.btnNewProfile:
                btnCreateProfileClick();
                break;
        }
    }
}
