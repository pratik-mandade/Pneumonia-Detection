package com.example.pneumonia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.zip.Inflater;

public class Login extends AppCompatActivity {

    TextView goToRegister;

    EditText email,password;
    Button login;

    FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.Email_login);
        password = findViewById(R.id.passWord_login);
        mAuth = FirebaseAuth.getInstance();
        goToRegister = findViewById(R.id.goToRegister);
        progressBar = findViewById(R.id.progressBar_2);
        login = findViewById(R.id.login);

        goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Registration.class);
                startActivity(i);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String email_txt = email.getText().toString();
                String password_txt = password.getText().toString();

                if(email_txt.isEmpty()){
                    Toast.makeText(Login.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password_txt.isEmpty()){
                    Toast.makeText(Login.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email_txt, password_txt)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(Login.this, "Login SuccessFul",
                                            Toast.LENGTH_SHORT).show();

                                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(i);
                                    finish();


                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(Login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });


    }
}