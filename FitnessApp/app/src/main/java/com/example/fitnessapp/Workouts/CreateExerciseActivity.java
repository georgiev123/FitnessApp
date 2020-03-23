package com.example.fitnessapp.Workouts;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.ProgramData;
import com.example.fitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateExerciseActivity extends AppCompatActivity {

    private FirebaseAuth mauth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Button btnCreate;
    private EditText etExName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_exercise);



        etExName = findViewById(R.id.etNewExName);
        final String name = etExName.getText().toString();

        btnCreate = findViewById(R.id.btnCreateEx);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> map = new HashMap<>();

                db.collection("Users")
                .document(mauth.getCurrentUser().getUid())
                        .collection("Workouts").document(ProgramData.whichActivity)
                        .collection(name).add(map);

                finish();
            }
        });
    }
}