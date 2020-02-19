package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    public FirebaseAuth mAuth;
    private String TAG = "asdf";

    private Button btnSignIn;
    private EditText etEmail;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();



        btnSignIn = findViewById(R.id.btnContinue);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etEmail = findViewById(R.id.etEmailSignIn);
                final String email = etEmail.getText().toString();

                etPassword = findViewById(R.id.etPasswordSignIn);
                final String password = etPassword.getText().toString();

                signInUser(email, password);
            }
        });
    }

    public void updateUI(FirebaseUser user) {
        if(user != null) {
            Toast.makeText(this, "You are logged in.",  Toast.LENGTH_SHORT).show();
            Intent HomePage = new Intent(this, HomePageActivity.class);
            startActivity(HomePage);
            finish();
        }else {
            Toast.makeText(this, "You entered an invalid email or password. Please try again.",  Toast.LENGTH_LONG).show();
        }
    }

    public void signInUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

}
