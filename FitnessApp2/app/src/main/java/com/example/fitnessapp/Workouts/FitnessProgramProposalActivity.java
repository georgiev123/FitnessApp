package com.example.fitnessapp.Workouts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fitnessapp.ProgramData;
import com.example.fitnessapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FitnessProgramProposalActivity extends AppCompatActivity {

    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    public FirebaseAuth mauth;

    private TextView proposalEx;
    private ImageView ivFitnessEx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_program_proposal);

        mauth = FirebaseAuth.getInstance();

        proposalEx = findViewById(R.id.tvProposalExercisesText);
        ivFitnessEx = findViewById(R.id.ivFitnessProgram);

        db.collection("Users").document(ProgramData.userProfile)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot doc = task.getResult();
                Map<String, Object> map = new HashMap<>();
                map.putAll(doc.getData());


                if(map.get("training_goal").toString().equals("Lose Weight")) {
                    proposalEx.setText("You want to lose weight. So one of the most important things " +
                            "for successful weight loss is the balanced and healthy eating. But if you want" +
                            "to have instant results you should also make big amount of exercises a day." +
                            "\n\n For loosing weight is mostly recommended the types of cardio exercises and " +
                            "these which are focused on more intense moving." +
                            "\n\n For you training in Life Fitness out team recommends the exercises from the Abs and " +
                            "cardio sections. Mainly - Crunches, Sit-Ups, Ropes and other of those kind.");
                    ivFitnessEx.setImageResource(R.drawable.crunches);
                }else if(map.get("training_goal").toString().equals("Gain Weight")) {
                    proposalEx.setText("You want to gain weight. So one of the most important things " +
                            "for successful weight gain is the balanced and healthy eating. You cant gain " +
                            "successfuly weight if you are eating a lot of unhealthy food. This will only " +
                            "slow the wanted results. \n\nThe other thing about the weight gain is the corectly done " +
                            "exercises. You must not try to do with more kilograms that you can. Slow and steady " +
                            "wins the race. So for your program out team recommends the following exercises:" +
                            "\n\n Chin-Ups, Deadlift, Curls, Bench Presses and other exercises focusing the body strength.");
                    ivFitnessEx.setImageResource(R.drawable.bench_presses);
                }else {
                    proposalEx.setText("You want to maintain weight. So you just can follow our recommended calories" +
                            "and macros intake and do some light exercises which won't exhaust you very much." +
                            "\n\n Our team recommends you to do some exercises for you agility and flexibility. Some" +
                            "of them are : Dumbbell Lunges, Bench Jump, Single Leg Squad, Push-Ups, Chin-Ups and other. ");
                    ivFitnessEx.setImageResource(R.drawable.sit_ups);
                }

            }
        });

    }
}
