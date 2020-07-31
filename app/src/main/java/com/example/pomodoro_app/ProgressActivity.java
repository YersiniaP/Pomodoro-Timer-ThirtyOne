package com.example.pomodoro_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProgressActivity extends AppCompatActivity {

    private Button progress_button_level;
    private Button progress_button_sign_out;
    private Button progress_button_create_task;
    private String active_email; // email of user
    public static final String EXTRA_EMAIL = "com.example.pomodoro_app.EXTRA_EMAIL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        Intent intent_user = getIntent(); // Used to grab email from login page
        active_email = intent_user.getStringExtra(Intent.EXTRA_EMAIL);

        // Clicking Level button initializes the Rewards page.
        progress_button_level = (Button) findViewById(R.id.progress_button_level);
        progress_button_level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenRewardsPage();
            }
        });

        // Clicking Sign Out button switches to the Login page.
        progress_button_sign_out = (Button) findViewById(R.id.progress_button_sign_out);
        progress_button_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Clicking Create Task button switches to the Task Creation page.
        progress_button_create_task = (Button) findViewById(R.id.progress_button_create);
        progress_button_create_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenTaskCreationPage();
            }
        });
    }

    // When the user clicks on the Level button from the Progress page, the rewards page opens.
    public void OpenRewardsPage() {
        Intent intent = new Intent(this, RewardsActivity.class);
        intent.putExtra(EXTRA_EMAIL, active_email); // Passes active email to Rewards page.
        startActivity(intent);
    }

    // When the user clicks on the Level button from the Progress page, the rewards page opens.
    public void OpenLoginPage() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    // When the user clicks on the Level button from the Progress page, the rewards page opens.
    public void OpenTaskCreationPage() {
        Intent intent = new Intent(this, TaskCreationActivity.class);
        startActivity(intent);
    }

}