package com.example.pomodoro_app;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Patterns;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.content.Intent;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    Button btn_Create_Account, btn_Sign_In;
    ImageView Tommy;
    EditText User_EmailAddress, User_Password;
    boolean isEmailValid, isPasswordValid;
    TextView Forgot_Password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        btn_Create_Account = (Button) findViewById(R.id.btn_Create_Account);
        Tommy = (ImageView) findViewById(R.id.Tommy);
        User_EmailAddress = (EditText) findViewById(R.id.Login_EmailAddress);
        User_Password = (EditText) findViewById(R.id.Login_Password);
        btn_Sign_In = (Button) findViewById(R.id.btn_Sign_In);
        Forgot_Password = (TextView) findViewById(R.id.Forgot_Password);


        btn_Sign_In.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetValidation();


            }
        });


        btn_Create_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccountCreation();
            }
        });

        //Click on squeeze me to go to the WhatIS page which explains the APP

        Tommy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WhatIs();
            }
        });


        Forgot_Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WhatIs();
            }
        });








    }








    //*****************************************************************************************
    public void AccountCreation() {
        Intent intent = new Intent(getApplicationContext(), AccountCreation.class);
        startActivity(intent);
    }

    public void WhatIs() {
        Intent intent = new Intent(this, WhatIs.class);
        startActivity(intent);
    }


    public void SetValidation() {
        // Check for a valid name.

        // Check for a valid email address.
        if (User_EmailAddress.getText().toString().isEmpty()) {
            User_EmailAddress.setError(getResources().getString(R.string.email_error));
            isEmailValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(User_EmailAddress.getText().toString()).matches()) {
            User_EmailAddress.setError(getResources().getString(R.string.email_error));
            isEmailValid = false;
        } else {
            isEmailValid = true;
        }
        if (User_Password.getText().toString().isEmpty()) {
            User_Password.setError(getResources().getString(R.string.password_error));
            isPasswordValid = false;
        } else if (User_Password.getText().length() < 4) {
            User_Password.setError(getResources().getString(R.string.password_invalid_error));
            isPasswordValid = false;
        } else {
            isPasswordValid = true;
        }


        if (isEmailValid && isPasswordValid) {
            Toast.makeText(getApplicationContext(), "Welcome!", Toast.LENGTH_LONG).show();
            ProgressActivity();

        }

    }

    public void ProgressActivity() {
        Intent intent = new Intent(this, ProgressActivity.class);
        startActivity(intent);
    }




}
































