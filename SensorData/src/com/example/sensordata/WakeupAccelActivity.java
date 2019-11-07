package com.example.sensordata;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class WakeupAccelActivity extends Activity {
    private String TAG = "WakeupAccelActivity";

    private SensorManager mSensorManager;
    private Sensor mWakeupAccel = null;

    private Timer mUpdateTimer;

    TextView xWakeupAccel;
    TextView yWakeupAccel;
    TextView zWakeupAccel;

    private float[] mWakeupAccelData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "---WakeupAccelActivity onCreate");
        setContentView(R.layout.activity_wakeup_accel);
        buildView();

        mWakeupAccelData = new float[3];
        getSensorService();

        mUpdateTimer = new Timer("WakeupAccelActivity");
        if (mUpdateTimer == null) {
            Log.e(TAG, "new Timer failed!!");
            return;
        }

        mUpdateTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateUI();
            }
        }, 100, 100);
    }

    private void getSensorService() {
        // TODO Auto-generated method stub
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (mSensorManager == null) {
            Log.e(TAG, "----getSystemService failed");

        } else {
            mWakeupAccel = mSensorManager.getDefaultSensor(
                    Sensor.TYPE_ACCELEROMETER, true);
        }

        if (mWakeupAccel != null) {
            mSensorManager.registerListener(mListener, mWakeupAccel,
                    SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "---WakeupAccelActivity onResume");

        if (mWakeupAccel == null) {
            mUpdateTimer.cancel();
            xWakeupAccel.setText("Device have not wake up accel sensor.");
            yWakeupAccel.setText("");
            zWakeupAccel.setText("");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i(TAG, "---WakeupAccelActivity onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "---WakeupAccelActivity onDestroy");
        if (mUpdateTimer != null) {
            mUpdateTimer.cancel();
            mUpdateTimer = null;
        }
        if (mWakeupAccel != null) {
            mSensorManager.unregisterListener(mListener, mWakeupAccel);
            mWakeupAccel = null;
        }
    }

    private SensorEventListener mListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                mWakeupAccelData[0] = event.values[0];
                mWakeupAccelData[1] = event.values[1];
                mWakeupAccelData[2] = event.values[2];
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub

        }
    };

    protected void updateUI() {
        // TODO Auto-generated method stub
        this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                xWakeupAccel.setText("Wakeup Accel X:\n" + mWakeupAccelData[0]);
                yWakeupAccel.setText("Wakeup Accel Y:\n" + mWakeupAccelData[1]);
                zWakeupAccel.setText("Wakeup Accel Z:\n" + mWakeupAccelData[2]);
            }
        });
    }

    private void buildView() {
        xWakeupAccel = (TextView) findViewById(R.id.xWakeupAccelText);
        yWakeupAccel = (TextView) findViewById(R.id.yWakeupAccelText);
        zWakeupAccel = (TextView) findViewById(R.id.zWakeupAccelText);
    }

}
