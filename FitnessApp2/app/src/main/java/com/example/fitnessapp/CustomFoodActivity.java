package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CustomFoodActivity extends AppCompatActivity {

    private FirebaseAuth mauth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String TAG = "Food Information";

    public TextView tvBarcode;
    public TextView tvName;
    public TextView tvCalories;
    public TextView tvCarbs;
    public TextView tvProteins;
    public TextView tvFats;
    public TextView tvGrams;
    private TextView tvCaloriesPerGrams;
    public Button btnAddMeal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_food);

        tvBarcode = findViewById(R.id.tvInfoBarcode);
        tvName = findViewById(R.id.tvInfoName);
        tvCalories = findViewById(R.id.tvInfoCalories);
        tvCarbs = findViewById(R.id.tvInfoCarbs);
        tvProteins = findViewById(R.id.tvInfoProteins);
        tvFats = findViewById(R.id.tvInfoFats);
        tvGrams = findViewById(R.id.tvInfoGrams);
        btnAddMeal = findViewById(R.id.btnAddFood);
        tvCaloriesPerGrams = findViewById(R.id.tvCaloriesPerGram);

        Toolbar mToolbar = findViewById(R.id.toolbarCustomFood);
        mToolbar.setTitle(TAG);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setNavigationIcon(R.drawable.ic_back_arrow);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if(ProgramData.addMeal) {
            btnAddMeal.setText("Add Food To Your Meal");
        }else {
            btnAddMeal.setText("Back");
        }


        db.collection("FoodDB").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> map = new HashMap<>();
                                map.putAll(document.getData());

                                if(ProgramData.foodChoosed.equals(document.getId())) {
                                    tvBarcode.setText("Barcode : " + map.get("barcode").toString());
                                    tvName.setText(document.getId());
                                    tvGrams.setText("Grams : " + map.get("grams").toString());

                                    Double currGrams = (Double.parseDouble(map.get("grams").toString())/100);
                                    tvCalories.setText("Calories : " + (int)Math.floor(Double.parseDouble(map.get("calories").toString()) * currGrams));
                                    tvCarbs.setText("Carbs : " + (int)Math.floor(Double.parseDouble(map.get("carbs").toString()) * currGrams));
                                    tvProteins.setText("Proteins : " + (int)Math.floor(Double.parseDouble(map.get("proteins").toString()) * currGrams));
                                    tvFats.setText("Fats : " + (int)Math.floor(Double.parseDouble(map.get("fats").toString()) * currGrams));
                                    tvCaloriesPerGrams.setText("Calories for 100g : " + Double.parseDouble(map.get("calories").toString()));

                                }
                            }

                        }else {
                            Log.d(TAG, "Get failed with.", task.getException());
                        }

                    }
                });

        btnAddMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ProgramData.addMeal) {
                    Double pCalories = Double.parseDouble(tvCalories.getText().toString().split("\\s+")[2]);
                    Double pCarbs = Double.parseDouble(tvCarbs.getText().toString().split("\\s+")[2]);
                    Double pProteinsIntake = Double.parseDouble(tvProteins.getText().toString().split("\\s+")[2]);
                    Double pFatsIntake = Double.parseDouble(tvFats.getText().toString().split("\\s+")[2]);
                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-YYYY hh:mm:ss");
                    String formattedDate = df.format(c);

                    Map<String, Object> map = new HashMap<>();

                    map.put("food_name", tvName.getText().toString());
                    map.put("calories", pCalories);
                    map.put("meal_number", ProgramData.whichMeal);
                    map.put("carbs", pCarbs);
                    map.put("proteins", pProteinsIntake);
                    map.put("fats", pFatsIntake);
                    map.put("grams", tvGrams.getText().toString().split("\\s+")[2]);
                    map.put("barcode", tvBarcode.getText().toString().split("\\s+")[2]);

                    db.collection("Users").document(mauth.getCurrentUser().getUid())
                            .collection("Meals").document(formattedDate).set(map);
                    ProgramData.addMeal = false;
                    ProgramData.doRestart = true;
                }

                finish();
            }
        });

    }
}
