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

    // For animation of XP progress bar
    private TextView level_text;
    private Button rewards_button_close;
    private ProgressBar xp_circle;
    private TextView xp_text;
    private int progress_point = 0; // Where the xp circle is currently at
    private Handler handler = new Handler(Looper.getMainLooper());
    private String active_email; //Email of logged in user
    private AppDB database;
    public static final Integer MAX_XP = 1000; // How much XP per level

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        // Receives data from ProgressActivity intent
        Intent intent_email = getIntent();
        active_email = intent_email.getStringExtra(ProgressActivity.EXTRA_EMAIL);

        // Connecting XML elements
        level_text = findViewById(R.id.rewards_textview_next_level);
        xp_text = findViewById(R.id.rewards_textview_total_xp);
        xp_circle = findViewById(R.id.rewards_xp_circle);
        xp_circle.setProgress(progress_point);

        // Generates database for accessing the user's level stats
        database = Room.databaseBuilder(getApplicationContext(), AppDB.class,
                "users-database").allowMainThreadQueries().build();

        Users target_user = database.users_dao().get_user_by_email(active_email);

        Log.e("active_email", active_email);

        // Updates UI elements
        xp_text.setText(String.valueOf(target_user.db_xp));
        level_text.setText(String.valueOf(target_user.db_level));
        Log.e("active_xp", String.valueOf(target_user.db_xp));
        Log.e("active_level", String.valueOf(target_user.db_level));

        // Loading in background thread
        Thread thread = new Thread() {
            @Override
            public void run() {
                /*
                I need to rework this. There needs to be a way to preserve where the progress point
                is in the current level. Once the meter maxes out, it should reset to 0 and start
                again but still show total xp earned this session.
                 */
                while (progress_point < MAX_XP){  // replace 1000 with
                    progress_point++;
                    android.os.SystemClock.sleep(5);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            // Updates progress bar each second until the xp level has been reached
                            xp_circle.setProgress(progress_point);
                            String xp_string = progress_point + " XP";
                            xp_text.setText(xp_string);
                        }
                    });
                }
            }
        };
        thread.start();

        // Clicking Close button returns to the Progress page.
        rewards_button_close = (Button) findViewById(R.id.rewards_button_close);
        rewards_button_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}