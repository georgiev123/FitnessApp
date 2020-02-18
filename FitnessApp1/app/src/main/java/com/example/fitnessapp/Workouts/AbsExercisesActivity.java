package com.example.fitnessapp.Workouts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fitnessapp.ProgramData;
import com.example.fitnessapp.R;

public class AbsExercisesActivity extends AppCompatActivity {

    private Button btnSideBridge, btnCrunches, btnLegRaises, btnSitUps;
    private String imgEx = "side_bridge.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abs_exercises);

        btnSideBridge = findViewById(R.id.btnExercise1);
        btnCrunches = findViewById(R.id.btnExercise2);
        btnLegRaises = findViewById(R.id.btnExercise3);
        btnSitUps = findViewById(R.id.btnExercise4);

        btnSideBridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgramData.exerciseName = "SideBridge";
                ProgramData.whichActivity = "Abs";
                ProgramData.imageExercise = imgEx;
                startActivity(new Intent(AbsExercisesActivity.this, ExerciseCustomViewActivity.class));
            }
        });
    }
}
