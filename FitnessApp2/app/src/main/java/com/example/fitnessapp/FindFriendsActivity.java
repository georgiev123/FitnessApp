package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class FindFriendsActivity extends AppCompatActivity {

    private FirebaseAuth mauth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private TextView tvCurrFriend;
    private EditText edUsernameS;
    private Button btnAddFr, btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);

        mauth = FirebaseAuth.getInstance();

        tvCurrFriend = findViewById(R.id.tvCurrFriend);
        edUsernameS = findViewById(R.id.etusernameFr);
        btnAddFr = findViewById(R.id.btnAddFr);
        btnBack = findViewById(R.id.btnBackFindFr);

        btnAddFr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doesUsernameExists(mauth.getCurrentUser().toString());
            }
        });
    }

    private void doesUsernameExists(final String usernameSearch) {
        db.collection("Users").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Map<String, Object> map = new HashMap<>();
                                map.putAll(document.getData());

                                String currUsername = map.get("username").toString();
                                if(currUsername.equals(usernameSearch)) {
                                    Map<String, Object> mp = new HashMap<>();
                                    mp.put(usernameSearch, "pend_fr_request");
                                    db.collection("Users").document(document.getId())
                                            .collection("Friend_requests")
                                            .add(mp);
                                }else {
                                    Toast.makeText(FindFriendsActivity.this, "User not found.", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    }
                });
    }
}
