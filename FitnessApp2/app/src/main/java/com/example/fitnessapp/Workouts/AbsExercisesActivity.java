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

public class AbsExercisesActivity extends AppCompatActivity {

    private String TAG = "Abs Exercises";

    private Button btnSideBridge, btnCrunches, btnLegRaises, btnSitUps, btncreateExericise;
    private String imgEx = "side_bridge.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abs_exercises);

        btnSideBridge = findViewById(R.id.btnExercise1);
        btnCrunches = findViewById(R.id.btnExercise2);
        btnLegRaises = findViewById(R.id.btnExercise3);
        btnSitUps = findViewById(R.id.btnExercise4);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbarAbsExercises);
        mToolbar.setTitle(TAG);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setNavigationIcon(R.drawable.ic_back_arrow);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSideBridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgramData.exerciseName = "Side Bridge";
                ProgramData.whichActivity = "Abs";
                ProgramData.imageExercise = imgEx;
                startActivity(new Intent(AbsExercisesActivity.this, ExerciseCustomViewActivity.class));
                finish();
            }
        });

        btnSitUps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgramData.exerciseName = "Sit Ups";
                ProgramData.whichActivity = "Abs";
                ProgramData.imageExercise = imgEx;
                startActivity(new Intent(AbsExercisesActivity.this, ExerciseCustomViewActivity.class));
                finish();
            }
        });

        btnLegRaises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgramData.exerciseName = "Leg Raises";
                ProgramData.whichActivity = "Abs";
                ProgramData.imageExercise = imgEx;
                startActivity(new Intent(AbsExercisesActivity.this, ExerciseCustomViewActivity.class));
                finish();
            }
        });

        btnCrunches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgramData.exerciseName = "Crunches";
                ProgramData.whichActivity = "Abs";
                ProgramData.imageExercise = imgEx;
                startActivity(new Intent(AbsExercisesActivity.this, ExerciseCustomViewActivity.class));
                finish();
            }
        });

    }
}
