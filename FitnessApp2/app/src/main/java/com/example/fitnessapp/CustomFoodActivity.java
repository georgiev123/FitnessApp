package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class CustomFoodActivity extends AppCompatActivity {

    public static TextView tvBarcode;
    public static TextView tvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_food);

        tvBarcode = findViewById(R.id.tvInfoBarcode);
        tvName = findViewById(R.id.tvFoodName);
    }
}
