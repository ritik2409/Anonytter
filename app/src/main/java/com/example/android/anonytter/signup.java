package com.example.android.anonytter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.Map;

public class signup extends AppCompatActivity {
    private FirebaseAuth authb;
    private FirebaseUser user;
    private EditText email;
    private EditText password;
    private TextView click_signin;
    private TextView click_signup;
    private ProgressDialog progressDialog;
    private String email2;
    private String username;
    SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        progressDialog = new ProgressDialog(this);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        click_signin = (TextView) findViewById(R.id.submit_signin);
        click_signup = (TextView) findViewById(R.id.submit_signup);


        session = new SessionManager(getApplicationContext());
        authb = FirebaseAuth.getInstance();


    }


    public void submit_signin(View view) {
        if (view == click_signin) {
            signinUsr();
        }
        if (view == click_signup) {
            Intent intent = new Intent(signup.this, LoginActivity.class);
            startActivity(intent);
        }
    }


    public void signinUsr() {
        email2 = email.getText().toString();
        String password2 = password.getText().toString();
        if (TextUtils.isEmpty(email2)) {
            //email is empty}
            Toast.makeText(signup.this, "Please enter email!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password2)) {
            //password is empty
            Toast.makeText(signup.this, "Please Enter Password!", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Logging User...");
        progressDialog.show();


        authb.signInWithEmailAndPassword(email2, password2).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    user = authb.getCurrentUser();
                    username = user.getDisplayName();
                    session.createLoginSession(username,email2);
                    Intent intent = new Intent(signup.this, tweets.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(signup.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                    progressDialog.cancel();

                }
            }
        });
    }
}



