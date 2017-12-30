package com.example.android.anonytter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class tweets extends AppCompatActivity {

    private FirebaseAuth authb;
    private EditText et;
    private String message;
    private String id;
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private ArrayList<String> tweet;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweets);

        progressDialog = new ProgressDialog(this);

        recyclerView = (RecyclerView) findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        authb = FirebaseAuth.getInstance();
        Button btn = (Button) findViewById(R.id.new_post);
        tweet = new ArrayList<>();
        adapter = new MyAdapter(tweet);
        tweet.add("This");
        tweet.add("is a");
        tweet.add("sample");
        tweet.add("tweet");
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
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("tweeets");
                            id = myRef.push().getKey();

                            myRef.child(id).setValue(message);
                            tweet.add(message);
                            recyclerView.setAdapter(adapter);
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

    public void account(View view) {

        signup s = new signup();
        String mail = s.getEmail2();
        if (mail==null) {
            LoginActivity l = new LoginActivity();
            mail = l.getEmail();
        }
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(tweets.this);
        alertDialogBuilder.setTitle("My Account");
        alertDialogBuilder.setMessage(mail).setCancelable(false);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(true);
    }

    public void logout(View view) {
        progressDialog.setMessage("Logging out...");
        progressDialog.show();

        authb.signOut();
        Intent intent = new Intent(tweets.this, signup.class);
        startActivity(intent);

    }
}
