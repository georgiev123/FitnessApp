package com.example.fitnessapp.Workouts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fitnessapp.ProgramData;
import com.example.fitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class BackExercisesActivity extends AppCompatActivity {

    private String TAG = "Back Exercises";

    private Button btnBackExt, btnDeadlift, btnDumbbellShrugs, btnBarbellShrugs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_exercises);

        btnBackExt = findViewById(R.id.btnExercise1);
        btnDeadlift = findViewById(R.id.btnExercise2);
        btnDumbbellShrugs = findViewById(R.id.btnExercise3);
        btnBarbellShrugs = findViewById(R.id.btnExercise4);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbarBackExercises);
        mToolbar.setTitle(TAG);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setNavigationIcon(R.drawable.ic_back_arrow);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnBackExt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgramData.exerciseName = "Back Extensions";
                ProgramData.whichActivity = "Back";
                startActivity(new Intent(BackExercisesActivity.this, ExerciseCustomViewActivity.class));
                finish();
            }
        });


        btnBarbellShrugs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgramData.exerciseName = "Dumbbell Shrugs";
                ProgramData.whichActivity = "Back";
                startActivity(new Intent(BackExercisesActivity.this, ExerciseCustomViewActivity.class));
                finish();
            }
        });


        btnDeadlift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgramData.exerciseName = "Deadlifts";
                ProgramData.whichActivity = "Back";
                startActivity(new Intent(BackExercisesActivity.this, ExerciseCustomViewActivity.class));
                finish();
            }
        });


        btnDumbbellShrugs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgramData.exerciseName = "Barbell Shrugs";
                ProgramData.whichActivity = "Back";
                startActivity(new Intent(BackExercisesActivity.this, ExerciseCustomViewActivity.class));
                finish();
            }
        });
    }
}
