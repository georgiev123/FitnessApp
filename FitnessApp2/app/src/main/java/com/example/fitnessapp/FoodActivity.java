package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Response;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.lang.reflect.Executable;

public class FoodActivity extends AppCompatActivity {

    private Button btnBarcodeScan;
    public static TextView resutlTv;

    final TextView textView = (TextView) findViewById(R.id.text);
    RequestQueue queue = Volley.newRequestQueue(this);
    String url ="http://www.google.com";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        resutlTv = findViewById(R.id.result_text);

        btnBarcodeScan = findViewById(R.id.btnBarcodeScan);
        btnBarcodeScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ScanCodeActivity.class));
            }
        });

        StringRequest stringRequest = new StringRequest(StringRequest.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                JSONObject jobj = new JSONObject(response.toString());
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }







}
