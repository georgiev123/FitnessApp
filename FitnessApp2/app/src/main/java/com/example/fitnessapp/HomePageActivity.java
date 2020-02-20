package com.example.fitnessapp;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.example.fitnessapp.Pedometer.StepDetector;
import com.example.fitnessapp.Pedometer.StepListener;
import com.example.fitnessapp.Workouts.WorkoutActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomePageActivity extends AppCompatActivity implements SensorEventListener, StepListener {

    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    public FirebaseAuth mauth;

    private Button btnLogOut;
    private Button btnCaloriesDiary;
    private Button btnWorkouts;
    private Button btnHistoryEx;
    public TextView currentUsername;
    public TextView tvCalories;

    public String username;
    public String gender;
    public String activityLevel;
    public String trainingGoal;
    public Double weight;
    public Double height;
    public Double age;
    public Double weeklyGoal;
    public Double calories;


    private TextView textView;
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TEXT_NUM_STEPS = "Number of Steps: ";
    private int numSteps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mauth = FirebaseAuth.getInstance();

        btnWorkouts = findViewById(R.id.btnWorkouts);
        btnCaloriesDiary = findViewById(R.id.btnCaloriesDiary);
        btnLogOut = findViewById(R.id.btnLogout);
        btnHistoryEx = findViewById(R.id.btnExHistory);
        tvCalories = findViewById(R.id.tvCaloriesHome);
        currentUsername = findViewById(R.id.tvUsername);
        textView = findViewById(R.id.tvPedometer);
        textView.setTextSize(30);

        DocumentReference docRef = db.collection("Users").document(mauth.getCurrentUser().getUid());
        docRef.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if(document.exists()) {
                                Log.d("asdf", "DocumentSnapshot data: " + document.getData());
                                username = document.getString("username");
                                currentUsername.setText(username);
                            }else {
                                Log.d("asdf", "No such document");
                            }
                        }else {
                            Log.d("asdf", "Get failed with.", task.getException());
                        }
                    }
                });


        calculateCalories();





        btnHistoryEx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePageActivity.this, ExerciseHistoryActivity.class));
            }
        });


        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomePageActivity.this, "You logged out.", Toast.LENGTH_LONG).show();
                mauth.getInstance().signOut();
                Intent mainActivity = new Intent(HomePageActivity.this, MainActivity.class);
                startActivity(mainActivity);
                finish();
            }
        });


        btnCaloriesDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent caloriesDiary = new Intent(HomePageActivity.this, CaloriesDiaryActivity.class);
                startActivity(caloriesDiary);
            }
        });


        btnWorkouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePageActivity.this, WorkoutActivity.class));
            }
        });




        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);


    }

    private void calculateCalories() {
        final DocumentReference docRef = db.collection("Users").document(mauth.getCurrentUser().getUid());
        docRef.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if(document.exists()) {
                                Log.d("asdf", "DocumentSnapshot data: " + document.getData());
                                gender = document.getString("gender");
                                activityLevel = document.getString("activity_level");
                                weight = document.getDouble("weight");
                                height = document.getDouble("height");
                                age = document.getDouble("age");
                                weeklyGoal = document.getDouble("weightLost_weekly");
                                trainingGoal = document.getString("training_goal");


                                if(gender.equals("male")) {
                                    calories = 800+(11*weight) + (66*height) - (8*age);
                                }else{
                                    calories = 700+(8*weight) + (33*height) - (5*age);
                                }

                                if(activityLevel.equals("Not Very Active")) {
                                    calories *= 2.5;
                                }else if(activityLevel.equals("Lightly Active")) {
                                    calories *= 2.65;
                                }else if(activityLevel.equals("Active")) {
                                    calories *= 2.8;
                                }else {
                                    calories *= 2.9;
                                }

                                calories /= (weeklyGoal*3);

                                if(trainingGoal.equals("Lose Weight")) {
                                    calories -= 200;

                                }else if(trainingGoal.equals("Gain Weight")) {
                                    calories += 200;
                                }

                                Toast.makeText(HomePageActivity.this, "calories =" + calories.intValue(), Toast.LENGTH_LONG).show();
                                tvCalories.setText(calories.intValue() + " - 0" + " = error");
                            }else {
                                Log.d("asdf", "No such document");
                            }
                        }else {
                            Log.d("asdf", "Get failed with.", task.getException());
                        }
                    }
                });
    }


    @Override
    public void onResume() {
        super.onResume();
//        numSteps = 0;
        textView.setText(TEXT_NUM_STEPS + numSteps);
        sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void step(long timeNs) {
        numSteps++;
        textView.setText(TEXT_NUM_STEPS + numSteps);
    }


}
