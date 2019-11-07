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

public class UncalibratedGyroActivity extends Activity {
    private String TAG = "UncalibratedGyroActivity";

    private SensorManager mSensorManager;
    private Sensor mUncalibratedGyro = null;

    private Timer mUpdateTimer;

    TextView xUncalibratedGyro;
    TextView yUncalibratedGyro;
    TextView zUncalibratedGyro;

    private float[] mUncalibratedGyroData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "---UncalibratedGyroActivity onCreate");
        setContentView(R.layout.activity_uncalibrated_gyro);
        buildView();

        mUncalibratedGyroData = new float[3];
        getSensorService();

        mUpdateTimer = new Timer("UncalibratedGyroActivity");
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
            mUncalibratedGyro = mSensorManager
                    .getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "----come in onResume");

        if (mUncalibratedGyro != null) {
            mSensorManager.registerListener(mListener, mUncalibratedGyro,
                    SensorManager.SENSOR_DELAY_UI);
        } else {
            mUpdateTimer.cancel();
            xUncalibratedGyro.setText("Device have not uncalibrated gyro sensor.");
            yUncalibratedGyro.setText("");
            zUncalibratedGyro.setText("");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mUncalibratedGyro != null) {
            mSensorManager.unregisterListener(mListener, mUncalibratedGyro);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "---UncalibratedGyroActivity onDestroy");
        if (mUpdateTimer != null) {
            mUpdateTimer.cancel();
            mUpdateTimer = null;
        }
        if (mUncalibratedGyro != null) {
            mSensorManager.unregisterListener(mListener, mUncalibratedGyro);
            mUncalibratedGyro = null;
        }
    }

    private SensorEventListener mListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE_UNCALIBRATED) {
                mUncalibratedGyroData[0] = event.values[0];
                mUncalibratedGyroData[1] = event.values[1];
                mUncalibratedGyroData[2] = event.values[2];
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
                xUncalibratedGyro.setText("Uncalibrated Gyro X:\n"
                        + mUncalibratedGyroData[0]);
                yUncalibratedGyro.setText("Uncalibrated Gyro Y:\n"
                        + mUncalibratedGyroData[1]);
                zUncalibratedGyro.setText("Uncalibrated Gyro Z:\n"
                        + mUncalibratedGyroData[2]);
            }
        });
    }

    private void buildView() {
        xUncalibratedGyro = (TextView) findViewById(R.id.xUncalibratedGyroText);
        yUncalibratedGyro = (TextView) findViewById(R.id.yUncalibratedGyroText);
        zUncalibratedGyro = (TextView) findViewById(R.id.zUncalibratedGyroText);
    }

}
