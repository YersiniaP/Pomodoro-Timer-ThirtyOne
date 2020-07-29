package com.example.pomodoro_app;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.ImageView;
import android.widget.Toast;
import android.content.Intent;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    Button btn_Create_Account;
    ImageView Tommy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //Click the 'Create Account button and go to the Account creation activity
        btn_Create_Account = (Button) findViewById(R.id.btn_Create_Account);
        Tommy = (ImageView) findViewById(R.id.Tommy);


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












    }



    public void AccountCreation() {
        Intent intent = new Intent(getApplicationContext(), AccountCreation.class);
        startActivity(intent);
    }

    public void WhatIs() {
        Intent intent = new Intent(this, WhatIs.class);
        startActivity(intent);
    }







}













