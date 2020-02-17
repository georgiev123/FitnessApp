package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HomePageActivity extends AppCompatActivity {

    public FirebaseDatabase mDB;
    public DatabaseReference userReference;
    public FirebaseAuth mauth;

    private Button btnLogOut;
    private Button btnCaloriesDiary;
    private TextView currentUsername;
    private TextView tvCalories;

    public String username;
    public String gender;
    public String activityLevel;
    public String trainingGoal;
    public int weight;
    public int height;
    public int age;
    public int weeklyGoal;
    public Double calories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mauth = FirebaseAuth.getInstance();

        String userId = mauth.getCurrentUser().getUid();
        mDB = FirebaseDatabase.getInstance();
        userReference = mDB.getReference();



        userReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Toast.makeText(HomePageActivity.this, "asdfads", Toast.LENGTH_LONG).show();
                username = dataSnapshot.child("username").getValue().toString();
                gender = dataSnapshot.child("gender").getValue().toString();
                activityLevel = dataSnapshot.child("physical_data").child("activityLevel").getValue().toString();
                trainingGoal = dataSnapshot.child("physical_data").child("training_goal").getValue().toString();
                weight = (Integer) dataSnapshot.child("physical_data").child("weight").getValue();
                height = (Integer) dataSnapshot.child("physical_data").child("height").getValue();
                age = (Integer) dataSnapshot.child("age").getValue();
                weeklyGoal = (Integer) dataSnapshot.child("physical_data").child("weightLost_weekly").getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        currentUsername = findViewById(R.id.tvUsername);
        currentUsername.setText(username);

        calculateCalories(weight,height,age,weeklyGoal,gender,activityLevel,trainingGoal);

        tvCalories = findViewById(R.id.tvCaloriesHome);
        tvCalories.setText(((ProgramData) this.getApplication()).getCalories().toString() + " - 0" + " = error");

        btnLogOut = findViewById(R.id.btnLogout);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomePageActivity.this, "You logged out.", Toast.LENGTH_LONG).show();
                mauth.getInstance().signOut();
                Intent mainActivity = new Intent(HomePageActivity.this, MainActivity.class);
                startActivity(mainActivity);
                finish();
            }
        });

        btnCaloriesDiary = findViewById(R.id.btnCaloriesDiary);
        btnCaloriesDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent caloriesDiary = new Intent(HomePageActivity.this, CaloriesDiaryActivity.class);
                startActivity(caloriesDiary);
            }
        });

    }

    private void calculateCalories(int weight, int height, int age, int weeklyGoal, String gender, String activityLevel, String trainingGoal) {
        if(gender.equals("male")) {
            calories = 66+(2.8*weight) + (32.3*height) - (6.8*age);
        }else{
            calories = 65+(2*weight) + (11.9*height) - (4.7*age);
        }

        if(activityLevel.equals("Not Very Active")) {
            calories *= 1.3;
        }else if(activityLevel.equals("Lightly Active")) {
            calories *= 1.475;
        }else if(activityLevel.equals("Active")) {
            calories *= 1.65;
        }else {
            calories *= 1.85;
        }

        calories /= (weeklyGoal*10)/2;

        if(trainingGoal.equals("Lose Weight")) {
            calories -= 200;

        }else if(trainingGoal.equals("Gain Weight")) {
            calories += 200;
        }

        ((ProgramData) this.getApplication()).setCalories(calories);
    }



}
