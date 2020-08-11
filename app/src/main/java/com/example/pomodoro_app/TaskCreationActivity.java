package com.example.pomodoro_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import static com.example.pomodoro_app.TimerActivity.taskName;

public class TaskCreationActivity extends AppCompatActivity {
    private Spinner mSpinner;

    private Button createTaskButton;

    //buttons for selecting times
    private Button startTimeButton;
    private Button endTimeButton;
    private Button saveTime;


    public TimePicker startTimePicker;

    //timePicker fields
    private TextView startTime;
    private TextView time;
    private Calendar calendar;
    private String format = "";

    //taskCreation fields
    EditText inputTaskName;
    EditText inputShortBreak;
    EditText inputNumBreaks;

    //used to calculate total task time
    int startTimeMinutes = 0;
    int endTimeMinutes = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_creation);
        mSpinner = findViewById(R.id.categoryDropDown);
        mSpinner.setAdapter(getArrayAdapter());

        /**v*NEW!*v*********************************************/
        //timePicker initialization
        startTimePicker = (TimePicker) findViewById(R.id.startTimePicker);
        startTime = (TextView) findViewById(R.id.startTimeButton);
        saveTime = findViewById(R.id.set_time_picker_button);
        time = (TextView) findViewById(R.id.textView1);
        calendar = Calendar.getInstance();

        //intitialize buttons for task creation
        startTimeButton = findViewById(R.id.startTimeButton);
        endTimeButton = findViewById(R.id.endTimeButton);
        createTaskButton = findViewById(R.id.create_button);
        inputTaskName = (EditText) findViewById(R.id.editTaskName);
        inputShortBreak = (EditText) findViewById(R.id.editShortBreak);
        inputNumBreaks = (EditText) findViewById(R.id.editNumBreaks);

        //displays & gathers picker information
        saveTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = startTimePicker.getHour();
                int minute = startTimePicker.getMinute();

                //sets start (first) and end (second) variables
                if(startTimeMinutes != 0){
                    endTimeMinutes = calcTaskTimeMinutes(hour, minute);
                } else {
                    startTimeMinutes = calcTaskTimeMinutes(hour, minute);
                }

                //change widget display
                setTime(v);
                togglePickerVisibility();
            }
        });

        //overrides toggle the timePicker display over other taskCreation fields
        startTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePickerVisibility();
            }
        });
        endTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePickerVisibility();
            }
        });


        //final button to send information to timerActivity
        createTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                taskName = inputTaskName.getText().toString();
                TimerActivity.breakLengthSessionMinutes = Double.parseDouble(inputShortBreak.getText().toString());
                TimerActivity.numBreaks = Integer.parseInt(inputNumBreaks.getText().toString());

                //accounts for 12 hour time calculations
                if (endTimeMinutes < startTimeMinutes){
                    endTimeMinutes = endTimeMinutes + (12 * 60);
                }

                TimerActivity.totalTimeMinutes = endTimeMinutes - startTimeMinutes;
                goToTimerActivity();
            }
        });
        /***^****^*********************************************/

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (mSpinner.getAdapter().getItem(i).equals("NewCategory")){
                    startActivityForResult(new Intent(TaskCreationActivity.this, NewCategoryActivity.class), 100);
                    mSpinner.setSelection(0,true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 101) {
            mSpinner.setAdapter(getArrayAdapter());
        }
    }

    private ArrayAdapter getArrayAdapter() {
        SharedPreferences sharedPreferences = getSharedPreferences("timer", MODE_PRIVATE);
        String category = sharedPreferences.getString("category", "");
        String[] split = category.split("-");

        String[] arr;
        if (category.isEmpty() || split == null || split.length == 0) {
            arr = new String[]{"Choose a Category:", "NewCategory"};
        } else {
            arr = new String[2 + split.length];
            arr[0] = "Choose a Category:";
            arr[1] = "NewCategory";

            for (int i = 0; i < split.length; i++) {
                arr[i + 2] = split[i];
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return adapter;
    }

    public void Create(View view) {
        if (mSpinner.getSelectedItem() != null)
            LogUtils.showLog(mSpinner.getSelectedItem().toString());
    }

    /***v*NEW*v***************************************************/
    //converts hours&minutes to minutes
    public int calcTaskTimeMinutes(int sHour, int sMinute){
        int sTotalMinutes = (sHour * 60) + sMinute;
        return sTotalMinutes;
    }

    //changes overlay of timePicker
    public void togglePickerVisibility(){
        if (startTimePicker.isShown()){
            //hides picker widgets
            startTimePicker.setVisibility(View.INVISIBLE);
            saveTime.setVisibility(View.INVISIBLE);
            time.setVisibility(View.INVISIBLE);

            //reveals taskCreation fields
            mSpinner.setVisibility(View.VISIBLE);
            inputTaskName.setVisibility(View.VISIBLE);
            inputShortBreak.setVisibility(View.VISIBLE);
            inputNumBreaks.setVisibility(View.VISIBLE);
            startTimeButton.setVisibility(View.VISIBLE);
            endTimeButton.setVisibility(View.VISIBLE);
        }
        else{
            startTimePicker.setVisibility(View.VISIBLE);
            saveTime.setVisibility(View.VISIBLE);
            time.setVisibility(View.VISIBLE);

            mSpinner.setVisibility(View.INVISIBLE);
            inputTaskName.setVisibility(View.INVISIBLE);
            inputShortBreak.setVisibility(View.INVISIBLE);
            inputNumBreaks.setVisibility(View.INVISIBLE);
            startTimeButton.setVisibility(View.INVISIBLE);
            endTimeButton.setVisibility(View.INVISIBLE);
        }
    }

    //picker functions https://www.tutorialspoint.com/android/android_timepicker_control.htm
    public void setTime(View view) {
        int hour = startTimePicker.getHour();
        int min = startTimePicker.getMinute();
        showTime(hour, min);
    }
    public void showTime(int hour, int min) {
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

        time.setText(new StringBuilder().append(hour).append(" : ").append(min)
                .append(" ").append(format));

}

    public void goToTimerActivity() {
        Intent intent = new Intent(getApplicationContext(), TimerActivity.class);
        startActivity(intent);
    }
}
