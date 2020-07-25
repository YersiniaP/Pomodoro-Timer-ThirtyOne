package com.example.pomodoro_app;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class TimerActivity extends AppCompatActivity {

    //hardcoding times for now
    private static final long WORK_DURATION = 10000;
    private static final long BREAK_DURATION = 6000;

    private TextView timeDisplay;
    private TextView taskStatus;
    private Button buttonToggleTimer;
   // private Button buttonResetTimer;
    private CountDownTimer workTimer;
    private CountDownTimer breakTimer;
    private long workRemaining = WORK_DURATION;
    private long breakRemaining = BREAK_DURATION;
    private boolean timerTicking;
    private boolean workDone = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        timeDisplay = findViewById(R.id.countdown_text);
        taskStatus = findViewById(R.id.task_status);
        buttonToggleTimer = findViewById(R.id.button_start_pause);
        //buttonResetTimer = findViewById(R.id.button_reset);
        taskStatus.setText("Work Time");
        buttonToggleTimer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (timerTicking) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        /*buttonResetTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });*/
        updateCountDownText();
    }

    private void startTimer() {
        workTimer = new CountDownTimer(workRemaining, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                workRemaining = millisUntilFinished;
                updateCountDownText();
            }

            @Override

            //upon finish of workTimer, creates and starts new breakTimer
            public void onFinish() {
               taskStatus.setText("Break Time");
               workRemaining = breakRemaining;

                //create second timer
                breakTimer = new CountDownTimer(workRemaining, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        workRemaining = millisUntilFinished;
                        updateCountDownText();
                    }

                    @Override
                    public void onFinish() {
                        timerTicking = false;
                        buttonToggleTimer.setText("Start");
                        buttonToggleTimer.setVisibility(View.INVISIBLE);
                       // buttonResetTimer.setVisibility(View.VISIBLE);
                    }
                }.start();

                timerTicking = false;
                workDone = true;
                buttonToggleTimer.setText("Start");
                buttonToggleTimer.setVisibility(View.INVISIBLE);
                //buttonResetTimer.setVisibility(View.VISIBLE);
            }
        }.start();
        timerTicking = true;
        buttonToggleTimer.setText("pause");
       // buttonResetTimer.setVisibility(View.INVISIBLE);
    }

    private void pauseTimer() {
        workTimer.cancel();
        timerTicking = false;
        buttonToggleTimer.setText("Start");
        //buttonResetTimer.setVisibility(View.VISIBLE);
    }

/*    private void resetTimer() {
        workRemaining = WORK_DURATION;
        updateCountDownText();
       // buttonResetTimer.setVisibility(View.INVISIBLE);
        buttonToggleTimer.setVisibility(View.VISIBLE);
    }*/

    private void updateCountDownText() {
        int minutes = (int) (workRemaining / 1000) / 60;
        int seconds = (int) (workRemaining / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timeDisplay.setText(timeLeftFormatted);
    }
}