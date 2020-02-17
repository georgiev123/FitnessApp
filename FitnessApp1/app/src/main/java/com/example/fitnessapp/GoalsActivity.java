package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GoalsActivity extends AppCompatActivity {

    private FirebaseAuth mauth;
    private FirebaseDatabase mDB = FirebaseDatabase.getInstance();
    private DatabaseReference userRef;

    private Button btnContinue;
    private EditText etGoalWeight;
    private Spinner spTrainingGoal;
    private static final String[] trainingGoals = {"Lose Weight", "Maintain Weight", "Gain Weight"};
    private CheckBox cbFirst;
    private CheckBox cbSecond;
    private CheckBox cbThird;
    private CheckBox cbForth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);

        mauth = FirebaseAuth.getInstance();
        userRef = mDB.getReference();

        btnContinue = findViewById(R.id.btnContinueGoals);
        etGoalWeight = findViewById(R.id.etGoalWeight);
        spTrainingGoal = findViewById(R.id.spTrainingGoal);
        cbFirst = findViewById(R.id.cbFirst);
        cbSecond = findViewById(R.id.cbSecond);
        cbThird = findViewById(R.id.cbThird);
        cbForth = findViewById(R.id.cbForth);


        ArrayAdapter<String> adapterTrainingGoal = new ArrayAdapter(this, android.R.layout.simple_spinner_item, trainingGoals);
        adapterTrainingGoal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTrainingGoal.setAdapter(adapterTrainingGoal);


        cbFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbSecond.setChecked(false);
                cbThird.setChecked(false);
                cbForth.setChecked(false);
            }
        });

        cbSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbFirst.setChecked(false);
                cbThird.setChecked(false);
                cbForth.setChecked(false);
            }
        });

        cbThird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbFirst.setChecked(false);
                cbSecond.setChecked(false);
                cbForth.setChecked(false);
            }
        });

        cbForth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbFirst.setChecked(false);
                cbSecond.setChecked(false);
                cbThird.setChecked(false);
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String goalWeight = etGoalWeight.getText().toString();
                if((cbFirst.isChecked() || cbSecond.isChecked() || cbThird.isChecked() || cbForth.isChecked()) && goalWeight != "") {

                    userRef = mDB.getReference("Users/user_" + mauth.getCurrentUser().getUid() + "/personal_information/physical_data/training_goal");
                    userRef.setValue(spTrainingGoal.getSelectedItem().toString());

                    userRef = mDB.getReference("Users/user_" + mauth.getCurrentUser().getUid() + "/personal_information/physical_data/weight_goal");
                    userRef.setValue(goalWeight).toString();

                    userRef = mDB.getReference("Users/user_" + mauth.getCurrentUser().getUid() + "/personal_information/physical_data/weightLost_weekly");
                    if(cbFirst.isChecked()) {
                        userRef.setValue(0.25).toString();
                    }else if(cbSecond.isChecked()) {
                        userRef.setValue(0.5).toString();
                    }else if(cbThird.isChecked()) {
                        userRef.setValue(0.75).toString();
                    }else if(cbForth.isChecked()) {
                        userRef.setValue(1).toString();
                    }


                    Intent homePage = new Intent(GoalsActivity.this, HomePageActivity.class);
                    startActivity(homePage);
                    finish();

                }else {
                    Toast.makeText(GoalsActivity.this, "You have invalid information.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
