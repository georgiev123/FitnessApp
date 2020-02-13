package com.example.myapplicationtutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button btnSignUp;
    public static final String TAG = "YOUR-TAG-NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmOnClick(view);
            }
        });
    }

    public void confirmOnClick(View view) {
        EditText etEmail = findViewById(R.id.etEmail);
        String email = etEmail.getText().toString();
        EditText etPassword = findViewById(R.id.etPassword);
        String password = etPassword.getText().toString();
        EditText etConfPass = findViewById(R.id.etConfirmPassword);
        String confPass = etConfPass.getText().toString();

        if(confPass.equals(password)) {
            createAccount(email, password);
            Intent personalInfo = new Intent(this, PersonalInformation.class);
            startActivity(personalInfo);
        }else {
            Toast.makeText(this, "The passwords does not match.", Toast.LENGTH_SHORT).show();
        }

    }

    public void createAccount (String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                            Toast.makeText(this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    public void  updateUI(FirebaseUser account){
        if(account != null){
            Toast.makeText(this,"U Signed up successfully", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,MainActivity.class));
        }else {
            Toast.makeText(this,"U couldn't sign up",Toast.LENGTH_LONG).show();
        }
    }
}
