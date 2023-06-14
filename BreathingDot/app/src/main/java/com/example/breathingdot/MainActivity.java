package com.example.breathingdot;

import static com.example.breathingdot.AlphaBreath.MAX_TIME_COUNTER;
import static com.example.breathingdot.AlphaBreath.MIN_TIME_COUNTER;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "BreathingDot";
    public static final int MSG_START_SIZE_BREATH = 1001;
    public static final int MSG_STOP_SIZE_BREATH = 1002;

    public static final int MSG_START_ALPHA_BREATH = 2001;
    public static final int MSG_DARKEN_ALPHA_BREATH = 2002;
    public static final int MSG_STOP_ALPHA_BREATH = 2003;

    private static SizeBreath sizeBreath;
    private static ImageView sizeImageView;
    private Button sizeButton;

    private static AlphaBreath alphaBreath;
    private static ImageView alphaImageView;
    private Button alphaButton;

    private static MyHandler handler;

    private static class MyHandler extends Handler {
        private WeakReference<MainActivity> mWeakReference;
        private String TAG = "MyHandler";

        public MyHandler(MainActivity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            Log.i(TAG, "handleMessage, msg.what=" + msg.what);
            super.handleMessage(msg);

            switch (msg.what) {
                case MSG_START_SIZE_BREATH:
                    sizeBreath.startSizeBreath();
                    break;

                case MSG_STOP_SIZE_BREATH:
                    sizeBreath.stopSizeBreath();
                    break;

                case MSG_START_ALPHA_BREATH:
                    int counter = msg.arg1;
                    Log.i(TAG, "MSG_START_ALPHA_BREATH, msg.arg1=" + counter);
                    if (counter == MAX_TIME_COUNTER) {
                        handler.obtainMessage(MSG_DARKEN_ALPHA_BREATH, counter, 0).sendToTarget();
                    } else {
                        alphaBreath.startAlphaBreath(counter);
                    }
                    break;

                case MSG_DARKEN_ALPHA_BREATH:
                    int darkenCounter = msg.arg1;
                    Log.i(TAG, "MSG_DARKEN_ALPHA_BREATH, msg.arg1=" + darkenCounter);
                    if (darkenCounter == MIN_TIME_COUNTER) {
                        alphaBreath.stopAlphaBreath();
                    } else {
                        alphaBreath.darkenAlphaBreath(darkenCounter);
                    }
                    break;

                case MSG_STOP_ALPHA_BREATH:
                    alphaBreath.stopAlphaBreath();
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
        initValuables();
    }

    private void getViews() {
        getSizeBreathViews();
        getAlphaBreathViews();
    }

    private void initValuables() {
        getMyHandler();
        sizeBreath = new SizeBreath(handler, sizeImageView);
        alphaBreath = new AlphaBreath(handler, alphaImageView);
    }

    private Handler getMyHandler() {
        if (handler == null) {
            handler = new MyHandler(this);
        }
        return handler;
    }

    private void getSizeBreathViews() {
        sizeButton = findViewById(R.id.size_breath);
        sizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.obtainMessage(MSG_START_SIZE_BREATH).sendToTarget();
            }
        });
        sizeImageView = findViewById(R.id.img_view_size_breath);
        sizeImageView.setImageDrawable(getDrawable(R.drawable.size_breath));
        sizeImageView.setVisibility(View.GONE);
    }

    private void getAlphaBreathViews() {
        alphaButton = findViewById(R.id.alpha_breath);
        alphaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "alphaButton clicked");
                handler.obtainMessage(MSG_START_ALPHA_BREATH, 0, 0).sendToTarget();
            }
        });
        alphaImageView = findViewById(R.id.img_view_alpha_breath);
        alphaImageView.setImageDrawable(getDrawable(R.drawable.alpha_breath));
        alphaImageView.setVisibility(View.GONE);
    }
}
