package com.example.myapplicationtutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

public class PersonalInformation extends AppCompatActivity {

    private Spinner spActivityLvl;
    private Spinner spHeight;
    private static final String[] activityLevels = {"NO activity", "some activity", "a lot activity"};
    private static final String[] heights = {"1.80", "1.60", "1.70"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);

        spActivityLvl = (Spinner)findViewById(R.id.spActivityLevel);
        spHeight = (Spinner)findViewById(R.id.spHeight);
        ArrayAdapter<String>adapterActivity = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,activityLevels);
        ArrayAdapter<String>adapterHeight = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,heights);

        adapterActivity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spActivityLvl.setAdapter(adapterActivity);
        adapterHeight.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spHeight.setAdapter(adapterHeight);
//        spinner.setOnItemSelectedListener(this);
    }

    public void clickMale(View view) {
        ImageView MaleImage = findViewById(R.id.ivMale);
        ImageView FemaleImage = findViewById(R.id.ivFemale);
        MaleImage.setAlpha((float)1);
        FemaleImage.setAlpha((float)0.5);

        Button btnMale = findViewById(R.id.btnMale);
        Button btnFemale = findViewById(R.id.btnFemale);
        btnMale.setAlpha((float)1);
        btnFemale.setAlpha((float)0.5);
    }

    public void clickFemale(View view) {
        ImageView MaleImage = findViewById(R.id.ivMale);
        ImageView FemaleImage = findViewById(R.id.ivFemale);
        MaleImage.setAlpha((float)0.5);
        FemaleImage.setAlpha((float)1);

        Button btnMale = findViewById(R.id.btnMale);
        Button btnFemale = findViewById(R.id.btnFemale);
        btnMale.setAlpha((float)0.5);
        btnFemale.setAlpha((float)1);
    }

    public void sendToMainScreen(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

//    @Override
//    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
//
//        switch (position) {
//            case 0:
//                // Whatever you want to happen when the first item gets selected
//                break;
//            case 1:
//                // Whatever you want to happen when the second item gets selected
//                break;
//            case 2:
//                // Whatever you want to happen when the thrid item gets selected
//                break;
//
//        }
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//        // TODO Auto-generated method stub
//    }
}
