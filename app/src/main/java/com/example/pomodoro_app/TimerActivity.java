package com.example.pomodoro_app;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;


public class TimerActivity extends AppCompatActivity {

    /********************************************************************
    ** hardcoding for now, eventually gathered from task creation page **
    ** replace all right side values with links to user entered data   **
    /*******************************************************************/
    private int numBreaks = 2;
    private int breakLengthSessionMinutes = 2;
    private int totalTimeMinutes = 7;  //ie (endTime - startTime)
    private String taskName = "Study";
    /********************************************************************
    ********************************************************************/

    //configure time variables
    private int numWorkSessions = numBreaks + 1;
    private int totalBreakMinutes = numBreaks * breakLengthSessionMinutes;
    private int totalWorkMinutes = totalTimeMinutes - totalBreakMinutes;
    private int workSessionLengthMinutes = totalWorkMinutes / numWorkSessions;

    //converts times to milliseconds
    private long BREAK_LENGTH_MS = breakLengthSessionMinutes * 60 * 1000;
    private long WORK_SESSION_LENGTH_MS = workSessionLengthMinutes * 60 * 1000;

    //names UI elements for reference
    private TextView timeDisplay;
    private TextView taskStatus;
    private Button buttonToggleTimer;

    private CountDownTimer workTimer;
    private CountDownTimer breakTimer;

    private long workRemaining = WORK_SESSION_LENGTH_MS;
    private long breakRemaining = BREAK_LENGTH_MS;
    private int breakCounter = 0;
    private boolean timerTicking;
    private boolean atWork = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        timeDisplay = findViewById(R.id.countdown_text);
        taskStatus = findViewById(R.id.task_status);
        buttonToggleTimer = findViewById(R.id.button_start_pause);
        taskStatus.setText(taskName);
        buttonToggleTimer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (timerTicking) {
                    if(atWork) {
                        pauseTimer(workTimer);
                        atWork = false;
                    } else {
                        pauseTimer(breakTimer);
                        atWork = true;
                        breakCounter++;
                    }
                } else {
                    taskStatus.setText(taskName);
                    workTimer = createTimer(workRemaining);
                    workTimer.start();
                    timerTicking = true;
                    buttonToggleTimer.setText("pause");
                }
            }
        });
        updateCountDownText(workRemaining);
    }

    private CountDownTimer createTimer(long newTime) {
        return new CountDownTimer(newTime, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                updateCountDownText(millisUntilFinished);
                workRemaining = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                timerTicking = false;
                buttonToggleTimer.setText("Start");
                buttonToggleTimer.setVisibility(View.INVISIBLE);
                makeAnotherTimer();
            }
        };
    }

    private void makeAnotherTimer(){
        if (!atWork) {
            taskStatus.setText(taskName);
            workTimer = createTimer(workRemaining);
            workTimer.start();
            atWork = true;
            buttonToggleTimer.setText("Pause");
            buttonToggleTimer.setVisibility(View.VISIBLE);
        } else if (atWork && breakCounter < numBreaks){
            taskStatus.setText("Break Time!");
            breakTimer = createTimer(breakRemaining);
            breakTimer.start();
            atWork = false;
            breakCounter++;
        } //else {recursion ends; no new timers}
    }

    private void pauseTimer( CountDownTimer currentTimer) {
        currentTimer.cancel();
        timerTicking = false;
        buttonToggleTimer.setText("Start");
    }

    private void updateCountDownText(long timeRemainingMS) {
        int minutes = (int) (timeRemainingMS / 1000) / 60;
        int seconds = (int) (timeRemainingMS / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timeDisplay.setText(timeLeftFormatted);
    }
}
