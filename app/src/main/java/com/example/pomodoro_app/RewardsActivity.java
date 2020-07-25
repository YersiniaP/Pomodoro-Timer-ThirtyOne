package com.example.pomodoro_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ProgressBar;
import android.widget.TextView;

public class RewardsActivity extends AppCompatActivity {

    // For animation of XP progress bar
    private TextView level_text;
    private ProgressBar xp_circle;
    private TextView xp_text;
    private int progress_point = 0; // Where the xp circle is currently at
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        // Connecting XML elements
        level_text = findViewById(R.id.rewards_textview_next_level);
        xp_text = findViewById(R.id.rewards_textview_total_xp);
        xp_circle = findViewById(R.id.rewards_xp_circle);
        xp_circle.setProgress(progress_point);

        // Loading in background thread
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (progress_point < 777){  // 1000 should be replaced with the current xp level
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
    }
}