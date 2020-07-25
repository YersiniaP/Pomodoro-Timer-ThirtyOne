package com.example.pomodoro_app;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class LogUtils {

    private static Toast mToast;

    public static void showLog(String msg) {
        Log.d("Test", msg);
    }

    public static void showToast(Context context, String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }

    public static String getString(String str) {
        if (str == null) {
            return "";
        }

        return str;
    }
}
