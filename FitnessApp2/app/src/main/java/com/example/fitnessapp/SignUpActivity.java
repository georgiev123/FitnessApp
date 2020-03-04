package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String TAG = "Sign Up";
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
                    signUpUser(email, username);
                }else {
                    Toast.makeText(SignUpActivity.this, "You have invalid information.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void updateUI(FirebaseUser user) {
        if(user != null) {
            db.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()) {
                        for(final QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> map = new HashMap<>();
                            map.putAll(document.getData());

                            if(map.get("username").toString().equals(username)) {
                                Toast.makeText(SignUpActivity.this, "This username already exists. Please try again.",  Toast.LENGTH_LONG).show();
                            }else {
                                Map<String, Object> currUser = new HashMap<>();
                                currUser.put("username", username);
                                db.collection("Users").document(mAuth.getCurrentUser().getUid()).set(currUser);
                                setUserInfo();
                                Toast.makeText(SignUpActivity.this, "You signed up.",  Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUpActivity.this, HomePageActivity.class));
                                finish();

                            }
                        }
                    }
                }
            });

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

    private void setUserInfo() {
        db.collection("Users").document(mAuth.getCurrentUser().getUid()).set(ProgramData.userInfoMap);
    }

}
