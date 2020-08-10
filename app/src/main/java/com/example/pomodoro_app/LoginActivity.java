package com.example.pomodoro_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Parcelable;
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
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {
    Button btn_Create_Account;
    Button btn_Sign_In;
    ImageView Tommy;
    EditText editTextTextEmailAddress;
    EditText editTextTextPassword;
    private AppDB database;
    public static final String EXTRA_EMAIL = "com.example.pomodoro_app.EXTRA_EMAIL";
    FirebaseAuth FirebaseAuth;
    TextView forgot_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //get Firebase instance
        FirebaseAuth = FirebaseAuth.getInstance();

        // Generates database for login validation

        /* ********************** -----original database------ **********************
        database = Room.databaseBuilder(getApplicationContext(), AppDB.class,
                "users-database").allowMainThreadQueries().build();
        **************************************************************************** */
        editTextTextEmailAddress = (EditText) findViewById(R.id.editTextTextEmailAddress);
        editTextTextPassword = (EditText) findViewById(R.id.editTextTextPassword);
        forgot_password = (TextView) findViewById(R.id.forgot_password);
        //Click the 'Create Account button and go to the Account creation activity
        btn_Create_Account = (Button) findViewById(R.id.btn_Create_Account);
        Tommy = (ImageView) findViewById(R.id.Tommy);

        btn_Create_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccountCreation();
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
                WhatIs();
            }
        });

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PasswordRecovery();
            }
        });


    }


    // Validates credentials and sends user to Progress page
    public void GoToProgress() {
        // Validates formatting input
        String email = editTextTextEmailAddress.getText().toString();
        String password = editTextTextPassword.getText().toString();
        // --- original database---Users target_user = database.users_dao().get_user_by_email(email); // The user to look for

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Missing input.",
                    Toast.LENGTH_LONG).show();
            return;
        }

        /* **************************** --- original database--- *********************
        // Loads the Progress page
        if (target_user == null) {
            // generate an error message
            Toast.makeText(getApplicationContext(), "Email not found.",
                    Toast.LENGTH_LONG).show();
        } else if (target_user.db_password.equals(password)) {
            Intent intent = new Intent(this, ProgressActivity.class);
            intent.putExtra(EXTRA_EMAIL, target_user.db_email); // Sends active email to Progress
            startActivity(intent);
        } else {
            // generate an error message
            Toast.makeText(this, "Invalid Username/Password!",
                    Toast.LENGTH_LONG).show();
        }

        ************************************************************************************** */

        FirebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),ProgressActivity.class));
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Invalid Email/Password", Toast.LENGTH_SHORT).show();
                }

            }
        });


    } //end of GoProgress function


            public void AccountCreation() {
                Intent intent = new Intent(getApplicationContext(), AccountCreation.class);
                startActivity(intent);
            }

            public void WhatIs() {
                Intent intent = new Intent(getApplicationContext(), WhatIs.class);
                startActivity(intent);
            }

            public void PasswordRecovery() {
                Intent intent = new Intent(getApplicationContext(), PasswordRecovery.class);
                startActivity(intent);
            }




    }// end of public class loginActivity













