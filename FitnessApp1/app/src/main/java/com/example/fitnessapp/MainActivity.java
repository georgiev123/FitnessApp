package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase mDB = FirebaseDatabase.getInstance();
    private DatabaseReference userRef;
    private FirebaseAuth mauth;

    private Button btnSignIn;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mauth = FirebaseAuth.getInstance();

        userRef = mDB.getReference("Users");

        btnSignUp = findViewById(R.id.btnSignUpMain);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpActivity = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(signUpActivity);
            }
        });

        btnSignIn = findViewById(R.id.btnSignInMain);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInActivity = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(signInActivity);
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
