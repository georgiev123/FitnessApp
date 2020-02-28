package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Map;

public class CaloriesDiaryActivity extends AppCompatActivity {
    private FirebaseAuth mauth;
    private Button btnMeal1;
    private Button btnMeal2;
    private Button btnMeal3;
    private TextView tvCaloriesGoal;
    private TextView tvMacros;
    private HomePageActivity hp;

    public String gender;
    public String activityLevel;
    public String trainingGoal;
    public Double weight;
    public Double height;
    public Double age;
    public Double weeklyGoal;
    public Double calories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories_diary);

        mauth = FirebaseAuth.getInstance();
        hp = new HomePageActivity();

        tvCaloriesGoal = findViewById(R.id.tvCaloriesGoal);
        tvMacros = findViewById(R.id.tvMacros);
//        tvCaloriesGoal.setText(((ProgramData) this.getApplication()).getCalories().toString() + " - 0" + " = error");

        btnMeal1 = findViewById(R.id.btnAddFoodMeal1);
        btnMeal1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToFoodActivity();
            }
        });

        btnMeal2 = findViewById(R.id.btnAddFoodMeal2);
        btnMeal2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToFoodActivity();
            }
        });

        btnMeal3 = findViewById(R.id.btnAddFoodMeal3);
        btnMeal3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToFoodActivity();
            }
        });

        hp.calculateCalories("CaloriesDiary", tvCaloriesGoal, mauth, tvMacros);

    }

    private void sendToFoodActivity() {
        Intent foodActivity = new Intent(CaloriesDiaryActivity.this, FoodActivity.class);
        startActivity(foodActivity);
    }

}
