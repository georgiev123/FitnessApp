package com.example.fitnessapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CaloriesDiaryActivity extends AppCompatActivity {
    private FirebaseAuth mauth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String TAG = "Calories Diary";
    private Button btnMeal1;
    private Button btnMeal2;
    private Button btnMeal3;
    private TextView tvCaloriesGoal;
    private TextView tvMacros;
    private HomePageActivity hp;
    public Double height;

    public ArrayList<String> arrName1 = new ArrayList<>();
    public ArrayList<String> arrGrams1 = new ArrayList<>();
    public ArrayList<String> arrCalories1 = new ArrayList<>();
    public ArrayList<String> arrName2 = new ArrayList<>();
    public ArrayList<String> arrGrams2 = new ArrayList<>();
    public ArrayList<String> arrCalories2 = new ArrayList<>();
    public ArrayList<String> arrName3 = new ArrayList<>();
    public ArrayList<String> arrGrams3 = new ArrayList<>();
    public ArrayList<String> arrCalories3 = new ArrayList<>();

    private RecyclerView rvMeal1;
    private RecyclerView rvMeal2;
    private RecyclerView rvMeal3;
    private RecyclerView.Adapter mAdapter1;
    private RecyclerView.Adapter mAdapter2;
    private RecyclerView.Adapter mAdapter3;
    private RecyclerView.LayoutManager layoutManager1;
    private RecyclerView.LayoutManager layoutManager2;
    private RecyclerView.LayoutManager layoutManager3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories_diary);

        mauth = FirebaseAuth.getInstance();
        hp = new HomePageActivity();

        rvMeal1 = findViewById(R.id.rvMeal1);
        rvMeal2 = findViewById(R.id.rvMeal2);
        rvMeal3 = findViewById(R.id.rvMeal3);
        rvMeal1.setHasFixedSize(true);
        rvMeal2.setHasFixedSize(true);
        rvMeal3.setHasFixedSize(true);

        layoutManager1 = new LinearLayoutManager(this);
        layoutManager2 = new LinearLayoutManager(this);
        layoutManager3 = new LinearLayoutManager(this);

        rvMeal1.setLayoutManager(layoutManager1);
        rvMeal2.setLayoutManager(layoutManager2);
        rvMeal3.setLayoutManager(layoutManager3);

        tvCaloriesGoal = findViewById(R.id.tvCaloriesGoal);
        tvMacros = findViewById(R.id.tvMacros);

        Toolbar mToolbar = findViewById(R.id.toolbarDiary);
        mToolbar.setTitle(TAG);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setNavigationIcon(R.drawable.ic_back_arrow);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnMeal1 = findViewById(R.id.btnAddFoodMeal1);
        btnMeal1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgramData.doRestart = false;
                ProgramData.whichMeal = "Meal1";
                ProgramData.addMeal = true;
                startActivity(new Intent(CaloriesDiaryActivity.this, FoodActivity.class));
            }
        });

        btnMeal2 = findViewById(R.id.btnAddFoodMeal2);
        btnMeal2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgramData.doRestart = false;
                ProgramData.whichMeal = "Meal2";
                ProgramData.addMeal = true;
                startActivity(new Intent(CaloriesDiaryActivity.this, FoodActivity.class));
            }
        });

        btnMeal3 = findViewById(R.id.btnAddFoodMeal3);
        btnMeal3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgramData.doRestart = false;
                ProgramData.whichMeal = "Meal3";
                ProgramData.addMeal = true;
                startActivity(new Intent(CaloriesDiaryActivity.this, FoodActivity.class));
            }
        });

        hp.calculateCalories("CaloriesDiary", tvCaloriesGoal, mauth, tvMacros);
        setListViews();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRestart() {
        super.onRestart();
        if(ProgramData.doRestart) {
            arrName1.clear();
            arrName2.clear();
            arrName3.clear();
            arrCalories1.clear();
            arrCalories2.clear();
            arrCalories3.clear();
            arrGrams1.clear();
            arrGrams2.clear();
            arrGrams3.clear();
            setListViews();
            hp.calculateCalories("CaloriesDiary", tvCaloriesGoal, mauth, tvMacros);
        }
    }

    private void setListViews() {
        db.collection("Users").document(mauth.getCurrentUser().getUid())
                .collection("Meals").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> map = new HashMap<>();
                                map.putAll(document.getData());

                                Integer gramsInt = (int)Double.parseDouble(map.get("grams").toString());
                                Integer caloriesInt = (int)Double.parseDouble(map.get("calories").toString());
                                if(map.get("meal_number").toString().equals("Meal1")) {
                                    arrName1.add(map.get("food_name").toString());
                                    arrGrams1.add(gramsInt.toString());
                                    arrCalories1.add(caloriesInt.toString());
                                }else if(map.get("meal_number").toString().equals("Meal2")) {
                                    arrName2.add(map.get("food_name").toString());
                                    arrGrams2.add(gramsInt.toString());
                                    arrCalories2.add(caloriesInt.toString());
                                }else {
                                    arrName3.add(map.get("food_name").toString());
                                    arrGrams3.add(gramsInt.toString());
                                    arrCalories3.add(caloriesInt.toString());
                                }
                            }

                            ProgramData.addMeal = false;

                            mAdapter1 = new RecyclerViewFood(arrName1, arrGrams1, arrCalories1);
                            rvMeal1.setAdapter(mAdapter1);
                            mAdapter2 = new RecyclerViewFood(arrName2, arrGrams2, arrCalories2);
                            rvMeal2.setAdapter(mAdapter2);
                            mAdapter3 = new RecyclerViewFood(arrName3, arrGrams3, arrCalories3);
                            rvMeal3.setAdapter(mAdapter3);


                        }else {
                            Log.d(TAG, "Get failed with.", task.getException());
                        }

                    }
                });
    }

}



