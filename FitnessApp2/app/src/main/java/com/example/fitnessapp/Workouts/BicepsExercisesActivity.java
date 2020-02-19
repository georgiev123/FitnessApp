package com.example.fitnessapp.Workouts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fitnessapp.ProgramData;
import com.example.fitnessapp.R;

public class BicepsExercisesActivity extends AppCompatActivity {

    private Button btnLoqPulley, btnCurls, btnReverseCurls, btnHammerCurls;
    private String imgEx = "side_bridge.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biceps_exercises);

        btnLoqPulley = findViewById(R.id.btnExercise1);
        btnCurls = findViewById(R.id.btnExercise2);
        btnReverseCurls = findViewById(R.id.btnExercise3);
        btnHammerCurls = findViewById(R.id.btnExercise4);

        btnLoqPulley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgramData.exerciseName = "Low Pulley Curls";
                ProgramData.whichActivity = "Biceps";
                ProgramData.imageExercise = imgEx;
                startActivity(new Intent(BicepsExercisesActivity.this, ExerciseCustomViewActivity.class));
                finish();
            }
        });


        btnCurls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgramData.exerciseName = "Curls";
                ProgramData.whichActivity = "Biceps";
                ProgramData.imageExercise = imgEx;
                startActivity(new Intent(BicepsExercisesActivity.this, ExerciseCustomViewActivity.class));
                finish();
            }
        });


        btnReverseCurls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgramData.exerciseName = "Reverse Curls";
                ProgramData.whichActivity = "Biceps";
                ProgramData.imageExercise = imgEx;
                startActivity(new Intent(BicepsExercisesActivity.this, ExerciseCustomViewActivity.class));
                finish();
            }
        });


        btnHammerCurls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgramData.exerciseName = "Hammer Curls";
                ProgramData.whichActivity = "Biceps";
                ProgramData.imageExercise = imgEx;
                startActivity(new Intent(BicepsExercisesActivity.this, ExerciseCustomViewActivity.class));
                finish();
            }
        });
    }
}
