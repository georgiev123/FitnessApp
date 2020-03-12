package com.example.fitnessapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.JsonObject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FoodActivity extends AppCompatActivity {

    public static Activity foodActivity;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mauth;
    private String TAG = "FoodActivity";

    private Button btnBarcodeScan;
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mGrams = new ArrayList<>();
    private ArrayList<String> mCalories = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        foodActivity = this;

        mauth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.recycle_view_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Toolbar mToolbar = findViewById(R.id.toolbarFoodActivity);
        mToolbar.setTitle(TAG);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setNavigationIcon(R.drawable.ic_back_arrow);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgramData.addMeal = false;
                finish();
            }
        });

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
                                Integer gramsToInt = (int)Double.parseDouble(map.get("grams").toString());
                                mGrams.add(gramsToInt.toString());

                                Double gramsPerCalories = (Double.parseDouble(map.get("grams").toString())/100);
                                Integer gramsPerCaloriesInt = (int)(Math.floor(Double.parseDouble(map.get("calories").toString()) * gramsPerCalories));
                                mCalories.add(gramsPerCaloriesInt.toString());

                            }
                            mAdapter = new RecyclerViewFood(mNames, mGrams, mCalories);
                            recyclerView.setAdapter(mAdapter);
                        }else {
                            Log.d(TAG, "Get failed with.", task.getException());
                        }

                    }
                });

    }

    @Override
    public void onBackPressed() {
        ProgramData.addMeal = false;
        finish();
    }

}