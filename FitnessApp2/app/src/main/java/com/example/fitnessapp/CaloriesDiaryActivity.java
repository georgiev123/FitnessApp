package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.fitnessapp.ProgramData.calories;
import static com.example.fitnessapp.ProgramData.caloriesIntake;

public class CaloriesDiaryActivity extends AppCompatActivity {
    private FirebaseAuth mauth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Button btnMeal1;
    private Button btnMeal2;
    private Button btnMeal3;
    private TextView tvCaloriesGoal;
    private TextView tvMacros;
    private HomePageActivity hp;

    public Double height;
    public ArrayList<String> arrMeal1 = new ArrayList<>();
    public ArrayList<String> arrMeal2 = new ArrayList<>();
    public ArrayList<String> arrMeal3 = new ArrayList<>();

    private ListView meal1;
    private ListView meal2;
    private ListView meal3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories_diary);

        mauth = FirebaseAuth.getInstance();
        hp = new HomePageActivity();

        meal1 = findViewById(R.id.lvMeal1);
        meal2 = findViewById(R.id.lvMeal2);
        meal3 = findViewById(R.id.lvMeal3);
        tvCaloriesGoal = findViewById(R.id.tvCaloriesGoal);
        tvMacros = findViewById(R.id.tvMacros);

        btnMeal1 = findViewById(R.id.btnAddFoodMeal1);
        btnMeal1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgramData.whichMeal = "Meal1";
                startActivity(new Intent(CaloriesDiaryActivity.this, FoodActivity.class));
            }
        });

        btnMeal2 = findViewById(R.id.btnAddFoodMeal2);
        btnMeal2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgramData.whichMeal = "Meal2";
                startActivity(new Intent(CaloriesDiaryActivity.this, FoodActivity.class));
            }
        });

        btnMeal3 = findViewById(R.id.btnAddFoodMeal3);
        btnMeal3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgramData.whichMeal = "Meal3";
                startActivity(new Intent(CaloriesDiaryActivity.this, FoodActivity.class));
            }
        });


        meal1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String[] clickedFood = meal1.getItemAtPosition(position).toString().split("\\s+");

                onClickInfo(clickedFood[0]);
            }
        });

//        db.collection("Users").document(mauth.getCurrentUser().getUid())
////                .collection("Meals").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
////            @Override
////            public void onComplete(@NonNull Task<QuerySnapshot> task) {
////                if(task.isSuccessful()) {
////                    for (QueryDocumentSnapshot document : task.getResult()) {
////                        Map<String, Object> map = new HashMap<>();
////                        map.putAll(document.getData());
////
////                        tvCaloriesGoal.setText(calories + "   -  " + (ProgramData.caloriesIntake.intValue()
////                                + Double.parseDouble(map.get("calories").toString()))
////                                + "   =   " + (calories - (ProgramData.caloriesIntake.intValue()
////                                + Double.parseDouble(map.get("calories").toString()))));
////                        Double carbs = calories
////                        tvMacros.setText("carbs :  " + (carbs.intValue() - ProgramData.carbsIntake.intValue()) +
////                                "   proteins :  " + (protein.intValue() - ProgramData.proteinsIntake.intValue())
////                                + "   fats :  " + (fats.intValue() - ProgramData.fatsIntake.intValue()));
////                        ProgramData.carbsIntake += Double.parseDouble(map.get("carbs").toString());
////                        ProgramData.proteinsIntake += Double.parseDouble(map.get("proteins").toString());
////                        ProgramData.fatsIntake += Double.parseDouble(map.get("fats").toString());
////                    }
////
////                }else {
////                    Log.d("asdf", "Get failed with.", task.getException());
////                }
////
////            }
////        });

        hp.calculateCalories("CaloriesDiary", tvCaloriesGoal, mauth, tvMacros);
        setListViews();
    }


    @Override
    public void onRestart() {
        super.onRestart();
        setListViews();
        hp.calculateCalories("CaloriesDiary", tvCaloriesGoal, mauth, tvMacros);
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

                                if(map.get("meal_number").toString().equals("Meal1")) {
                                    arrMeal1.add(map.get("food_name").toString() + "                                         calories : " + map.get("calories").toString());
                                }else if(map.get("meal_number").toString().equals("Meal2")) {
                                    arrMeal2.add(map.get("food_name").toString() + "                                         calories : " + map.get("calories").toString());
                                }else {
                                    arrMeal3.add(map.get("food_name").toString() + "                                         calories : " + map.get("calories").toString());
                                }
                            }

                            String arrStrMeal1[] = new String[arrMeal1.size()];
                            for (int i = 0; i < arrMeal1.size(); i++) {
                                arrStrMeal1[i] = arrMeal1.get(i);
                            }

                            String arrStrMeal2[] = new String[arrMeal2.size()];
                            for (int i = 0; i < arrMeal2.size(); i++) {
                                arrStrMeal2[i] = arrMeal2.get(i);
                            }

                            String arrStrMeal3[] = new String[arrMeal3.size()];
                            for (int i = 0; i < arrMeal3.size(); i++) {
                                arrStrMeal3[i] = arrMeal3.get(i);
                            }

                            ArrayAdapter< String > arrayAdapter1 = new ArrayAdapter(CaloriesDiaryActivity.this, R.layout.lv_followers, R.id.tvFollowerList ,arrStrMeal1);
                            meal1.setAdapter(arrayAdapter1);

                            ArrayAdapter< String > arrayAdapter2 = new ArrayAdapter(CaloriesDiaryActivity.this, R.layout.lv_followers, R.id.tvFollowerList, arrStrMeal2);
                            meal2.setAdapter(arrayAdapter2);

                            ArrayAdapter< String > arrayAdapter3 = new ArrayAdapter(CaloriesDiaryActivity.this, R.layout.lv_followers, R.id.tvFollowerList, arrStrMeal3);
                            meal3.setAdapter(arrayAdapter3);

                        }else {
                            Log.d("asdf", "Get failed with.", task.getException());
                        }

                    }
                });
    }

    private void onClickInfo(final String clickedFood) {
        db.collection("Users").document(mauth.getCurrentUser().getUid())
                .collection("Meals").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> map = new HashMap<>();
                                map.putAll(document.getData());

                                String currFood = map.get("food_name").toString();
                                if(currFood.equals(clickedFood)) {
                                    setBarcodeScanned(clickedFood);
                                    startActivity(new Intent(CaloriesDiaryActivity.this, CustomFoodActivity.class));
                                    break;
                                }
                            }

                        }
                    }
                });
    }

    private void setBarcodeScanned(final String clickedFood) {
        db.collection("FoodDB").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> map = new HashMap<>();
                                map.putAll(document.getData());

                                String currFood = document.getId();
                                if(currFood.equals(clickedFood)) {
                                    ProgramData.barcodeScanned = map.get("barcode").toString();
                                }
                            }

                        }else {
                            Log.d("asdf", "Get failed with.", task.getException());
                        }

                    }
                });
    }
}
