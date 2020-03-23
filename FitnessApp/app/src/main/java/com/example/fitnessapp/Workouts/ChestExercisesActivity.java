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

public class ChestExercisesActivity extends AppCompatActivity {

    private String TAG = "Chest Exercises";

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

        Toolbar mToolbar = findViewById(R.id.toolbarChestExercises);
        mToolbar.setTitle(TAG);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setNavigationIcon(R.drawable.ic_back_arrow);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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
                ProgramData.exerciseName = "Dumbbell Flyes";
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
