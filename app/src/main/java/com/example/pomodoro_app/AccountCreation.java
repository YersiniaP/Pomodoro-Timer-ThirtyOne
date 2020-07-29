package com.example.pomodoro_app;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;




public class AccountCreation extends AppCompatActivity {

    Button toast_pop_up, register;
    EditText email, password, name;
    TextView login;
    boolean isNameValid, isEmailValid, isPasswordValid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);

        name = (EditText) findViewById(R.id.name);
        email =(EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        register = (Button) findViewById(R.id.register);





        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetValidation();


            }
        });






    }
    //*****************************************************************************************
    public void SetValidation() {
        // Check for a valid name.
        if (name.getText().toString().isEmpty()) {
            name.setError(getResources().getString(R.string.name_error));
            isNameValid = false;
        } else {
            isNameValid = true;
        }

        // Check for a valid email address.
        if (email.getText().toString().isEmpty()) {
            email.setError(getResources().getString(R.string.email_error));
            isEmailValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            email.setError(getResources().getString(R.string.email_error));
            isEmailValid = false;
        } else  {
            isEmailValid = true;
        }
        if (password.getText().toString().isEmpty()) {
            password.setError(getResources().getString(R.string.password_error));
            isPasswordValid = false;
        } else if (password.getText().length() < 4)  {
            password.setError(getResources().getString(R.string.password_invalid_error));
            isPasswordValid = false;
        } else  {
            isPasswordValid = true;
        }



        if (isNameValid && isEmailValid && isPasswordValid) {
            Toast.makeText(getApplicationContext(), "User Account Created!", Toast.LENGTH_LONG).show();
            MainActivity();
        }

    }








    public void MainActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }




}

