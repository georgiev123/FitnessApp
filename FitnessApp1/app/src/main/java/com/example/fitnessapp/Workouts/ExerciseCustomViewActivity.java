package com.example.fitnessapp.Workouts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fitnessapp.ProgramData;
import com.example.fitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shrikanthravi.collapsiblecalendarview.data.Day;
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExerciseCustomViewActivity extends AppCompatActivity {

//    https://github.com/shrikanth7698/Collapsible-Calendar-View-Android - collapsible-calendar-view

    private FirebaseAuth mAuth;

    private TextView exName;
    private Button btnAddHistory;
    private EditText etExWeight;
    private EditText etRepetitions;

    public String exerciseName;
    public String whichActivity;
    private Image exerciseImage;
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private Date date = new Date();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_custom_view);

        mAuth = FirebaseAuth.getInstance();

        whichActivity = ((ProgramData) this.getApplication()).getWhichActivity();
        exerciseName = ((ProgramData) this.getApplication()).getExerciseName();

        etExWeight = findViewById(R.id.etExWeight);
        etRepetitions = findViewById(R.id.etRepetition);
        exName = findViewById(R.id.tvExercise);
        exName.setText(exerciseName);

        btnAddHistory = findViewById(R.id.btnAddSession);
        btnAddHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currWeight = etExWeight.getText().toString();
                String currRepetition = etRepetitions.getText().toString();
                if(whichActivity.equals("Abs")) {
                    startActivity(new Intent(ExerciseCustomViewActivity.this, AbsExercisesActivity.class));
//                    userRef = mDB.getReference("Users/user_" + mAuth.getCurrentUser().getUid() + "/workouts/" + whichActivity + "/"+exerciseName+"/"+date+"/1/exercise_weight");
//                    userRef.setValue(currWeight);
//                    userRef = mDB.getReference("Users/user_" + mAuth.getCurrentUser().getUid() + "/workouts/" + whichActivity + "/"+exerciseName+"/"+date+"/1/exercise_repetition");
//                    userRef.setValue(currRepetition);
                }else if(whichActivity.equals("Back")) {
                    startActivity(new Intent(ExerciseCustomViewActivity.this, BackExercisesActivity.class));
                }else if(whichActivity.equals("Biceps")) {
                    startActivity(new Intent(ExerciseCustomViewActivity.this, BicepsExercisesActivity.class));
                }else {
                    startActivity(new Intent(ExerciseCustomViewActivity.this, ChestExercisesActivity.class));
                }



            }
        });
    }
}
