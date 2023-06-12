package com.example.breathingdot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "BreathingDot";
    private static final int MSG_START_BREATHING = 1001;
    private static final int MSG_STOP_BREATHING = 1002;

    private static final int MSG_START_ALPHA_BREATH = 2001;
    private static final int MSG_STOP_ALPHA_BREATH = 2002;

    private Button breathButton;
    private static ImageView imageView;
    private static AnimationDrawable animationDrawable;

    private Button alphaButton;
    private static ImageView alphaImageView;
    private static AnimationDrawable alphaAnimationDrawable;
    private static int timeCounter = 0;
    private static final int MAX_ALPHA_VALUE = 255;
    private static final int MAX_TIME_COUNTER = 20;
    private static final int GRADIENT_DURATION = 700; // milliseconds

    private static Thread alphaThread;
    private static MyHandler handler;

    private static class MyHandler extends Handler {

        private WeakReference<MainActivity> mWeakReference;
        private String TAG = "MyHandler";

        public MyHandler(MainActivity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_START_BREATHING:
                    Log.i(TAG, "MSG_START_BREATHING");
                    startBreathing();
                    break;

                case MSG_STOP_BREATHING:
                    Log.i(TAG, "MSG_STOP_BREATHING");
                    stopBreathing();
                    break;

                case MSG_START_ALPHA_BREATH:
                    int count = msg.arg1;
                    Log.i(TAG, "MSG_START_ALPHA_BREATH, msg.arg1=" + count);
                    if (count == MAX_TIME_COUNTER) {
                        Log.i(TAG, "MSG_START_ALPHA_BREATH if");
                        timeCounter = 0;
                        stopAlphaBreath();

                    } else {
                        Log.i(TAG, "MSG_START_ALPHA_BREATH count=" + count);
                        startAlphaBreath(count);
                    }
                    break;

                case MSG_STOP_ALPHA_BREATH:
                    Log.i(TAG, "MSG_STOP_ALPHA_BREATH");
                    stopAlphaBreath();
                    break;

                default:
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getViews();
        getMyHandler(this);
    }

    private void getMyHandler(MainActivity activity) {
        handler = new MyHandler(activity);
    }

    private void getViews() {
        breathButton = findViewById(R.id.breath);
        breathButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.obtainMessage(MSG_START_BREATHING).sendToTarget();
            }
        });
        imageView = findViewById(R.id.img_view_redpoint);
        imageView.setImageDrawable(getDrawable(R.drawable.redpoint));
        imageView.setImageAlpha(120);
        imageView.setVisibility(View.GONE);
        animationDrawable = (AnimationDrawable) imageView.getDrawable();

        alphaButton = findViewById(R.id.alpha);
        alphaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.obtainMessage(MSG_START_ALPHA_BREATH).sendToTarget();
            }
        });
        alphaImageView = findViewById(R.id.img_view_alphapoint);
        alphaImageView.setImageDrawable(getDrawable(R.drawable.alphapoint));
        alphaImageView.setVisibility(View.GONE);
        alphaAnimationDrawable = (AnimationDrawable) alphaImageView.getDrawable();
    }

    private static void startBreathing() {
        Log.i(TAG, "startBreathing");
//        animationDrawable.setOneShot(true);
        imageView.setVisibility(View.VISIBLE);
        animationDrawable.start();
        scheduleStopDrawable(animationDrawable);
    }

    private static void stopBreathing() {
        Log.i(TAG, "stopBreathing");
        if (animationDrawable.isRunning()) {
            imageView.setVisibility(View.GONE);
            animationDrawable.stop();
        }
    }

    private static void scheduleStopDrawable(AnimationDrawable drawable) {
        Timer timer = new Timer("draw");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.obtainMessage(MSG_STOP_BREATHING).sendToTarget();
            }
        }, 1000);
    }

    private static void startAlphaBreath(int timeCounter) {
        Log.i(TAG, "timeCounter=" + timeCounter);

        int value = MAX_ALPHA_VALUE * timeCounter / MAX_TIME_COUNTER;
        alphaImageView.setVisibility(View.VISIBLE);
        alphaImageView.setImageAlpha(value);

        Timer timer = new Timer("alphaTimer");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                int value = timeCounter + 1;
                Log.i("MyThread", "timecounter=" + timeCounter + ", value=" + value);
                handler.obtainMessage(MSG_START_ALPHA_BREATH, value, 0).sendToTarget();
            }
        }, GRADIENT_DURATION / MAX_TIME_COUNTER);
    }

    private static void stopAlphaBreath() {
        Log.i(TAG, "stopAlphaBreath");
        alphaImageView.setVisibility(View.GONE);
    }
}
