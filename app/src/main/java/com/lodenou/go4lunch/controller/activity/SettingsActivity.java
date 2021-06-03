package com.lodenou.go4lunch.controller.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import com.lodenou.go4lunch.R;


import java.util.Arrays;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {


    Spinner spinner;
    List<String> listSpinner = Arrays.asList("French", "English");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.settings, new SettingsFragment())
//                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
    }
//            setUpSpinner();
            backArrowSetting();
        }


//    public class SettingsFragment extends PreferenceFragmentCompat {
//        @Override
//        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
//            setPreferencesFromResource(R.xml.root_preferences, rootKey);
//
//        }}

        private void setUpSpinner() {
            spinner = (Spinner)findViewById(R.id.spinner);
//            ArrayAdapter<String> adapter = new ArrayAdapter<>(SettingsActivity.this,
//                    android.R.layout.simple_spinner_item, listSpinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listSpinner);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    switch (i) {
                        case 0:
                            //TODO SWITCH LANGUAGE TO FRENCH IF LANGUAGE ISNT ALREADY FRENCH
                            break;
                        case 1:
                            //TODO SWITCH LANGUAGE TO ENGLISH IF LANGUAGE ISNT ALREADY ENGLISH
                            break;
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    Toast.makeText(SettingsActivity.this, "Please, select a language", Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void backArrowSetting(){
            ImageButton backArrow = findViewById(R.id.back_arrow_setting);
            backArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}


