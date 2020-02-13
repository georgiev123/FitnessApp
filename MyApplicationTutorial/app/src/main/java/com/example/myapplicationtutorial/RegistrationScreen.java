package com.example.myapplicationtutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationScreen extends AppCompatActivity {

    private FirebaseAuth mAuth;
    public static final String TAG = "YOUR-TAG-NAME";
    private boolean successfulSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_screen);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    public void confirmOnClick(View view) {
        EditText etEmail = findViewById(R.id.etEmailSignIn);
        String email = etEmail.getText().toString();
        EditText etPassword = findViewById(R.id.etPasswordSignIn);
        String password = etPassword.getText().toString();

        if(email.equals("") || password.equals("")) {
            Toast.makeText(this,"You have entered invalid password or email. Please try again.", Toast.LENGTH_LONG).show();
        }
        else {

            signIn(email, password);

//        if(successfulSignIn) {
            Intent personalInfo = new Intent(this, PersonalInformation.class);
            startActivity(personalInfo);
//            this.finish();
//        }
        }

    }

    public void sendToSingUp(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    public void updateUI(FirebaseUser account){
        if(account != null){
            Toast.makeText(this,"U Signed In successfully", Toast.LENGTH_LONG).show();
//            startActivity(new Intent(this,MainActivity.class));
            successfulSignIn = true;
        }else {
            Toast.makeText(this,"U Didnt signed in",Toast.LENGTH_LONG).show();
            successfulSignIn = false;
        }
    }

    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(RegistrationScreen.this,"Ne stana",Toast.LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
//                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

}
