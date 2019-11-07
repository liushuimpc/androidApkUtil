package com.example.wakupwhenlocked;

import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "WakeupWhenLocked";

    private static final Instrumentation INSTRUMENTATION = new
            Instrumentation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        runTest();
    }

    private void runTest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                INSTRUMENTATION.sendKeyDownUpSync(KeyEvent.KEYCODE_POWER);
                delayWakeupThread(5000);
            }
        }).start();

    }

    private void delayWakeupThread(final int milliseconds) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(milliseconds);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent myIntent = new Intent(MainActivity.this,
                        WakeupActivity.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                MainActivity.this.startActivity(myIntent);
            }
        }).start();
    }
}

