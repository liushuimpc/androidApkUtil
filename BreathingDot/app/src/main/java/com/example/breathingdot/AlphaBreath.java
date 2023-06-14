package com.example.breathingdot;

import static com.example.breathingdot.MainActivity.MSG_DARKEN_ALPHA_BREATH;
import static com.example.breathingdot.MainActivity.MSG_START_ALPHA_BREATH;
import static com.example.breathingdot.MainActivity.MSG_STOP_ALPHA_BREATH;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class AlphaBreath {
    private static final String TAG = "AlphaBreath";
    public static final int START_ALPHA_VALUE = 30;
    public static final int MAX_ALPHA_VALUE = 170; // 255
    public static final int MAX_TIME_COUNTER = 30;
    public static final int MIN_TIME_COUNTER = 0;
    public static final int LIGHTEN_GRADIENT_DURATION = 350; // milliseconds, but not accurate
    public static final int DARKEN_GRADIENT_DURATION = 700; // milliseconds, but not accurate

    private Handler handler;
    private ImageView imageView;

    public AlphaBreath(Handler h, ImageView v) {
        handler = h;
        imageView = v;
    }

    public void startAlphaBreath(int counter) {
        Log.i(TAG, "timeCounter=" + counter);

        int value = (MAX_ALPHA_VALUE - START_ALPHA_VALUE) * counter / MAX_TIME_COUNTER
                + START_ALPHA_VALUE;
        Log.i(TAG, "value=" + value);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageAlpha(value);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.obtainMessage(MSG_START_ALPHA_BREATH, counter + 1, 0).sendToTarget();
            }
        }, LIGHTEN_GRADIENT_DURATION / MAX_TIME_COUNTER);
    }

    public void darkenAlphaBreath(int counter) {
        Log.i(TAG, "darkenAlphaBreath =" + counter);

        int value = MAX_ALPHA_VALUE * counter / MAX_TIME_COUNTER;
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageAlpha(value);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.obtainMessage(MSG_DARKEN_ALPHA_BREATH, counter - 1 , 0).sendToTarget();
            }
        }, DARKEN_GRADIENT_DURATION / MAX_TIME_COUNTER);
    }

    public void stopAlphaBreath() {
        Log.i(TAG, "stopAlphaBreath");
        imageView.setVisibility(View.GONE);
    }
}
