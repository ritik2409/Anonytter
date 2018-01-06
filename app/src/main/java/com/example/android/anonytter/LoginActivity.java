package com.example.android.anonytter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class LoginActivity extends AppCompatActivity {
    private EditText textmail;
    private EditText textpass;
    private EditText textusername;
    private ProgressDialog progressDialog;
    private TextView submit_signup;
    private FirebaseAuth firebaseAuth;
    private String username;
    private String email;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionManager(getApplicationContext());
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        textmail = (EditText) findViewById(R.id.email);
        textpass = (EditText) findViewById(R.id.password);
        textusername = (EditText) findViewById(R.id.username);
        submit_signup = (TextView) findViewById(R.id.submit_signup);
    }


    public void submit_signup(View view) {
        if (view == submit_signup) {
            registerUser();

        }

    }




    public void registerUser() {
        username = textusername.getText().toString();
        email = textmail.getText().toString().trim();
        String password = textpass.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            //email is empty}
            Toast.makeText(this, "Please enter email!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            //password is empty
            Toast.makeText(this, "Please Enter Password!", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Registering User...");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(username).build();
                            user.updateProfile(profileUpdates);



                            //user registered
                            Toast.makeText(LoginActivity.this, "Registeration Complete", Toast.LENGTH_SHORT).show();
                            session.createLoginSession(email,username);
                            Intent intent = new Intent(LoginActivity.this, tweets.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Could not register, please try again", Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                        }
                    }
                });

    }


}
