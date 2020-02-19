package com.example.fitnessapp.Workouts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fitnessapp.ProgramData;
import com.example.fitnessapp.R;

public class BackExercisesActivity extends AppCompatActivity {

    private Button btnBackExt, btnDeadlift, btnDumbbellShrugs, btnBarbellShrugs;
    private String imgEx = "side_bridge.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_exercises);

        btnBackExt = findViewById(R.id.btnExercise1);
        btnDeadlift = findViewById(R.id.btnExercise2);
        btnDumbbellShrugs = findViewById(R.id.btnExercise3);
        btnBarbellShrugs = findViewById(R.id.btnExercise4);

        btnBackExt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgramData.exerciseName = "Back Extensions";
                ProgramData.whichActivity = "Back";
                ProgramData.imageExercise = imgEx;
                startActivity(new Intent(BackExercisesActivity.this, ExerciseCustomViewActivity.class));
                finish();
            }
        });


        btnBarbellShrugs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgramData.exerciseName = "Dumbbell Shrugs";
                ProgramData.whichActivity = "Back";
                ProgramData.imageExercise = imgEx;
                startActivity(new Intent(BackExercisesActivity.this, ExerciseCustomViewActivity.class));
                finish();
            }
        });


        btnDeadlift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgramData.exerciseName = "Deadlifts";
                ProgramData.whichActivity = "Back";
                ProgramData.imageExercise = imgEx;
                startActivity(new Intent(BackExercisesActivity.this, ExerciseCustomViewActivity.class));
                finish();
            }
        });


        btnDumbbellShrugs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgramData.exerciseName = "Barbell Shrugs";
                ProgramData.whichActivity = "Back";
                ProgramData.imageExercise = imgEx;
                startActivity(new Intent(BackExercisesActivity.this, ExerciseCustomViewActivity.class));
                finish();
            }
        });
    }
}
