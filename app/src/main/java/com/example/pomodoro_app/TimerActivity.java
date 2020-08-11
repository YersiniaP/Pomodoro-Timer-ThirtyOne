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
    public static int numBreaks;
    public static double breakLengthSessionMinutes;
    public static int totalTimeMinutes;  //ie (endTime - startTime)
    public static String taskName;
    /********************************************************************
    ********************************************************************/

    //configure time variables
    private int numWorkSessions = numBreaks + 1;
    private double totalBreakMinutes = numBreaks * breakLengthSessionMinutes;
    private double totalWorkMinutes = totalTimeMinutes - totalBreakMinutes;
    private double workSessionLengthMinutes = totalWorkMinutes / numWorkSessions;

    //converts times to milliseconds
    private long BREAK_LENGTH_MS = (long) (breakLengthSessionMinutes * 60 * 1000);
    private long WORK_SESSION_LENGTH_MS = (long)(workSessionLengthMinutes * 60 * 1000);

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

    //stopwatch variables
    private Button buttonToggleStopWatch;
    private StopWatch pausedTimeTracker;
    private TextView stopWatchDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        timeDisplay = findViewById(R.id.countdown_text);
        taskStatus = findViewById(R.id.task_status);
        stopWatchDisplay = findViewById(R.id.stopwatch_display);
        buttonToggleTimer = findViewById(R.id.button_start_pause);
        taskStatus.setText(taskName);
        pausedTimeTracker = new StopWatch();
        buttonToggleStopWatch = findViewById(R.id.button_pause_stopwatch);

        buttonToggleStopWatch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (pausedTimeTracker.isStopWatchTicking()) {
                    stopWatchDisplay.setText(pausedTimeTracker.toString());
                }
            }
        });

        buttonToggleTimer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Task pauses
                if (timerTicking) {

                    //Pauses the Work Timer
                    if(atWork) {
                        pauseTimer(workTimer);
                        atWork = false;

                        //toggle pause stopwatch
                        if (pausedTimeTracker.elapsed() != 0){
                            pausedTimeTracker.resume();
                        } else {
                            pausedTimeTracker.startStopWatch();
                        }
                    }
                }

                // Task starts/resumes
                else {
                    atWork = true;
                    taskStatus.setText(taskName);
                    workTimer = createTimer(workRemaining);
                    workTimer.start();
                    timerTicking = true;
                    buttonToggleTimer.setText("pause");

                    pausedTimeTracker.pause();
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
                buttonToggleTimer.setText("start");
                buttonToggleTimer.setVisibility(View.INVISIBLE);
                makeAnotherTimer();
            }
        };
    }

    private void makeAnotherTimer(){

        //transitioning from break to work timer
        if (!atWork) {

            //change timer status to work
            taskStatus.setText(taskName);
            timerTicking = true;
            workTimer = createTimer(WORK_SESSION_LENGTH_MS);
            workTimer.start();
            atWork = true;
            buttonToggleTimer.setText("Pause");
            buttonToggleTimer.setVisibility(View.VISIBLE);

            //stop pause stopwatch
            pausedTimeTracker.pause();
        }
        //transitioning from work to break timer
        else if (atWork && breakCounter < numBreaks){

            //change timer status to break
            taskStatus.setText("Break Time!");
            breakTimer = createTimer(breakRemaining);
            breakTimer.start();
            atWork = false;
            timerTicking = true; //*
            breakCounter++;

            //resume pauseTimer
            pausedTimeTracker.resume();
        }
        else { //recursion ends; no new timers
            taskStatus.setText("DONE!");
        }
    }

    private void pauseTimer( CountDownTimer currentTimer) {
        currentTimer.cancel();
        timerTicking = false;
        buttonToggleTimer.setText("start");
    }

    private void updateCountDownText(long timeRemainingMS) {
        int minutes = (int) (timeRemainingMS / 1000) / 60;
        int seconds = (int) (timeRemainingMS / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timeDisplay.setText(timeLeftFormatted);
    }

};

//from https://gist.github.com/EdHurtig/78cbe307c1c85db12af7
class StopWatch {

    //time variables
    private long startTimeNS;
    private long endTimerNS;
    private long pausePointNS;

    //status variables
    private boolean stopWatchTicking = false;
    private boolean stopWatchPaused = false;

    //constructor
    protected StopWatch(){
        startTimeNS = 0;
        endTimerNS = 0;
        pausePointNS = 0;
    }

    //get functions
    public boolean isStopWatchTicking() {
        return stopWatchTicking;
    }
    public boolean isStopWatchPaused() {
        return stopWatchPaused;
    }

    public void startStopWatch(){
        startTimeNS = System.nanoTime();
        stopWatchTicking = true;
        stopWatchPaused = false;
    }


    /**
     * Pauses the Stopwatch
     *
     * @return The time elapsed so far
     */
    public long pause() {
        if (!isStopWatchTicking()) {
            return -1;
        } else if (isStopWatchPaused()) {
            return (pausePointNS - startTimeNS);
        } else {
            pausePointNS = System.nanoTime();
            stopWatchPaused = true;
            return (pausePointNS - startTimeNS);
        }
    }

    /**
     * Resumes the StopWatch from it's paused state
     */
    public void resume() {
        if (isStopWatchPaused() && isStopWatchTicking()) {
            startTimeNS = System.nanoTime() - (pausePointNS - startTimeNS);
            stopWatchPaused = false;
        }
    }

    /**
     * Returns the total time elapsed
     *
     * @return The total time elapsed
     */
    public long elapsed() {
        if (isStopWatchTicking()) {
            if (isStopWatchPaused())
                return (pausePointNS - startTimeNS);
            return (System.nanoTime() - startTimeNS);
        } else
            return (endTimerNS - startTimeNS);
    }

    /**
     * Returns the number of seconds this Stopwatch has elapsed
     *
     * @return The String of the number of seconds
     */
    public String toString() {
        long timeElapsed = elapsed();
        return ((double) timeElapsed / 1000000000.0) + " Seconds";
    }
};
