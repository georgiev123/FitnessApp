package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExerciseHistoryActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mauth = FirebaseAuth.getInstance();
    private String TAG = "HistoryEx";

    private TextView tvWeightEx;
    private TextView tvRepetitionEx;
    private Button btnBackHome;

    private ArrayList<String> mWeights = new ArrayList<>();
    private ArrayList<String> mRepetitions = new ArrayList<>();
    private ArrayList<String> mDates = new ArrayList<>();
    private ArrayList<String> mExercises = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_history);


        db.collection("Users").document(mauth.getCurrentUser().getUid())
                .collection("Workouts").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> map = new HashMap<>();
                                map.putAll(document.getData());

                                mWeights.add(map.get("weight1").toString());
                                mRepetitions.add(map.get("repetition1").toString());
                                if(map.get("weight2") == null) {
                                    mWeights.add("0");
                                    mRepetitions.add("0");
                                }else {
                                    mWeights.add(map.get("weight2").toString());
                                    mRepetitions.add(map.get("repetition2").toString());
                                }

                                if(map.get("weight3") == null) {
                                    mWeights.add("0");
                                    mRepetitions.add("0");
                                }else {
                                    mWeights.add(map.get("weight3").toString());
                                    mRepetitions.add(map.get("repetition3").toString());
                                }

                                mDates.add(document.getId());
                                mExercises.add(map.get("exercise_name").toString());
                            }

                            RecyclerView recyclerView = findViewById(R.id.recycle_view);
                            RecyclerViewAdapter adapter = new RecyclerViewAdapter(ExerciseHistoryActivity.this, mWeights, mRepetitions, mExercises, mDates);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(ExerciseHistoryActivity.this));
                        }else {
                            Log.d(TAG, "Get failed with.", task.getException());
                        }

                    }
                });

//        Toast.makeText(ExerciseHistoryActivity.this, test, Toast.LENGTH_SHORT).show();

        btnBackHome = findViewById(R.id.btnBackH);
        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
