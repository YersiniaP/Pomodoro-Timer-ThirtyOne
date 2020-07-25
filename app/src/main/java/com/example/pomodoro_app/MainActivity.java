package com.example.pomodoro_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {
    private Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSpinner = findViewById(R.id.editTextTextPersonName8);
        mSpinner.setAdapter(getArrayAdapter());

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (mSpinner.getAdapter().getItem(i).equals("NewCategory")){
                    startActivityForResult(new Intent(MainActivity.this, NewCategoryActivity.class), 100);
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
            arr = new String[]{"", "NewCategory"};
        } else {
            arr = new String[2 + split.length];
            arr[0] = "";
            arr[1] = "NewCategory";

            for (int i = 0; i < split.length; i++) {
                arr[i + 2] = split[i];
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return adapter;
    }

    public void newCategory(View view) {

    }

    public void Create(View view) {
        if (mSpinner.getSelectedItem() != null)
            LogUtils.showLog(mSpinner.getSelectedItem().toString());
    }
}