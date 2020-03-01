package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.Result;

import java.util.HashMap;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private ZXingScannerView scannerView;
    int MY_PERMISSIONS_REQUEST_CAMERA=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
    }

    @Override
    protected void onPause() {
        super.onPause();

        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        }

        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    public void handleResult(final Result result) {
        FoodActivity.resutlTv.setText((result.getText()));
        db.collection("FoodDB").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> map = new HashMap<>();
                                map.putAll(document.getData());

                                if(result.getText().equals(map.get("barcode").toString())) {
                                    startActivity(new Intent(ScanCodeActivity.this, CustomFoodActivity.class));
                                    ProgramData.foodChoosed = document.getId();
                                }
                            }

                        }else {
                            Log.d("asdf", "Get failed with.", task.getException());
                        }

                    }
                });

        ProgramData.addMeal = true;

        onBackPressed();
    }

}
