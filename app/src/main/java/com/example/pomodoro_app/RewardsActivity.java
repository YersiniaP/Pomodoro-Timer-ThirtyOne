package com.example.pomodoro_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class RewardsActivity extends AppCompatActivity {
    // Logic
    private Handler handler = new Handler(Looper.getMainLooper());

    // UI
    private TextView level_text;
    private Button rewards_button_close;
    private ProgressBar xp_circle;
    private TextView xp_text;

    // Database
    private AppDB database;
    private String active_email; //Email of logged in user

    // XP Circle
    public static final Integer MAX_XP = 1000; // How much XP per level
    private int current_xp_position = 0; // Where the xp circle is currently at
    private int stopping_xp_position; // Where the xp meter stops filling at


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        // Connects elements
        level_text = findViewById(R.id.rewards_textview_next_level);
        xp_text = findViewById(R.id.rewards_textview_total_xp);
        xp_circle = findViewById(R.id.rewards_xp_circle);
        xp_circle.setProgress(current_xp_position);

        // Clicking Close button returns to the Progress page.
        rewards_button_close = (Button) findViewById(R.id.rewards_button_close);
        rewards_button_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Receives data from ProgressActivity intent
        Intent intent_email = getIntent();
        active_email = intent_email.getStringExtra(ProgressActivity.EXTRA_EMAIL);
        if (active_email == null){
            Log.e("rewards email load", "active_email not loaded");
            return;
        }

        // Generates database for accessing the user's level stats
        database = Room.databaseBuilder(getApplicationContext(), AppDB.class,
                "users-database").allowMainThreadQueries().build();

        // Finds user by email address
        Users target_user = database.users_dao().get_user_by_email(active_email);
        if (target_user == null)
        {
            Log.e("rewards user query", "user not found in database");
            return;
        }

        // Determines the XP remaining until the next level is reached.
        stopping_xp_position = target_user.db_xp % MAX_XP;

        // Updates elements depending on database information
        xp_text.setText(String.valueOf(target_user.db_xp));
        level_text.setText(String.valueOf(target_user.db_level));

        // Loading in background thread
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (current_xp_position < MAX_XP){  // replace 1000 with
                    current_xp_position++;
                    android.os.SystemClock.sleep(5);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            // Updates progress bar each second until the xp level has been reached
                            xp_circle.setProgress(current_xp_position);
                            String xp_string = current_xp_position + " XP";
                            xp_text.setText(xp_string);
                        }
                    });
                }
            }
        };
        thread.start();


    }
}