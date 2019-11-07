package com.example.wakupwhenlocked;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

public class WakeupActivity extends AppCompatActivity {
    private final static String TAG = "WakeupActivity";

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            WakeupActivity.this.finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true);
            setTurnScreenOn(true);
        } else {
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                            | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        }
        handler.removeMessages(0);
        handler.sendEmptyMessageDelayed(0, 1);
    }

    @Override
    public void finish() {
        super.finish();
    }
}
