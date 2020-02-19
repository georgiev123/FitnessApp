package com.example.fitnessapp.Workouts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fitnessapp.R;

public class WorkoutActivity extends AppCompatActivity {

    private Button btnAbs, btnBiceps, btnBack, btnChest;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        btnAbs = findViewById(R.id.btnAbsEx);
        btnBiceps = findViewById(R.id.btnBicepsEx);
        btnBack = findViewById(R.id.btnBackEx);
        btnChest = findViewById(R.id.btnChestEx);

        btnAbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WorkoutActivity.this, AbsExercisesActivity.class));
            }
        });

        btnBiceps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WorkoutActivity.this, BicepsExercisesActivity.class));
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WorkoutActivity.this, BackExercisesActivity.class));
            }
        });

        btnChest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WorkoutActivity.this, ChestExercisesActivity.class));
            }
        });

    }
}
