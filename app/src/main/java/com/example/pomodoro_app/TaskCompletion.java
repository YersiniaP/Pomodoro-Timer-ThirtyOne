package com.example.pomodoro_app;


import android.os.Bundle;
import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class TaskCompletion extends AppCompatActivity {

    private Button button_yes;
    private Button button_no;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_completion);
        button_yes = findViewById(R.id.button_yes);
        button_no = findViewById(R.id.button_no);

        button_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Returns users to the reward page if chose yes
                Intent intent = new Intent(getApplicationContext(), RewardsActivity.class);
                finish();
                startActivity(intent);
            }
        });

        button_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Returns users to the reward page if chose no
                Intent intent = new Intent(getApplicationContext(), RewardsActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }
}