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
import android.widget.Toast;

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
    private String TAG = "LogCustomActivity";

    private TextView exName;
    private TextView tvDescription;
    private Button btnAddHistory;
    private Button btnBack;
    private EditText etExWeight1;
    private EditText etRepetitions1;
    private EditText etExWeight2;
    private EditText etRepetitions2;
    private EditText etExWeight3;
    private EditText etRepetitions3;

    public String exerciseName;
    public String whichActivity;
    public String formattedDate;
    private ImageView exerciseImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_custom_view);

        mAuth = FirebaseAuth.getInstance();

        tvDescription = findViewById(R.id.tvExDescription);
        whichActivity = ((ProgramData) this.getApplication()).getWhichActivity();
        exerciseName = ((ProgramData) this.getApplication()).getExerciseName();
        exerciseImage = findViewById(R.id.ivExerciseImage);

        switch (exerciseName) {
            case "Side Bridge" :
                exerciseImage.setImageResource(R.drawable.side_bridge);
                tvDescription.setText("Muscle: Obliques " +
                        "\nStep 1: Lie on your side. Make sure one leg is on top of the other. " +
                        "Position the rorearm on the floor perpendicular with your body. " +
                        "\nStep 2: use your forearm to lift your upper body of the ground. " +
                        "The other arm is placed on the side of your body, Your body should be straight. " +
                        "Only your forearm and the side of your foot should be touching The floor. Hold this position, ");
                break;
            case "Sit Ups" :
                exerciseImage.setImageResource(R.drawable.sit_ups);
                break;
            case "Leg Raises" :
                exerciseImage.setImageResource(R.drawable.leg_raises);
                break;
            case "Crunches" :
                exerciseImage.setImageResource(R.drawable.crunches);
                break;
            case "Back Extensions" :
                exerciseImage.setImageResource(R.drawable.back_extentions);
                break;
            case "Dumbbell Shrugs" :
                exerciseImage.setImageResource(R.drawable.dumbbell_shrugs);
                break;
            case "Deadlifts" :
                exerciseImage.setImageResource(R.drawable.deadlift);
                break;
            case "Barbell Shrugs" :
                exerciseImage.setImageResource(R.drawable.barbbell_shrugs);
                break;
            case "Low Pulley Curls" :
                exerciseImage.setImageResource(R.drawable.low_pulley_curls);
                break;
            case "Curls" :
                exerciseImage.setImageResource(R.drawable.curls);
                break;
            case "Reverse Curls" :
                exerciseImage.setImageResource(R.drawable.reverse_curls);
                break;
            case "Hammer Curls" :
                exerciseImage.setImageResource(R.drawable.hammer_curls);
                break;
            case "Push-Ups" :
                exerciseImage.setImageResource(R.drawable.push_ups);
                break;
            case "Bench Presses" :
                exerciseImage.setImageResource(R.drawable.bench_presses);
                break;
            case "Dumbbell Flyes" :
                exerciseImage.setImageResource(R.drawable.dumbbell_flyes);
                break;
            case "Dumbbell Presses" :
                exerciseImage.setImageResource(R.drawable.dumbbell_presses);
                break;
        }



        etExWeight1 = findViewById(R.id.etExWeight1);
        etRepetitions1 = findViewById(R.id.etRepetition1);
        etExWeight2 = findViewById(R.id.etExWeight2);
        etRepetitions2 = findViewById(R.id.etRepetition2);
        etExWeight3 = findViewById(R.id.etExWeight3);
        etRepetitions3 = findViewById(R.id.etRepetition3);
        exName = findViewById(R.id.tvExercise);
        exName.setText(exerciseName);

        btnAddHistory = findViewById(R.id.btnAddSession);
        btnAddHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-YYYY hh:mm:ss");
                formattedDate = df.format(c);

                Map<String, Object> currUserEx = new HashMap<>();
                currUserEx.put("exercise_type", whichActivity);
                currUserEx.put("exercise_name", exerciseName);

                if(!etExWeight2.getText().toString().equals("") && !etRepetitions2.getText().toString().equals("")) {
                    Double currWeight2 = Double.parseDouble(etExWeight2.getText().toString());
                    Double currRepetition2 = Double.parseDouble(etRepetitions2.getText().toString());
                    currUserEx.put("weight2", currWeight2);
                    currUserEx.put("repetition2", currRepetition2);
                }

                if(!etExWeight3.getText().toString().equals("") && !etRepetitions3.getText().toString().equals("")) {
                    Double currWeight3 = Double.parseDouble(etExWeight3.getText().toString());
                    Double currRepetition3 = Double.parseDouble(etRepetitions3.getText().toString());
                    currUserEx.put("weight3", currWeight3);
                    currUserEx.put("repetition3", currRepetition3);
                }


                if(!etExWeight1.getText().toString().equals("") && !etRepetitions1.getText().toString().equals("")) {
                    Double currWeight1 = Double.parseDouble(etExWeight1.getText().toString());
                    Double currRepetition1 = Double.parseDouble(etRepetitions1.getText().toString());
                    currUserEx.put("weight1", currWeight1);
                    currUserEx.put("repetition1", currRepetition1);

                    db.collection("Users").document(mAuth.getCurrentUser().getUid())
                            .collection("Workouts").document(formattedDate)
                            .set(currUserEx).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ExerciseCustomViewActivity.this, "Your training set has been saven into the exercise history.", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error!", e);
                        }
                    });
                }

                etExWeight1.setText("");
                etExWeight2.setText("");
                etExWeight3.setText("");
                etRepetitions1.setText("");
                etRepetitions2.setText("");
                etRepetitions3.setText("");

            }
        });

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCurrActivity();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startCurrActivity();
    }

    private void startCurrActivity() {
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
}
