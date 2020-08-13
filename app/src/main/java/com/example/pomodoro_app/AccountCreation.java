package com.example.pomodoro_app;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AccountCreation extends AppCompatActivity {

    Button toast_pop_up, register;
    EditText user_email, user_password, user_name;
    TextView login;
    FirebaseAuth FirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);

        user_name = (EditText) findViewById(R.id.name);
        user_email =(EditText) findViewById(R.id.email);
        user_password = (EditText) findViewById(R.id.password);
        register = (Button) findViewById(R.id.register);
        FirebaseAuth = FirebaseAuth.getInstance();

        // If user is logged in the proceed to progress activity else close, need to fix this
        /*
        if(FirebaseAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),ProgressActivity.class));
            finish();
        }
*/
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetValidation();
            }
        });
    }

    @Override
    public void onBackPressed() {
        /* This prevents the app from automatically closing and insteads returns the user back
        to the login page */
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        finish();
        startActivity(intent);
    }

    public void SetValidation() {
        String email = user_email.getText().toString().trim();
        String password = user_password.getText().toString().trim();
        if (isNameValid() && isEmailValid() && isPasswordValid()) {
            FirebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(AccountCreation.this, "User Created", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        finish();
                        startActivity(intent);
                    } else {
                        Toast.makeText(AccountCreation.this, "Error" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    /* Returns true if the name is not blank.  Returns false if the username is blank and prompts
    * the field error. */
    private Boolean isNameValid(){
        if (user_name.getText().toString().isEmpty()){
            user_name.setError("Enter your user name");
            isEmailValid();
            isPasswordValid();
            return false;
        } else {
            return true;
        }
    }

    /* Returns true if the email is formatted correctly.  Returns false if the email is blank and
    prompts the field error. */
    private Boolean isEmailValid(){
        if (user_email.getText().toString().isEmpty()){
            user_email.setError("Enter your email");
            isPasswordValid();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(user_email.getText().toString()).matches()) {
            user_email.setError("Enter a valid email");
            isPasswordValid();
            return false;
        } else {
            return true;
        }
    }

    /* Returns true if the password is formatted correctly. Returns false if the username is blank and
    prompts the field error. */
    private Boolean isPasswordValid(){
        if (user_password.getText().toString().isEmpty()){
            user_password.setError("Enter your password");
            return false;
        } else if (user_password.getText().length() < 6){
            user_password.setError("Password must be at least 6 characters long");
            return false;
        } else {
            return true;
        }
    }
}
