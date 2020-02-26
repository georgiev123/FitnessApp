package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CustomProfileActivity extends AppCompatActivity {

    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    public FirebaseAuth mauth;

    private TextView tvUsername;
    private TextView tvAge;
    private TextView tvGender;
    private TextView tvHeight;
    private TextView tvActivityLevel;
    private TextView tvWeight;
    private TextView tvWeightLostWeekly;
    private TextView tvWeightGoal;
    private TextView tvTrainingGoal;
    private Button btnBack;
    private ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_profile);

        mauth = FirebaseAuth.getInstance();

        profileImage = findViewById(R.id.ivProfileImage);
        btnBack = findViewById(R.id.btnBackProfile);
        tvUsername = findViewById(R.id.tvProfileUsername);
        tvAge = findViewById(R.id.tvProfileAge);
        tvGender = findViewById(R.id.tvProfileGender);
        tvHeight = findViewById(R.id.tvProfileHeight);
        tvWeight = findViewById(R.id.tvProfileWeight);
        tvActivityLevel = findViewById(R.id.tvProfileActivityLevel);
        tvWeightLostWeekly = findViewById(R.id.tvProfileWeightWeeklyGoal);
        tvWeightGoal = findViewById(R.id.tvProfileWeightGoal);
        tvTrainingGoal = findViewById(R.id.tvProfileTrainingGoal);

        tvWeightLostWeekly.setVisibility(View.GONE);


            db.collection("Users").document(ProgramData.userProfile)
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot doc = task.getResult();
                    Map<String, Object> map = new HashMap<>();
                    map.putAll(doc.getData());

                    tvUsername.setText("Username:" + map.get("username").toString());
                    tvActivityLevel.setText("Activity Level:" + map.get("activity_level").toString());
                    tvAge.setText("Age:" + map.get("age").toString());
                    tvGender.setText("Gender:" + map.get("gender").toString());
                    tvHeight.setText("Height:" + map.get("height").toString());
                    tvTrainingGoal.setText("Training Goal:" + map.get("training_goal").toString());
                    tvWeight.setText("Weight:" + map.get("weight").toString());
                    tvWeightGoal.setText("Weight Goal:" + map.get("weight_goal").toString());
                    if(map.get("gender").toString().equals("male")) {
                        profileImage.setImageResource(R.drawable.man);
                    }else {
                        profileImage.setImageResource(R.drawable.women);
                    }

                    if (!map.get("training_goal").equals("Maintain Weight")) {
                        tvWeightLostWeekly.setText("Weight Lost Weekly:" + map.get("weightLost_weekly").toString());
                        tvWeightLostWeekly.setVisibility(View.VISIBLE);
                    }

                }
            });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
