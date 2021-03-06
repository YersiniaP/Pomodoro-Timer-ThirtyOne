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
    private TextView rewards_level;
    private Button rewards_button_close;
    private ProgressBar rewards_xp_circle;
    private TextView rewards_total_xp;
    private TextView rewards_remaining_xp;

    // Database
    private AppDB database;
    private String active_email; //Email of logged in user
    public static final String EXTRA_EMAIL = "com.example.pomodoro_app.EXTRA_EMAIL";
    Users user_entry;

    // XP Circle
    public static final Integer MAX_XP = 1000; // How much XP per level
    private int current_xp_position = 0; // Where the xp circle is currently at
    private int stopping_xp_position; // Where the xp meter stops filling at


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        // Connects elements
        rewards_level = findViewById(R.id.rewards_textview_level);
        rewards_total_xp = findViewById(R.id.rewards_textview_total_xp);
        rewards_xp_circle = findViewById(R.id.rewards_xp_circle);
        rewards_xp_circle.setProgress(current_xp_position);
        rewards_remaining_xp = findViewById(R.id.rewards_textview_remaining_xp);

        // Clicking Close button returns to the Progress page.
        rewards_button_close = (Button) findViewById(R.id.rewards_button_close);
        rewards_button_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProgressActivity.class);
                intent.putExtra(EXTRA_EMAIL, active_email); // Passes active email to Rewards page.
                finish();
                startActivity(intent);
            }
        });

        // Receives data from ProgressActivity intent
        Intent intent_email = getIntent();
        active_email = intent_email.getStringExtra(ProgressActivity.EXTRA_EMAIL);
        if (active_email == null){
            active_email = intent_email.getStringExtra(TaskCompletion.EXTRA_EMAIL);
        }

        // Generates database for accessing the user's level stats
        database = Room.databaseBuilder(getApplicationContext(), AppDB.class,
                "users-database").allowMainThreadQueries().build();

        // Finds user by email address
        user_entry = database.users_dao().get_user_by_email(active_email);
        Log.e("user_entry", String.valueOf(user_entry));
        Log.e("active_email", String.valueOf(active_email));

        // Determines the XP remaining until the next level is reached.
        stopping_xp_position = user_entry.db_xp % MAX_XP;

        // Updates elements depending on database information
        rewards_total_xp.setText(String.valueOf(user_entry.db_xp));
        rewards_level.setText(String.valueOf(user_entry.db_level));

        /* In a separate thread, the xp progress starts at zero and increases until it hits the
        amount of xp left in that level. */
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (current_xp_position < stopping_xp_position){
                    current_xp_position++;
                    android.os.SystemClock.sleep(1);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            rewards_xp_circle.setProgress(current_xp_position);
                            Integer reversed_xp = MAX_XP - current_xp_position;
                            String xp_string = reversed_xp + " XP";
                            rewards_remaining_xp.setText(xp_string);
                        }
                    });
                }
            }
        };
        thread.start();


    }

    @Override
    public void onBackPressed() {
        /* This prevents the app from automatically closing and instead returns the user back
        to the progress page */
        Intent intent = new Intent(getApplicationContext(), ProgressActivity.class);
        intent.putExtra(EXTRA_EMAIL, active_email); // Passes active email to Rewards page.
        finish();
        startActivity(intent);
    }
}