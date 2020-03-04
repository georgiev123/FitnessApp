package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.HashMap;
import java.util.Map;

public class PersonalInformationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Spinner spActivityLvl;
    private Spinner spHeight;
    private static final String[] activityLevels = {"Not Very Active", "Lightly Active", "Active", "Very Active"};
    private static final String[] heights = {"1.90","1.80", "1.70","1.60", "1.50"};

    private Button btnContinue;
    private EditText etAge;
    private EditText etWeight;
    private String gender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);

        mAuth = FirebaseAuth.getInstance();

        etAge = findViewById(R.id.etAge);
        etWeight = findViewById(R.id.etWeight);
        spActivityLvl = findViewById(R.id.spActivityLevel);
        spHeight = findViewById(R.id.spHeight);

        ArrayAdapter<String> adapterActivity = new ArrayAdapter(this, android.R.layout.simple_spinner_item,activityLevels);
        ArrayAdapter<String>adapterHeight = new ArrayAdapter(this, android.R.layout.simple_spinner_item,heights);
        adapterActivity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterHeight.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spActivityLvl.setAdapter(adapterActivity);
        spHeight.setAdapter(adapterHeight);

        btnContinue = findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Double age = Double.parseDouble(etAge.getText().toString());
                Double weight = Double.parseDouble(etWeight.getText().toString());
                Double height = Double.parseDouble(spHeight.getSelectedItem().toString());


//                Map<String, Object> currUser = new HashMap<>();
                ProgramData.userInfoMap.put("age",  age);
                ProgramData.userInfoMap.put("gender", gender);
                ProgramData.userInfoMap.put("weight", weight);
                ProgramData.userInfoMap.put("height", height);
                ProgramData.userInfoMap.put("activity_level", spActivityLvl.getSelectedItem().toString());

//                db.collection("Users").document(mAuth.getCurrentUser().getUid())
//                        .set(currUser, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.d("asdf", "DocumentSnapshot successful written!");
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w("asdf", "Error!", e);
//                    }
//                });

                startActivity(new Intent(PersonalInformationActivity.this, GoalsActivity.class));
                finish();
            }
        });


    }

    public void clickMale(View view) {
        ImageView MaleImage = findViewById(R.id.ivMale);
        ImageView FemaleImage = findViewById(R.id.ivFemale);
        MaleImage.setAlpha((float)1);
        FemaleImage.setAlpha((float)0.5);

        Button btnMale = findViewById(R.id.btnMale);
        Button btnFemale = findViewById(R.id.btnFemale);
        btnMale.setAlpha((float)1);
        btnFemale.setAlpha((float)0.5);

        gender = "male";
    }

    public void clickFemale(View view) {
        ImageView MaleImage = findViewById(R.id.ivMale);
        ImageView FemaleImage = findViewById(R.id.ivFemale);
        MaleImage.setAlpha((float)0.5);
        FemaleImage.setAlpha((float)1);

        Button btnMale = findViewById(R.id.btnMale);
        Button btnFemale = findViewById(R.id.btnFemale);
        btnMale.setAlpha((float)0.5);
        btnFemale.setAlpha((float)1);

        gender = "female";
    }

}
