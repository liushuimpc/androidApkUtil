package com.example.breathingdot;

import static com.example.breathingdot.MainActivity.MSG_STOP_SIZE_BREATH;

import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class SizeBreath {
    private static final String TAG = "SizeBreath";
    // Total expand gradient duration, it might keep the last state in the last seconds.
    private static final int EXPAND_GRADIENT_DURATION = 800; // milliseconds
    private static final int DEFAULT_ALPHA_VALUE = 170; // 0-255

    private Handler handler;
    private ImageView imageView;
    private AnimationDrawable animationDrawable;

    public SizeBreath(Handler h, ImageView v) {
        handler = h;
        imageView = v;
        animationDrawable = (AnimationDrawable) imageView.getDrawable();

        init();
    }

    private void init() {
        imageView.setImageAlpha(DEFAULT_ALPHA_VALUE);
    }

    public void startSizeBreath() {
        Log.i(TAG, "startSizeBreath");
//        animationDrawable.setOneShot(true);
        imageView.setVisibility(View.VISIBLE);
        animationDrawable.start();
        scheduleStopDrawable(animationDrawable);
    }

    public void stopSizeBreath() {
        Log.i(TAG, "stopSizeBreath");
        if (animationDrawable.isRunning()) {
            imageView.setVisibility(View.GONE);
            animationDrawable.stop();
        }
    }

    public void scheduleStopDrawable(AnimationDrawable drawable) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.obtainMessage(MSG_STOP_SIZE_BREATH).sendToTarget();
            }
        }, EXPAND_GRADIENT_DURATION);
    }
}
