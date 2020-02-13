package com.example.myapplicationtutorial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class Workouts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts);
    }

    public void createAbsExercises(View view) {
        Intent absIntent = new Intent(this, AbsExercises.class);
        startActivity(absIntent);
    }
    

}
