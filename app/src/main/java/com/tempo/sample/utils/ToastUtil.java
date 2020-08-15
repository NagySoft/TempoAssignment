package com.tempo.sample.utils;

import android.content.Context;
import android.widget.Toast;

import static android.widget.Toast.makeText;
import static com.tempo.sample.TempoApplication.getContext;

public class ToastUtil {

    public static void showToast(String msg) {
        makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    public static void showToast(int msgId) {
        makeText(getContext(), msgId, Toast.LENGTH_LONG).show();
    }

    public static void showToast(Context context, String msg) {
        makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void showToast(Context context, int msgId) {
        makeText(context, msgId, Toast.LENGTH_LONG).show();
    }
}
