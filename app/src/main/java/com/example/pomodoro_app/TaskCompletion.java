package com.example.pomodoro_app;


import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class TaskCompletion extends AppCompatActivity {

    private Button button_yes;
    private Button button_no;
    String active_email;
    public static final String EXTRA_EMAIL = "com.example.pomodoro_app.EXTRA_EMAIL";

    @Override
    public void onBackPressed() {
        // Prevents user from using back button on this screen
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_completion);
        button_yes = findViewById(R.id.button_yes);
        button_no = findViewById(R.id.button_no);

        // Receive intent
        Intent intent_user = getIntent();
        active_email = intent_user.getStringExtra(TimerActivity.EXTRA_EMAIL);

        button_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Returns users to the reward page if chose yes
                Intent intent = new Intent(getApplicationContext(), RewardsActivity.class);
                intent.putExtra(EXTRA_EMAIL, active_email); // Passes active email to Rewards page.
                finish();
                startActivity(intent);
            }
        });

        button_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Returns users to the reward page if chose no
                Intent intent = new Intent(getApplicationContext(), RewardsActivity.class);
                intent.putExtra(EXTRA_EMAIL, active_email); // Passes active email to Rewards page.
                finish();
                startActivity(intent);
            }
        });
    }
}