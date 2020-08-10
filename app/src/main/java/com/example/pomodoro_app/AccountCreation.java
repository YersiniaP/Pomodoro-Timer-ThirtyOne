package com.example.pomodoro_app;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    EditText user_email, user_password, name;
    TextView login;
    boolean isNameValid, isEmailValid, isPasswordValid;
    FirebaseAuth FirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);

        name = (EditText) findViewById(R.id.name);
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
    //*****************************************************************************************
    public void SetValidation() {

        String email = user_email.getText().toString().trim();
        String password = user_password.getText().toString().trim();

        // Check for a valid name.
        if (name.getText().toString().isEmpty()) {
            name.setError(getResources().getString(R.string.name_error));
            isNameValid = false;
        } else {
            isNameValid = true;
        }

        // Check for a valid email address.
        if (user_email.getText().toString().isEmpty()) {
            user_email.setError(getResources().getString(R.string.email_error));
            isEmailValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(user_email.getText().toString()).matches()) {
            user_email.setError(getResources().getString(R.string.email_error));
            isEmailValid = false;
        } else  {
            isEmailValid = true;
        }
        if (user_password.getText().toString().isEmpty()) {
            user_password.setError(getResources().getString(R.string.password_error));
            isPasswordValid = false;
        } else if (user_password.getText().length() < 4)  {
            user_password.setError(getResources().getString(R.string.password_invalid_error));
            isPasswordValid = false;
        } else  {
            isPasswordValid = true;
        }

        /* ***********************************************************************************
                                ----Original Database----
        // Generates database for login validation
        AppDB database = Room.databaseBuilder(getApplicationContext(), AppDB.class,
                "users-database").allowMainThreadQueries().build();

        String target_email = email.getText().toString();
        String target_name = name.getText().toString();
        String target_password = password.getText().toString();
        Users target_email_user = database.users_dao().get_user_by_email(target_email);
        Users target_username_user = database.users_dao().get_user_by_username(target_name);

        if (target_username_user != null){
            Toast.makeText(getApplicationContext(), "Username already exists!", Toast.LENGTH_LONG).show();
            return;
        }
        else if(target_email_user != null)
        {
            Toast.makeText(getApplicationContext(), "Email already exists!", Toast.LENGTH_LONG).show();
            return;
        }
        ********************************************************************************************* */
        if (isNameValid && isEmailValid && isPasswordValid) {
           /* ***********************************************************
                     ----Original Database----
            // Adds user to the database.
            Users this_user = new Users();
            this_user.db_email = target_email;
            this_user.db_password = target_password;
            this_user.db_level = 3;
            this_user.db_xp = 1500;
            this_user.db_username = target_name;
            database.users_dao().insert_user(this_user);

            // Returns user back to Login page.
            Toast.makeText(getApplicationContext(), "User Account Created!", Toast.LENGTH_LONG).show();
            finish(); // Kills the page instead of keeping it inactive.
            ********************************************************************** */
            FirebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(AccountCreation.this, "User Created", Toast.LENGTH_LONG).show();
                        finish();
                    }else{

                        Toast.makeText(AccountCreation.this, "Error" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });


        }
    } //end of set validation

} // end ofpublic class AccountCreation extends AppCompatActivity
// make a short commit here to make sure that I could push the update to github by using andriod studio
