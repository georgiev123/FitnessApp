package com.example.myapplicationtutorial;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

public class AbsExercises extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abs_exercises);

    }

    public void createCustomWorkoutClick(View view) {
        Intent customExercise = new Intent(this, MakeCustomExercise.class);
        startActivity(customExercise);
    }

}
