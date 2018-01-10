package com.example.android.anonytter;

import android.app.AlertDialog;
import android.app.ProgressDialog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;


import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

import java.util.HashMap;


public class tweets extends AppCompatActivity {

    private FirebaseAuth authb;
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private ArrayList<Posts> tweet;
    private ProgressDialog progressDialog;
    private String email;
    private String username;
    SessionManager session;
    Posts posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweets);

        session = new SessionManager(getApplicationContext());
        progressDialog = new ProgressDialog(this);
        session.checkLogin();


        HashMap<String, String> user = session.getUserDetails();
        email = user.get(SessionManager.KEY_EMAIL);
        username = user.get(SessionManager.KEY_USERNAME);

        recyclerView = (RecyclerView) findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        authb = FirebaseAuth.getInstance();


        Button btn = (Button) findViewById(R.id.new_post);
        tweet = new ArrayList<>();
        readData();

        recyclerView.setHasFixedSize(true);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(tweets.this, addPost.class);
                startActivity(intent);
                finish();

            }
        });
    }

    private void readData() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference rd = databaseReference.child("Posts");

        rd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tweet.clear();

                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    posts = snap.getValue(Posts.class);
                    posts.author = "@"+ username;
                    tweet.add(posts);
                }
                adapter = new MyAdapter(tweet, tweets.this, R.layout.tweet_list);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public void account(View view) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(tweets.this);
        alertDialogBuilder.setTitle("My Account");
        alertDialogBuilder.setMessage("username:" + username + "\n" + email).setCancelable(false);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(true);

    }

    public void logout(View view) {
        progressDialog.setMessage("Logging out...");
        progressDialog.show();

        authb.signOut();
        session.logoutUser();
    }
}
