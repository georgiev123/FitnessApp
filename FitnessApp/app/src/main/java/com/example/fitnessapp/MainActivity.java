package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mauth;

    private Button btnSignIn;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mauth = FirebaseAuth.getInstance();

        btnSignUp = findViewById(R.id.btnSignUpMain);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }
        });

        btnSignIn = findViewById(R.id.btnSignInMain);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignInActivity.class));
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currUser = mauth.getCurrentUser();
        if(currUser != null) {
            startActivity(new Intent(this, HomePageActivity.class));
            Toast.makeText(this, currUser.getEmail(), Toast.LENGTH_LONG).show();
        }
    }


}
