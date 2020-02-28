package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FoodActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mauth;

    private String TAG = "FoodActivity";

    private Button btnBarcodeScan;
    public static TextView resutlTv;


    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mGrams = new ArrayList<>();
    private ArrayList<String> mCalories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        mauth = FirebaseAuth.getInstance();
        resutlTv = findViewById(R.id.result_text);

        btnBarcodeScan = findViewById(R.id.btnBarcodeScan);
        btnBarcodeScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ScanCodeActivity.class));
            }
        });

        db.collection("FoodDB").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> map = new HashMap<>();
                                map.putAll(document.getData());

                                mNames.add(document.getId());
                                mGrams.add(map.get("grams").toString());
                                mCalories.add(map.get("calories").toString());
                            }

                            RecyclerView recyclerView = findViewById(R.id.recycle_view);
                            RecyclerViewAdapter adapter = new RecyclerViewAdapter(FoodActivity.this, mNames, mCalories, mGrams, null);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(FoodActivity.this));
                        }else {
                            Log.d(TAG, "Get failed with.", task.getException());
                        }

                    }
                });

    }







}
