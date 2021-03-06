package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FindFriendsActivity extends AppCompatActivity {

    private FirebaseAuth mauth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String TAG = "Find Followees";

    private ListView listView;
    private EditText edUsernameS;
    private Button btnAddFr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);

        listView = findViewById(R.id.lvFollowers);


        mauth = FirebaseAuth.getInstance();

        edUsernameS = findViewById(R.id.etusernameFr);
        btnAddFr = findViewById(R.id.btnAddFr);

        btnAddFr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFollower();
            }
        });

        Toolbar mToolbar = findViewById(R.id.toolbarFindFollowees);
        mToolbar.setTitle(TAG);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setNavigationIcon(R.drawable.ic_back_arrow);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgramData.userProfile = mauth.getCurrentUser().getUid();
                finish();
            }
        });

        setListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String followUserProfile = listView.getItemAtPosition(position).toString();

                db.collection("Users").get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {

                                        Map<String, Object> map = new HashMap<>();
                                        map.putAll(document.getData());

                                        String currDocUsername = map.get("username").toString();
                                        if(currDocUsername.equals(followUserProfile)) {
                                            ProgramData.userProfile = document.getId();
                                            startActivity(new Intent(FindFriendsActivity.this, CustomProfileActivity.class));
                                            break;
                                        }
                                    }

                                }
                            }
                        });
            }
        });
    }

    private void setListView() {
        db.collection("Users").document(mauth.getCurrentUser().getUid())
                .collection("Followings").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                       @Override
                       public void onComplete(@NonNull Task<QuerySnapshot> task) {
                           if(task.isSuccessful()) {
                               ArrayList<String> followers = new ArrayList<>();
                               for (QueryDocumentSnapshot document : task.getResult()) {
                                    followers.add(document.getId());
                               }

                               String arrFollowers[] = new String[followers.size()];
                               for (int i = 0; i < followers.size(); i++) {
                                   arrFollowers[i] = followers.get(i);
                               }
                               ArrayAdapter < String > arrayAdapter = new ArrayAdapter(FindFriendsActivity.this, R.layout.lv_followers, R.id.tvFollowerList, arrFollowers);
                               listView.setAdapter(arrayAdapter);
                           }
                       }
                   });


    }

    private void createFollower() {
        db.collection("Users").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Map<String, Object> map = new HashMap<>();
                                map.putAll(document.getData());

                                String currDocUsername = map.get("username").toString();
                                if(currDocUsername.equals(edUsernameS.getText().toString()) &&
                                        !mauth.getCurrentUser().getUid().equals(document.getId())) {
                                    Map<String, Object> mp = new HashMap<>();
                                    mp.put("age", map.get("age"));
                                    mp.put("gender", map.get("gender").toString());
                                    mp.put("user_uid", document.getId());
                                    db.collection("Users").document(mauth.getCurrentUser().getUid())
                                            .collection("Followings").document(edUsernameS.getText().toString())
                                            .set(mp);
                                    edUsernameS.setText("");
                                    setListView();
                                    break;
                                }
                            }

                        }
                    }
                });
    }
}
