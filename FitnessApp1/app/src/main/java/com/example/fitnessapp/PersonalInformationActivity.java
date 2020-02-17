package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

public class PersonalInformationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDB = FirebaseDatabase.getInstance();
    private DatabaseReference userRef;

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

                userRef = mDB.getReference("Users/user_" + mAuth.getCurrentUser().getUid() + "/physical_data/height");
                userRef.setValue(spHeight.getSelectedItem().toString());
                userRef = mDB.getReference("Users/user_" + mAuth.getCurrentUser().getUid() + "/physical_data/weight");
                userRef.setValue(etWeight.getText().toString());
                userRef = mDB.getReference("Users/user_" + mAuth.getCurrentUser().getUid() + "/physical_data/activityLevel");
                userRef.setValue(spActivityLvl.getSelectedItem().toString());
                userRef = mDB.getReference("Users/user_" + mAuth.getCurrentUser().getUid() + "/age");
                userRef.setValue(etAge.getText().toString());
                userRef = mDB.getReference("Users/user_" + mAuth.getCurrentUser().getUid() + "/gender");
                userRef.setValue(gender);






                Intent homePage = new Intent(PersonalInformationActivity.this, GoalsActivity.class);
                startActivity(homePage);
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
