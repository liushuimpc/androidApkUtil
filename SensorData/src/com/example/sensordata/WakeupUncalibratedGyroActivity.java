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

public class WakeupUncalibratedGyroActivity extends Activity {
    private String TAG = "WakeupUncalibratedGyroActivity";

    private SensorManager mSensorManager;
    private Sensor mWakeupUncalibratedGyro = null;

    private Timer mUpdateTimer;

    TextView xWakeupUncalibratedGyro;
    TextView yWakeupUncalibratedGyro;
    TextView zWakeupUncalibratedGyro;

    private float[] mWakeupUncalibratedGyroData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "---WakeupUncalibratedGyroActivity onCreate");
        setContentView(R.layout.activity_wakeup_uncalibrated_gyro);
        buildView();

        mWakeupUncalibratedGyroData = new float[3];
        getSensorService();

        mUpdateTimer = new Timer("WakeupUncalibratedGyroActivity");
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
            mWakeupUncalibratedGyro = mSensorManager.getDefaultSensor(
                    Sensor.TYPE_GYROSCOPE_UNCALIBRATED, true);
        }

        if (mWakeupUncalibratedGyro != null) {
            mSensorManager.registerListener(mListener, mWakeupUncalibratedGyro,
                    SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "---WakeupUncalibratedGyroActivity onResume");

        if (mWakeupUncalibratedGyro == null) {
            mUpdateTimer.cancel();
            xWakeupUncalibratedGyro
                    .setText("Device have not wake up uncalibrated gyro sensor.");
            yWakeupUncalibratedGyro.setText("");
            zWakeupUncalibratedGyro.setText("");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i(TAG, "---WakeupUncalibratedGyroActivity onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "---WakeupGyroActivity onDestroy");
        if (mUpdateTimer != null) {
            mUpdateTimer.cancel();
            mUpdateTimer = null;
        }
        if (mWakeupUncalibratedGyro != null) {
            mSensorManager.unregisterListener(mListener,
                    mWakeupUncalibratedGyro);
            mWakeupUncalibratedGyro = null;
        }
    }

    private SensorEventListener mListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE_UNCALIBRATED) {
                mWakeupUncalibratedGyroData[0] = event.values[0];
                mWakeupUncalibratedGyroData[1] = event.values[1];
                mWakeupUncalibratedGyroData[2] = event.values[2];
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
                xWakeupUncalibratedGyro.setText("Wakeup Uncalibrated Gyro X:\n"
                        + mWakeupUncalibratedGyroData[0]);
                yWakeupUncalibratedGyro.setText("Wakeup Uncalibrated Gyro Y:\n"
                        + mWakeupUncalibratedGyroData[1]);
                zWakeupUncalibratedGyro.setText("Wakeup Uncalibrated Gyro Z:\n"
                        + mWakeupUncalibratedGyroData[2]);
            }
        });
    }

    private void buildView() {
        xWakeupUncalibratedGyro = (TextView) findViewById(R.id.xWakeupUncalibratedGyroText);
        yWakeupUncalibratedGyro = (TextView) findViewById(R.id.yWakeupUncalibratedGyroText);
        zWakeupUncalibratedGyro = (TextView) findViewById(R.id.zWakeupUncalibratedGyroText);
    }

}
