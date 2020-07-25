package com.example.pomodoro_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;



public class NewCategoryActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);
        mEtName = findViewById(R.id.et_name);
        findViewById(R.id.bt_back).setOnClickListener(this);
        findViewById(R.id.bt_done).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_back:
                break;
            case R.id.bt_done:
                String name = mEtName.getText().toString().trim();
                if (name.isEmpty()) {
                    LogUtils.showToast(this, "Please enter the category");
                    return;
                }
                SharedPreferences sharedPreferences = getSharedPreferences("timer", MODE_PRIVATE);
                String category = sharedPreferences.getString("category", "");
                String[] split = category.split("-");
                if (category.isEmpty() || split == null || split.length == 0) {
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putString("category", name + "-");
                    edit.commit();
                } else {
                    String categoryList = "";
                    for (int i = 0; i < split.length; i++) {
                        String str = split[i];
                        if (str.isEmpty())
                            continue;
                        categoryList = categoryList + str + "-";
                        if (str.equals(name)) {
                            LogUtils.showToast(this, "Existing please try again");
                            return;
                        }
                    }
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putString("category", categoryList + name + "-");
                    edit.commit();
                }
                setResult(101);
                break;
        }

        finish();
    }
}
