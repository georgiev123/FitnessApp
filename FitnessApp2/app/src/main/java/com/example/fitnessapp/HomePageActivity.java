package com.example.fitnessapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.example.fitnessapp.Pedometer.StepDetector;
import com.example.fitnessapp.Pedometer.StepListener;
import com.example.fitnessapp.Workouts.FitnessProgramProposalActivity;
import com.example.fitnessapp.Workouts.WorkoutActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomePageActivity extends AppCompatActivity implements SensorEventListener, StepListener {

    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    public FirebaseAuth mauth;

    public TextView tvCalories;
    public static TextView stepCounter;

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;

    private MenuItem btnUsername;

    private SensorManager sensorManager;
    private android.hardware.Sensor accel;
    private StepDetector simpleStepDetector;

    public static String username;
    private static final String TEXT_NUM_STEPS = "Number of Steps: ";
    private int numSteps;
    private String TAG = "Home Activity";

    private ArrayList<String> arrNames = new ArrayList<>();
    private ArrayList<String> arrActivities = new ArrayList<>();
    private ArrayList<String> arrKindActivities = new ArrayList<>();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mauth = FirebaseAuth.getInstance();
        ProgramData.userProfile = mauth.getCurrentUser().getUid();

        recyclerView = findViewById(R.id.rvFolloweeFeed);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawer = findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        mDrawer.addDrawerListener(drawerToggle);

        nvDrawer = findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(android.hardware.Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);
//..................................................................................................................

        btnUsername = findViewById(R.id.btnNavProfile);
        tvCalories = findViewById(R.id.tvCaloriesHome);
        stepCounter = findViewById(R.id.tvPedometer);
        stepCounter.setTextSize(30);

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

        setRecyclerView();

        db.collection("Users").document(mauth.getCurrentUser().getUid())
                .collection("Workouts").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                db.collection("Users").document(document.getId())
                                        .collection("Achievements").document("Pedometer")
                                        .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {

                                        if (e != null) {
                                            Log.w(TAG, "Listen failed.", e);
                                            return;
                                        }

                                        if (snapshot != null && snapshot.exists()) {
                                            Map<String, Object> mp = new HashMap<>();
                                            mp.putAll(snapshot.getData());
                                        } else {
                                            Log.d(TAG, "Current data: null");
                                        }
                                    }
                                });
                            }
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });

        calculateCalories("HomePage", tvCalories, mauth, null);
    }

    private void setRecyclerView() {
        db.collection("Users").document(mauth.getCurrentUser().getUid())
                .collection("Followings").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (final QueryDocumentSnapshot document : task.getResult()) {
                        Map<String, Object> map = new HashMap<>();
                        map.putAll(document.getData());
                        arrNames.add(document.getId());
                        db.collection("Users").document(map.get("user_uid").toString())
                                .collection("Achievements").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task1) {
                                if(task1.isSuccessful()) {
                                    for (QueryDocumentSnapshot document1 : task1.getResult()) {
                                        Map<String, Object> map1 = new HashMap<>();
                                        map1.putAll(document1.getData());


                                        arrActivities.add(map1.get("steps").toString());
                                        arrKindActivities.add(document1.getId());
                                    }

                                    mAdapter = new RecyclerViewFolowee(arrNames, arrActivities, arrKindActivities);
                                    recyclerView.setAdapter(mAdapter);
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
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
            case R.id.btnFitnesProgram:
                startActivity(new Intent(this, FitnessProgramProposalActivity.class));
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
//        setTitle(menuItem.getTitle());
        // Close the navigation drawer
//        mDrawer.closeDrawers();
    }

    public void calculateCalories(final String  calledFromActivity ,final TextView caloriesTV, final FirebaseAuth fAuth, final TextView macrosTV) {
        final DocumentReference docRef = db.collection("Users").document(fAuth.getCurrentUser().getUid());
        docRef.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if(document.exists()) {
                                String gender;
                                String activityLevel;
                                String trainingGoal;
                                Double weight;
                                Double height;
                                Double age;
                                Double weeklyGoal;
                                Double calories;

                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                gender = document.getString("gender");
                                activityLevel = document.getString("activity_level");
                                weight = document.getDouble("weight");
                                height = document.getDouble("height");
                                age = document.getDouble("age");
                                weeklyGoal = document.getDouble("weightLost_weekly");
                                trainingGoal = document.getString("training_goal");


                                if(gender.equals("male")) {
                                    calories = (10*weight) + (6.25*(height*100)) - (5*age) + 5;
                                }else{
                                    calories = (10*weight) + (6.25*(height*100)) - (5*age) - 161;
                                }

                                if(activityLevel.equals("Not Very Active")) {
                                    calories *= 1.2;
                                }else if(activityLevel.equals("Lightly Active")) {
                                    calories *= 1.375;
                                }else if(activityLevel.equals("Active")) {
                                    calories *= 1.55;
                                }else {
                                    calories *= 1.725;
                                }

                                if(trainingGoal.equals("Gain Weight")) {
                                    calories += ((weeklyGoal*1500)/2);
                                }else if (trainingGoal.equals("Lose Weight")) {
                                    calories -= ((weeklyGoal*1000)/2);
                                }


//                                Toast.makeText(HomePageActivity.this, "calories =" + calories.intValue(), Toast.LENGTH_LONG).show();
                                Double caloriesRemaining = calories-ProgramData.caloriesIntake;
                                caloriesTV.setText(calories.intValue() + "   -  " + ProgramData.caloriesIntake.intValue() + "   =   " + caloriesRemaining.intValue());
                                if(calledFromActivity.equals("CaloriesDiary")) {
                                    Double carbs;
                                    Double protein;
                                    Double fats;

                                   if(trainingGoal.equals("Lose Weight")) {
                                        carbs = calories*0.45;
                                        protein = calories*0.35;
                                        fats = calories*0.2;
                                    }else {
                                        carbs = calories*0.5;
                                        protein = calories*0.25;
                                        fats = calories*0.25;
                                    }

                                   macrosTV.setText("carbs :  " + (carbs.intValue() - ProgramData.carbsIntake.intValue()) +
                                           "   proteins :  " + (protein.intValue() - ProgramData.proteinsIntake.intValue())
                                           + "   fats :  " + (fats.intValue() - ProgramData.fatsIntake.intValue()));
                                }
                            }else {
                                Log.d(TAG, "No such document");
                            }
                        }else {
                            Log.d(TAG, "Get failed with.", task.getException());
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

        stepCounter.setText(TEXT_NUM_STEPS + numSteps);
        sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == android.hardware.Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void onAccuracyChanged(android.hardware.Sensor sensor, int accuracy) {

    }

    @Override
    public void step(long timeNs) {
        numSteps++;
        stepCounter.setText(TEXT_NUM_STEPS + numSteps);

        if(numSteps >= 10) {

            Map<String, Object> map = new HashMap<>();
            map.put("steps", numSteps);

            db.collection("Users").document(mauth.getCurrentUser().getUid())
                    .collection("Achievements").document("Pedometer")
                    .set(map);
        }
    }


    @Override
    public void onRestart() {
        super.onRestart();
        setRecyclerView();
        calculateCalories("HomePage", tvCalories, mauth, null);
    }


}
