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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

import java.util.HashMap;
import java.util.Map;

public class HomePageActivity extends AppCompatActivity implements SensorEventListener, StepListener {

    public FirebaseFirestore mDB = FirebaseFirestore.getInstance();
    public FirebaseAuth mauth;

    private Button btnLogOut;
    private Button btnCaloriesDiary;
    private Button btnWorkouts;
    private TextView currentUsername;
    private TextView tvCalories;

    public String username;
    public String gender;
    public String activityLevel;
    public String trainingGoal;
    public int weight;
    public int height;
    public int age;
    public int weeklyGoal;
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

        String userId = mauth.getCurrentUser().getUid();
        Map<String, Object> user = new HashMap<>();


        currentUsername = findViewById(R.id.tvUsername);
        currentUsername.setText(username);

//        calculateCalories(weight,height,age,weeklyGoal,gender,activityLevel,trainingGoal);

        tvCalories = findViewById(R.id.tvCaloriesHome);
//        tvCalories.setText(((ProgramData) this.getApplication()).getCalories().toString() + " - 0" + " = error");


        btnLogOut = findViewById(R.id.btnLogout);
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

        btnCaloriesDiary = findViewById(R.id.btnCaloriesDiary);
        btnCaloriesDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent caloriesDiary = new Intent(HomePageActivity.this, CaloriesDiaryActivity.class);
                startActivity(caloriesDiary);
            }
        });

        btnWorkouts = findViewById(R.id.btnWorkouts);
        btnWorkouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePageActivity.this, WorkoutActivity.class));
            }
        });

        textView = findViewById(R.id.tvPedometer);
        textView.setTextSize(30);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);


    }

    private void calculateCalories(int weight, int height, int age, int weeklyGoal, String gender, String activityLevel, String trainingGoal) {
        if(gender.equals("male")) {
            calories = 66+(2.8*weight) + (32.3*height) - (6.8*age);
        }else{
            calories = 65+(2*weight) + (11.9*height) - (4.7*age);
        }

        if(activityLevel.equals("Not Very Active")) {
            calories *= 1.3;
        }else if(activityLevel.equals("Lightly Active")) {
            calories *= 1.475;
        }else if(activityLevel.equals("Active")) {
            calories *= 1.65;
        }else {
            calories *= 1.85;
        }

        calories /= (weeklyGoal*10)/2;

        if(trainingGoal.equals("Lose Weight")) {
            calories -= 200;

        }else if(trainingGoal.equals("Gain Weight")) {
            calories += 200;
        }

        ((ProgramData) this.getApplication()).setCalories(calories);
    }


    @Override
    public void onResume() {
        super.onResume();
        numSteps = 0;
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
