package com.prjproject.tcc.activities;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.prjproject.tcc.R;
import com.prjproject.tcc.controller.DatabaseController;
import com.prjproject.tcc.model.Profile;

public class CreateProfileActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText edtFieldName;
    private Resources resources;
    private DatabaseController dbController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        initViews();
        dbController = new DatabaseController(getApplicationContext());
    }

    private void initViews() {
        resources = getResources();

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                callClearErrors(s);
            }
        };

        edtFieldName = (EditText) findViewById(R.id.edtFieldName);
        edtFieldName.addTextChangedListener(textWatcher);
        Button btnCreateProfile = (Button) findViewById(R.id.btnCreateProfile);
        btnCreateProfile.setOnClickListener(this);
    }


    //Click handling
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnCreateProfile:
                btnCreateClick();
                break;
        }
    }

    private void btnCreateClick() {
        if (validateFields()) {
            if(dbController.insertProfile(new Profile(edtFieldName.getText().toString()))){
                finish();
            }
            else{
                Toast.makeText(this, resources.getString(R.string.profile_create_error), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void callClearErrors(Editable s) {
        if (!s.toString().isEmpty()) {
            clearErrorFields(edtFieldName);
        }
    }

    private boolean validateFields() {
        String user = edtFieldName.getText().toString().trim();
        return (!isEmptyFields(user) && hasSizeValid(user));
    }

    private boolean isEmptyFields(String user) {
        if (TextUtils.isEmpty(user)) {
            edtFieldName.requestFocus();
            edtFieldName.setError(resources.getString(R.string.profile_name_required));
            return true;
        }
        return false;
    }

    private boolean hasSizeValid(String user) {

        if (!(user.length() >= 3)) {
            edtFieldName.requestFocus();
            edtFieldName.setError(resources.getString(R.string.profile_name_size_invalid));
            return false;
        }

        return true;
    }

    private void clearErrorFields(EditText... editTexts) {
        for (EditText editText : editTexts) {
            editText.setError(null);
        }
    }

}
