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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDB = FirebaseDatabase.getInstance();
    private DatabaseReference userRef;

    private String TAG = "asdf";
    private String username;

    private Button btnSignUp;
    private EditText etUsername;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();


        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etUsername = findViewById(R.id.etUsername);
                 username = etUsername.getText().toString();

                etEmail = findViewById(R.id.etEmailSignUp);
                final String email = etEmail.getText().toString();

                etPassword = findViewById(R.id.etPasswordSignUp);
                final String password = etPassword.getText().toString();

                etConfirmPassword = findViewById(R.id.etConfirmPassword);
                final String confPassword = etConfirmPassword.getText().toString();

                if(password.equals(confPassword)) {
                    signUpUser(email, password);
                }else {
                    Toast.makeText(SignUpActivity.this, "You have invalid information.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void updateUI(FirebaseUser user) {
        if(user != null) {
            Toast.makeText(this, "You signed up.",  Toast.LENGTH_SHORT).show();

            userRef = mDB.getReference("Users/user_" + user.getUid() + "/personal_information/username");
            userRef.setValue(username);

            Intent HomePage = new Intent(this, PersonalInformationActivity.class);
            startActivity(HomePage);
            finish();
        }else {
            Toast.makeText(this, "You entered an invalid email, password or username. Please try again.",  Toast.LENGTH_LONG).show();
        }
    }

    public void signUpUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                    }
                });
    }

}
