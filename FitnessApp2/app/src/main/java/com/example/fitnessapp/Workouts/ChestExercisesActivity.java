package com.example.fitnessapp.Workouts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fitnessapp.ProgramData;
import com.example.fitnessapp.R;

public class ChestExercisesActivity extends AppCompatActivity {
    private Button btnPushUps, btnBenchPress, btnDumbbellFlys, btnDumbbellPress;
    private String imgEx = "side_bridge.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chest_exercises);

        btnPushUps = findViewById(R.id.btnExercise1);
        btnBenchPress = findViewById(R.id.btnExercise2);
        btnDumbbellFlys = findViewById(R.id.btnExercise3);
        btnDumbbellPress = findViewById(R.id.btnExercise4);

        btnPushUps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgramData.exerciseName = "Push-Ups";
                ProgramData.whichActivity = "Chest";
                ProgramData.imageExercise = imgEx;
                startActivity(new Intent(ChestExercisesActivity.this, ExerciseCustomViewActivity.class));
                finish();
            }
        });


        btnBenchPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgramData.exerciseName = "Bench Presses";
                ProgramData.whichActivity = "Chest";
                ProgramData.imageExercise = imgEx;
                startActivity(new Intent(ChestExercisesActivity.this, ExerciseCustomViewActivity.class));
                finish();
            }
        });


        btnDumbbellFlys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgramData.exerciseName = "Dumbbell Flys";
                ProgramData.whichActivity = "Chest";
                ProgramData.imageExercise = imgEx;
                startActivity(new Intent(ChestExercisesActivity.this, ExerciseCustomViewActivity.class));
                finish();
            }
        });


        btnDumbbellPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgramData.exerciseName = "Dumbbell Presses";
                ProgramData.whichActivity = "Chest";
                ProgramData.imageExercise = imgEx;
                startActivity(new Intent(ChestExercisesActivity.this, ExerciseCustomViewActivity.class));
                finish();
            }
        });
    }
}
