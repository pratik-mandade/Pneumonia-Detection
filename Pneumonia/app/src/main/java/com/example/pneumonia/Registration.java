package com.example.pneumonia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.ktx.Firebase;

public class Registration extends AppCompatActivity {

    TextView goToLogin;

    EditText email,password;
    Button register;

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
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.Email);
        password = findViewById(R.id.passWord);
        register = findViewById(R.id.register);
        progressBar = findViewById(R.id.progressBar);
        goToLogin = findViewById(R.id.goToLogin);
        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Login.class);
                startActivity(i);
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String email_txt = email.getText().toString();
                String password_txt = password.getText().toString();

                if(email_txt.isEmpty()){
                    Toast.makeText(Registration.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password_txt.isEmpty()){
                    Toast.makeText(Registration.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email_txt, password_txt)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(Registration.this, "Account Created",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Registration.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });


            }
        });
    }
}