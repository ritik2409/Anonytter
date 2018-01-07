package com.example.android.anonytter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class tweets extends AppCompatActivity {

    private FirebaseAuth authb;
    private EditText et;
    private String message;
    private String id;
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private ArrayList<Posts> tweet;
    private ProgressDialog progressDialog;
    private String email;
    private String username;
    SessionManager session;
    String time;
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
        adapter = new MyAdapter(tweet);
        readData();

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ab = new AlertDialog.Builder(tweets.this);
                ab.setTitle("ENTER NEW POST");
                et = new EditText(tweets.this);
                ab.setView(et);
                ab.setPositiveButton("POST", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        message = et.getText().toString();
                        if (message.isEmpty()) {
                            Toast.makeText(tweets.this, "Plz fill something to add post", Toast.LENGTH_LONG).show();
                        } else {
                            writeNewPost(message);
                        }
                    }
                });
                ab.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog a = ab.create();
                a.show();

            }
        });
    }

    private void readData() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference rd = databaseReference.child("Posts");
        rd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot items: dataSnapshot.getChildren())
                {
                    posts = items.getValue(Posts.class);
                    posts.author = "@"+username;
                    tweet.add(posts);
                }
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(tweets.this, "Error reading data!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void writeNewPost(String message) {
        time = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss aaa").format(Calendar.getInstance().getTime());
        posts = new Posts(email, message, time);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference postsref = ref.child("Posts").push();
        postsref.setValue(posts);
        posts = new Posts("@"+username,message,time);
        tweet.add(posts);
        recyclerView.setAdapter(adapter);
    }


    private void updateUI() {
        Toast.makeText(this, "You are logged out!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(tweets.this, signup.class);
        startActivity(intent);
        finish();
    }

    public void account(View view) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(tweets.this);
            alertDialogBuilder.setTitle("My Account");
            alertDialogBuilder.setMessage("username:"+username+"\n"+email).setCancelable(false);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(true);

    }

    public void logout(View view) {
        progressDialog.setMessage("Logging out...");
        progressDialog.show();

        authb.signOut();
        LoginManager.getInstance().logOut();
        updateUI();
        session.logoutUser();
    }
}
