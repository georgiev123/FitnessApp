package com.example.fitnessapp.Workouts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fitnessapp.ProgramData;
import com.example.fitnessapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shrikanthravi.collapsiblecalendarview.data.Day;
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExerciseCustomViewActivity extends AppCompatActivity {

//    https://github.com/shrikanth7698/Collapsible-Calendar-View-Android - collapsible-calendar-view

    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private TextView exName;
    private Button btnAddHistory;
    private Button btnBack;
    private EditText etExWeight;
    private EditText etRepetitions;

    public String exerciseName;
    public String whichActivity;
    public String formattedDate;
    private ImageView exerciseImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_custom_view);

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy-hh-mm");
        formattedDate = df.format(c);

        mAuth = FirebaseAuth.getInstance();

        whichActivity = ((ProgramData) this.getApplication()).getWhichActivity();
        exerciseName = ((ProgramData) this.getApplication()).getExerciseName();
        exerciseImage = findViewById(R.id.ivExerciseImage);
        // Да се измисли начин за автоматизирано взимане
        exerciseImage.setImageResource(R.drawable.side_bridge);


        etExWeight = findViewById(R.id.etExWeight);
        etRepetitions = findViewById(R.id.etRepetition);
        exName = findViewById(R.id.tvExercise);
        exName.setText(exerciseName);

        btnAddHistory = findViewById(R.id.btnAddSession);
        btnAddHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double currWeight = Double.parseDouble(etExWeight.getText().toString());
                Double currRepetition = Double.parseDouble(etRepetitions.getText().toString());

                Map<String, Object> currUserEx = new HashMap<>();
                currUserEx.put("weight", currWeight);
                currUserEx.put("repetition", currRepetition);

                db.collection("Users").document(mAuth.getCurrentUser().getUid())
                        .collection("Workouts").document(whichActivity)
                        .collection(exerciseName).document(formattedDate)
                        .set(currUserEx).addOnSuccessListener(new OnSuccessListener<Void>() {
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
            }
        });

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(whichActivity.equals("Abs")) {
                    startActivity(new Intent(ExerciseCustomViewActivity.this, AbsExercisesActivity.class));
                }else if(whichActivity.equals("Back")) {
                    startActivity(new Intent(ExerciseCustomViewActivity.this, BackExercisesActivity.class));
                }else if(whichActivity.equals("Biceps")) {
                    startActivity(new Intent(ExerciseCustomViewActivity.this, BicepsExercisesActivity.class));
                }else {
                    startActivity(new Intent(ExerciseCustomViewActivity.this, ChestExercisesActivity.class));
                }
                finish();

            }
        });

    }
}
