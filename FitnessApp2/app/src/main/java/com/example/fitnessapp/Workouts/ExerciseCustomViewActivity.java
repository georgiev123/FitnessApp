package com.example.fitnessapp.Workouts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
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
import com.google.firebase.firestore.DocumentReference;
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

    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference currUserRef;
    private String TAG;

    private TextView tvDescription;
    private EditText etExWeight1;
    private EditText etRepetitions1;
    private EditText etExWeight2;
    private EditText etRepetitions2;
    private EditText etExWeight3;
    private EditText etRepetitions3;
    private Button btnAddExercise;

    public String exerciseName;
    public String whichActivity;
    public String formattedDate;
    private ImageView exerciseImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_custom_view);

        mAuth = FirebaseAuth.getInstance();
        currUserRef = db.collection("Users").document(mAuth.getCurrentUser().getUid());

        tvDescription = findViewById(R.id.tvExDescription);
        whichActivity = ProgramData.whichActivity;
        exerciseName = ProgramData.exerciseName;
        exerciseImage = findViewById(R.id.ivExerciseImage);
        TAG = exerciseName;

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbarCustomExercise);
        mToolbar.setTitle(TAG);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        btnAddExercise = mToolbar.findViewById(R.id.btnAddEx);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCurrActivity();
                finish();
            }
        });

        switch (exerciseName) {
            case "Side Bridge" :
                exerciseImage.setImageResource(R.drawable.side_bridge);
                tvDescription.setText("Muscle: Obliques " +
                        "\nStep 1: Lie on your side. Make sure one leg is on top of the other. " +
                        "Position the forearm on the floor perpendicular with your body. " +
                        "\nStep 2: Use your forearm to lift your upper body of the ground. " +
                        "The other arm is placed on the side of your body. Your body should be straight. " +
                        "Only your forearm and the side of your foot should be touching the floor. Hold this position. ");
                break;
            case "Sit Ups" :
                exerciseImage.setImageResource(R.drawable.sit_ups);
                tvDescription.setText("Muscle: Rectus Abdominis\n" +
                        "Step 1: Lie on the stability Position\n" +
                        "hands on yout head and feet on the\n" +
                        "Step 2:Raise your upper while\n" +
                        "k&ping back On the stability\n" +
                        "bail Hold for one second Return to starling\n" +
                        "position");
                break;
            case "Leg Raises" :
                exerciseImage.setImageResource(R.drawable.leg_raises);
                tvDescription.setText("Muscle: Rectus Abdominis\n" +
                        "Step 1: Lie the your legs\n" +
                        "Straight Position your hands behind your\n" +
                        "Step 2: Raise your upper body Hold\n" +
                        "for one seconff Return to starting positiom");
                break;
            case "Crunches" :
                exerciseImage.setImageResource(R.drawable.crunches);
                tvDescription.setText("Muscle: Rectus Abdominis\n" +
                        "Step 1: While lying\n" +
                        "yout legs. raise your heels an inch Off the\n" +
                        "and place yout hands by your sides.\n" +
                        "Step 2: Keeping pur parallel to the\n" +
                        "your torso and legs so they form a e. As\n" +
                        "you raise yourself. bend your knees and pull\n" +
                        "them up toward your Chest.");
                break;
            case "Back Extensions" :
                exerciseImage.setImageResource(R.drawable.back_extentions);
                tvDescription.setText("Muscle: Loner Back\n" +
                        "Step 1: into a\n" +
                        "station by placing feet undet the leg\n" +
                        "brace ancho' so that it is hrnked there,\n" +
                        "Step 2: With your upper thighs resting the bad.\n" +
                        "lock your hands behind head and bend\n" +
                        "forward at 'he hips until your upper body is\n" +
                        "just Shott Of being 90 degrees to the\n" +
                        "Step 3; Slowly raise your until in\n" +
                        "line with yow then lower it\n" +
                        "Alternat.vely. you can hold onto a weight by\n" +
                        "it with arms across your chest\n" +
                        "and do 'he\n");
                break;
            case "Dumbbell Shrugs" :
                exerciseImage.setImageResource(R.drawable.dumbbell_shrugs);
                tvDescription.setText("Muscle: Trapezius\n" +
                        "Step 1: Stand upright two fairly\n" +
                        "Heavy dumbbells at your Sides. with\n" +
                        "palms facing each other (neutral-grip).\n" +
                        "Step 2: Keep your Shoulders relaxed Shrug\n" +
                        "your shoulders as if you we«e trying to touch\n" +
                        "them to your ears„\n" +
                        "Step 3: Hotd the top most position. then\n" +
                        "gradually lower them to the starling posilico\n" +
                        "Do not your Or shift head\n" +
                        "forward during the motion Repeat");
                break;
            case "Deadlifts" :
                exerciseImage.setImageResource(R.drawable.deadlift);
                tvDescription.setText("Step 1: Stand \"Ith your feet hip-distance apart\n" +
                        "and bend down in sitting until your\n" +
                        "t the Keep back\n" +
                        "Straight and make Sure that Spine iS as\n" +
                        "close to the neutral position as possib'e, Take\n" +
                        "an grip on the bar hands\n" +
                        "Slightly wider than a Shout-dewidth apart\n" +
                        "Step 2; Exhale. draw your abdominal rnuscres\n" +
                        "in. and lilt the bat by pushing up thraliga\n" +
                        "Step 3: AS the bar reaches yCwr knees during\n" +
                        "the lilt phase. push yrmr nips forward to rose\n" +
                        "your torso so that you are Standing tall with\n" +
                        "by yow sides and bar resting\n");
                break;
            case "Barbell Shrugs" :
                exerciseImage.setImageResource(R.drawable.barbbell_shrugs);
                tvDescription.setText("Muscle: Trapezius\n" +
                        "Step 1: Stand upright two fairly\n" +
                        "Heavy barbell at your Sides. With your palms\n" +
                        "facing each other (neutral-grip).\n" +
                        "Step 2: Keep Shoulders relaxed Shrug\n" +
                        "your shoulders as if you we«e trying to\n" +
                        "them to your ears„\n" +
                        "Step 3: Hotd the top most position. then\n" +
                        "gradually lower them to the starling posi1iLY1.\n" +
                        "Do not your eltms Or shifl head\n" +
                        "forward during the motion\n");
                break;
            case "Low Pulley Curls" :
                exerciseImage.setImageResource(R.drawable.low_pulley_curls);
                tvDescription.setText("Muscle: Biceps grachii\n" +
                        "Step 1: Adjust to the lowest\n" +
                        "While standing straight. face the pulley Hold\n" +
                        "handle with underhand qrip.\n" +
                        "Step 2: Lift handle with Hold for\n" +
                        "one second. Return to starting positiom");
                break;
            case "Curls" :
                exerciseImage.setImageResource(R.drawable.curls);
                tvDescription.setText("Muscle: Biceps Brachii\n" +
                        "Step 1: a in euh hand with\n" +
                        "you' palms weights facing outward.\n" +
                        "arms should form a 90 degree angle.\n" +
                        "Step 2: Keeping p3ur elbow Close to body\n" +
                        "and in a steady position. curl the weight up\n" +
                        "towards your shoulder Lower weights slowly\n" +
                        "back to Starting");
                break;
            case "Reverse Curls" :
                exerciseImage.setImageResource(R.drawable.reverse_curls);
                tvDescription.setText("Muscle: Biceps grachi•\n" +
                        "Step 1: a Barbell hand with your feet\n" +
                        "about shewlder width apart Hang both arms\n" +
                        "donn •n front of your body. lully extended with\n" +
                        "Nms facing tcyward your\n" +
                        "Step 2: Raise the dumbbells up to the height\n" +
                        "of your shoulder while keeping\n" +
                        "flied in gagitiOn Sid&\n" +
                        "Step 3; Retum to starting position by lowering\n" +
                        "the back to the Original\n" +
                        "position.");
                break;
            case "Hammer Curls" :
                exerciseImage.setImageResource(R.drawable.hammer_curls);
                tvDescription.setText("Muscle: Biceps grachii\n" +
                        "Step 1: a in euh hand with\n" +
                        "you' palms facing Outward.\n" +
                        "arms should form a 90 degree angle.\n" +
                        "Step 2: Keeping pur Close to body\n" +
                        "and in a steady position, curt the weight up\n" +
                        "shm.lder Lower weights slowly\n" +
                        "back to Starting\n");
                break;
            case "Push-Ups" :
                exerciseImage.setImageResource(R.drawable.push_ups);
                tvDescription.setText("Muscle: Pectoralis\n" +
                        "Step 1. Lie prone with your arms straight,\n" +
                        "your palms flat on the floor, and your hands\n" +
                        "shoulder-width apart (or wider).\n" +
                        "Step 2. Hold your feet together or very slightly\n" +
                        "spread: Inhale and bend your elbows to bring\n" +
                        "your torso near the floor, avoiding extreme\n" +
                        "hyperextension of your spine Push yourself\n" +
                        "back to arms, extended position, exhaling as\n" +
                        "you complete the movement.\n");
                break;
            case "Bench Presses" :
                exerciseImage.setImageResource(R.drawable.bench_presses);
                tvDescription.setText("Muscle: Pectoralis\n" +
                        "Step 1: Lay flat on a bench and have your feet\n" +
                        "fiat on the floor. The bar, when racked, will\n" +
                        "be slightly behind your head. It is important\n" +
                        "to use a spotter for this exercise, especially\n" +
                        "if you are using a free bar or dumbbells, to\n" +
                        "eliminate the chance of dropping the weight\n" +
                        "on yourself.\n" +
                        "Step 2: Once you have lifted the weight off\n" +
                        "of the rack, the bar will be directly over the\n" +
                        "center of your chest. Keep your head on the\n" +
                        "bench at all times throughout the movement.");
                break;
            case "Dumbbell Flyes" :
                exerciseImage.setImageResource(R.drawable.dumbbell_flyes);
                tvDescription.setText("Muscle: Pectoralis\n" +
                        "Step 1: To perform dumbbell flies pick, up a\n" +
                        "relatively light weight in each hand. You will\n" +
                        "need to experiment to determine how much\n" +
                        "weight is your flies. with a lighter weight\n" +
                        "then slowly work your way up. Lie down on\n" +
                        "a weight bench and raise both dumbbells\n" +
                        "straight up over your chest with your arms\n" +
                        "straight.\n" +
                        "Step 2: This is your starting position. Spread\n" +
                        "both arms outward in a wide arc until your\n" +
                        "arms are out at your sides. Your arms can\n" +
                        "be slightly bent. This is the fly part of the\n" +
                        "dumbbell flies. It is called that because you");
                break;
            case "Dumbbell Presses" :
                exerciseImage.setImageResource(R.drawable.dumbbell_presses);
                tvDescription.setText("Muscle: Pectoralis\n" +
                        "Step 1: Lie flat on the bench with feet on the\n" +
                        "floor for stability, your arms extended upward,\n" +
                        "and your hands facing in toward each other\n" +
                        "holding the dumbbells: Inhale and lower the\n" +
                        "dumbbells to chest level, bending your elbows\n" +
                        "and rotating your forearms to bring your hand\n" +
                        "in pronation Press the dumbbells back up\n" +
                        "and do an isometric contraction to isolate\n" +
                        "the stress on the pectorals; exhale as you\n" +
                        "complete the movement.");
                break;
        }



        etExWeight1 = findViewById(R.id.etExWeight1);
        etRepetitions1 = findViewById(R.id.etRepetition1);
        etExWeight2 = findViewById(R.id.etExWeight2);
        etRepetitions2 = findViewById(R.id.etRepetition2);
        etExWeight3 = findViewById(R.id.etExWeight3);
        etRepetitions3 = findViewById(R.id.etRepetition3);


        btnAddExercise.setOnClickListener(new View.OnClickListener() {
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

                    currUserRef
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

                ProgramData.exercisesCount += 1;
                if(ProgramData.exercisesCount == ProgramData.exercisesToAchievement) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("exercises_count", ProgramData.exercisesCount);

                    currUserRef
                            .collection("Achievements").document("Workouts")
                            .set(map);
                    ProgramData.exercisesToAchievement *= 5;
                }

                etExWeight1.setText("");
                etExWeight2.setText("");
                etExWeight3.setText("");
                etRepetitions1.setText("");
                etRepetitions2.setText("");
                etRepetitions3.setText("");
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
