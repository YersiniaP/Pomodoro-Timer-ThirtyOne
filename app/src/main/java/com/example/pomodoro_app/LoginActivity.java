package com.example.pomodoro_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;



public class LoginActivity extends AppCompatActivity {
    Button btn_Create_Account;
    Button btn_Sign_In;
    ImageView Tommy;
    EditText editTextTextEmailAddress;
    EditText editTextTextPassword;
    public static final String EXTRA_EMAIL = "com.example.pomodoro_app.EXTRA_EMAIL";
    FirebaseAuth FirebaseAuth;
    TextView forgot_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //get Firebase instance
        FirebaseAuth = FirebaseAuth.getInstance();

        editTextTextEmailAddress = (EditText) findViewById(R.id.editTextTextEmailAddress);
        editTextTextPassword = (EditText) findViewById(R.id.editTextTextPassword);
        forgot_password = (TextView) findViewById(R.id.forgot_password);

        //Click the 'Create Account button and go to the Account creation activity
        btn_Create_Account = (Button) findViewById(R.id.btn_Create_Account);
        Tommy = (ImageView) findViewById(R.id.Tommy);

        btn_Create_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AccountCreation.class);
                finish();
                startActivity(intent);
            }
        });

        // Click Login to check credentials and go to Progress page if successful
        btn_Sign_In = (Button) findViewById(R.id.btn_Sign_In);
        btn_Sign_In.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoToProgress();
            }
        });

        //Click on squeeze me to go to the WhatIS page which explains the APP
        Tommy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WhatIs.class);
                startActivity(intent);
            }
        });

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PasswordRecovery.class);
                finish();
                startActivity(intent);
            }
        });


    }

    @Override
    public void onBackPressed() {
        /* This prevents the app from automatically closing when back is pressed */
        return;
    }


    // Validates credentials and sends user to Progress page
    public void GoToProgress() {
        // Validates formatting input
        final String email = editTextTextEmailAddress.getText().toString();
        final String password = editTextTextPassword.getText().toString();
        // --- original database---Users target_user = database.users_dao().get_user_by_email(email); // The user to look for

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Missing input.",
                    Toast.LENGTH_LONG).show();
            return;
        }

        FirebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Welcome", Toast.LENGTH_SHORT).show();

                    // Sends user to Progress page along with the user's email
                    Intent intent = new Intent(getApplicationContext(), ProgressActivity.class);
                    intent.putExtra(EXTRA_EMAIL, email);
                    finish();
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Invalid Email/Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    } //end of GoProgress function
    }// end of public class loginActivity













