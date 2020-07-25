package com.example.pomodoro_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private Button btn_Create_Account;
    private TextView txtSqueezeMe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //Click the 'Sign On' button and go to the Account creation activity
        btn_Create_Account = (Button) findViewById(R.id.btn_Create_Account);
        btn_Create_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccountCreation();
            }
        });

        //Click on squeeze me to go to the WhatIS page which explains the APP
        txtSqueezeMe = (TextView) findViewById(R.id.txtSqueezeMe);
        txtSqueezeMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WhatIs();
            }
        });












    }



    public void AccountCreation() {
        Intent intent = new Intent(this, AccountCreation.class);
        startActivity(intent);
    }

    public void WhatIs() {
        Intent intent = new Intent(this, WhatIs.class);
        startActivity(intent);
    }







}













