package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.chip.Chip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class GoalsActivity extends AppCompatActivity {

    private FirebaseAuth mauth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

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

        spTrainingGoal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spTrainingGoal.getSelectedItem().equals("Gain Weight")) {
                    cbFirst.setText("Gain 0.25 kg per week");
                    cbSecond.setText("Gain 0.5 kg per week");
                    cbFirst.setAlpha(1);
                    cbSecond.setAlpha(1);
                    cbThird.setAlpha(0);
                    cbForth.setAlpha(0);
                }else if(spTrainingGoal.getSelectedItem().equals("Maintain Weight")) {
                    cbFirst.setAlpha(0);
                    cbSecond.setAlpha(0);
                    cbThird.setAlpha(0);
                    cbForth.setAlpha(0);
                }else {
                    cbFirst.setText("Lose 0.25 kg per week");
                    cbSecond.setText("Lose 0.5 kg per week");
                    cbFirst.setAlpha(1);
                    cbSecond.setAlpha(1);
                    cbThird.setAlpha(1);
                    cbForth.setAlpha(1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
                if((cbFirst.isChecked() || cbSecond.isChecked() || cbThird.isChecked() || cbForth.isChecked() || spTrainingGoal.getSelectedItem().equals("Maintain Weight")) && goalWeight != "") {

                    Map<String, Object> currUser = new HashMap<>();
                    currUser.put("training_goal", spTrainingGoal.getSelectedItem());
                    currUser.put("weight_goal", goalWeight);
                    if(cbFirst.isChecked()) {
                        currUser.put("weightLost_weekly", 0.25);
                    }else if(cbSecond.isChecked()) {
                        currUser.put("weightLost_weekly", 0.5);
                    }else if(cbThird.isChecked()) {
                        currUser.put("weightLost_weekly", 0.75);
                    }else if(cbForth.isChecked()) {
                        currUser.put("weightLost_weekly", 1);
                    }

                    db.collection("Users").document(mauth.getCurrentUser().getUid())
                            .set(currUser, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("asdf", "DocumentSnapshot successful written!");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("asdf", "Error!", e);
                        }
                    });

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
