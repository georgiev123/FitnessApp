package com.example.myapplicationtutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView TvSteps;
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TEXT_NUM_STEPS = "Number of Steps: ";
    private int numSteps;

    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    private DatabaseReference dbRef;
    private FirebaseUser currentUser;

    private String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abs_exercises);

        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference();
        dbRef = FirebaseDatabase.getInstance().getReference();




        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    String value = dataSnapshot.getValue(String.class);
                    Log.d("asdfs", "Value is: " + value);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        //        initViews();

//NAVBAR
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        AppBarConfiguration appBarConfiguration =
//                new AppBarConfiguration.Builder(navController.getGraph()).build();
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        NavigationUI.setupWithNavController(toolbar, navController);



//КРАЧКОМЕТЪР
//        TvSteps = (TextView) findViewById(R.id.tv_steps);
//        Button BtnStart = (Button) findViewById(R.id.btn_start);
//        Button BtnStop = (Button) findViewById(R.id.btn_stop);

//        BtnStart.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                numSteps = 0;
//                sensorManager.registerListener(MainActivity.this, accel, SensorManager.SENSOR_DELAY_FASTEST);
//
//            }
//        });
//
//        BtnStop.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//
//                sensorManager.unregisterListener(MainActivity.this);
//
//            }
//        });
    }


//КАМЕРА ЗА ЧЕТЕНЕ НА БАРКОД
//    private void initViews() {
//        btnTakePicture = findViewById(R.id.btnTakePicture);
//        btnScanBarcode = findViewById(R.id.btnScanBarcode);
//        btnTakePicture.setOnClickListener(this);
//        btnScanBarcode.setOnClickListener(this);
//    }

//    public void onClick(View v) {
//
//        switch (v.getId()) {
//            case R.id.btnTakePicture:
//                startActivity(new Intent(MainActivity.this, PictureBarcodeActivity.class));
//                break;
//            case R.id.btnScanBarcode:
//                startActivity(new Intent(MainActivity.this, ScannedBarcodeActivity.class));
//                break;
//        }
//
//    }

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

//    @Override
//    public void step(long timeNs) {
//        numSteps++;
//        TvSteps.setText(TEXT_NUM_STEPS + numSteps);
//    }

//    public void openCamera(View view) {
//        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//        startActivity(intent);
//    }

    public void WorkoutsClick(View view) {
        Intent customExercise = new Intent(this, Workouts.class);
        startActivity(customExercise);
    }

}

