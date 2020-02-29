package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

    public TextView tvBarcode;
    public TextView tvName;
    public TextView tvCalories;
    public TextView tvCarbs;
    public TextView tvProteins;
    public TextView tvFats;
    public TextView tvGrams;
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


        db.collection("FoodDB").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> map = new HashMap<>();
                                map.putAll(document.getData());

                                if(ProgramData.barcodeScanned.equals(map.get("barcode").toString())) {
                                    tvBarcode.setText(map.get("barcode").toString());
                                    tvCalories.setText(map.get("calories").toString());
                                    tvCarbs.setText(map.get("carbs").toString());
                                    tvProteins.setText(map.get("proteins").toString());
                                    tvFats.setText(map.get("fats").toString());
                                    tvGrams.setText(map.get("grams").toString());
                                    tvName.setText(document.getId());
                                }
                            }

                        }else {
                            Log.d("asdf", "Get failed with.", task.getException());
                        }

                    }
                });

        btnAddMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ProgramData.openedByBarcodeScanner) {
                    ProgramData.caloriesIntake += Double.parseDouble(tvCalories.getText().toString());
                    ProgramData.carbsIntake += Double.parseDouble(tvCarbs.getText().toString());
                    ProgramData.proteinsIntake += Double.parseDouble(tvProteins.getText().toString());
                    ProgramData.fatsIntake += Double.parseDouble(tvFats.getText().toString());
                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-YYYY hh:mm:ss");
                    String formattedDate = df.format(c);

                    Map<String, Object> map = new HashMap<>();
                    map.put("food_name", tvName.getText());
                    map.put("calories", tvCalories.getText());
                    map.put("meal_number", ProgramData.whichMeal);
                    map.put("carbs", tvCarbs.getText().toString());
                    map.put("proteins", tvProteins.getText().toString());
                    map.put("fats", tvFats.getText().toString());

                    db.collection("Users").document(mauth.getCurrentUser().getUid())
                            .collection("Meals").document(formattedDate).set(map);
                    ProgramData.openedByBarcodeScanner = false;
                }

                finish();
            }
        });

    }
}
