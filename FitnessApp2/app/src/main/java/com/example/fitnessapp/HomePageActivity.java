package com.example.fitnessapp;

import android.animation.ObjectAnimator;
import android.content.ClipData;
import android.content.Intent;
import android.content.res.Configuration;
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
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
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
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.MenuItem;
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


    public TextView tvCalories;

    public static String username;
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
    private String TAG = "Home Activity";

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;

    private MenuItem btnUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mauth = FirebaseAuth.getInstance();
        ProgramData.userProfile = mauth.getCurrentUser().getUid();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawer = findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);

        nvDrawer = findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);

//..................................................................................................................

        btnUsername = findViewById(R.id.btnNavProfile);
        tvCalories = findViewById(R.id.tvCaloriesHome);
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
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                username = document.getString("username");
//                                btnUsername.setTitle(username);
                            }else {
                                Log.d(TAG, "No such document");
                            }
                        }else {
                            Log.d(TAG, "Get failed with.", task.getException());
                        }
                    }
                });

        calculateCalories();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    private void setupDrawerContent(NavigationView navigationView) {

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
//        Fragment fragment = new Fragment();
//        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.btnNavProfile:
                startActivity(new Intent(this, CustomProfileActivity.class));
                break;
            case R.id.btnExHistory:
                startActivity(new Intent(this, ExerciseHistoryActivity.class));
                break;
            case R.id.btnCaloriesDiary:
                startActivity(new Intent(this, CaloriesDiaryActivity.class));
                break;
            case R.id.btnWorkouts:
                startActivity(new Intent(this, WorkoutActivity.class));
                break;
            case R.id.btnFollowers:
                startActivity(new Intent(this, FindFriendsActivity.class));
                break;
            case R.id.btnLogout:
                Toast.makeText(HomePageActivity.this, "You logged out.", Toast.LENGTH_LONG).show();
                mauth.getInstance().signOut();
                startActivity(new Intent(HomePageActivity.this, MainActivity.class));
                finish();
        }

//        fragmentClass = HomePageActivity.class;

//        try {
//            fragment = (Fragment) fragmentClass.newInstance();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // Insert the fragment by replacing any existing fragment
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
//        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
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

                                if(trainingGoal.equals("Gain Weight")) {
                                    calories += ((weeklyGoal*1000)/height);
                                }else if (trainingGoal.equals("Lose Weight")) {
                                    calories /= (weeklyGoal*3);
                                }


                                if(trainingGoal.equals("Lose Weight")) {
                                    calories -= 200;

                                }else if(trainingGoal.equals("Gain Weight")) {
                                    calories += 200;
                                }

                                Toast.makeText(HomePageActivity.this, "calories =" + calories.intValue(), Toast.LENGTH_LONG).show();
                                Double caloriesRemaining = calories-ProgramData.caloriesIntake;
                                tvCalories.setText(calories.intValue() + "   -  " + ProgramData.caloriesIntake.intValue() + "   =   " + caloriesRemaining.intValue());
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        if(numSteps >= 10) {

//            db.collection("Users").document(mauth.getCurrentUser().getUid())
//                    .set()
        }
    }


}
