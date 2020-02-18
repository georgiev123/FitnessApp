package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
import java.util.HashMap;
import java.util.Map;

public class ExerciseHistoryActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mauth = FirebaseAuth.getInstance();

    private TextView tvWeightEx;
    private TextView tvRepetitionEx;
    private Button btnBackHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_history);

        tvWeightEx = findViewById(R.id.tvWeightH);
        tvRepetitionEx = findViewById(R.id.tvRepetitionsH);


//        CollectionReference datesRef = db.collection("Users").document(mauth.getCurrentUser().getUid())
//                .collection("Workouts").document("Abs")
//                .collection("SideBridge");
//
//        Query query = datesRef.whereEqualTo("weight", "");

        db.collection("Users").document(mauth.getCurrentUser().getUid())
                .collection("Workouts").document("Abs")
                .collection("SideBridge").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<Object, Object> map = new HashMap<>();
                                map.putAll(document.getData());
                                Double rep = (Double)map.get("repetition");
                                Double weight = (Double) map.get("weight");
                                tvWeightEx.setText(weight.toString());
                                tvRepetitionEx.setText(rep.toString());
                            }

                        }else {
                            Log.d("asdf", "Get failed with.", task.getException());
                        }
                    }
                });

        btnBackHome = findViewById(R.id.btnBackH);
        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
