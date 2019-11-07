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

public class WakeupGyroActivity extends Activity {
    private String TAG = "WakeupGyroActivity";

    private SensorManager mSensorManager;
    private Sensor mWakeupGyro;

    private Timer mUpdateTimer;

    TextView xWakeupGyro;
    TextView yWakeupGyro;
    TextView zWakeupGyro;

    private float[] mWakeupGyroData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "---WakeupGyroActivity onCreate");
        setContentView(R.layout.activity_wakeup_gyro);
        buildView();

        mWakeupGyroData = new float[3];
        getSensorService();

        mUpdateTimer = new Timer("WakeupGyroActivity");
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
            mWakeupGyro = mSensorManager.getDefaultSensor(
                    Sensor.TYPE_GYROSCOPE, true);
        }

        if (mWakeupGyro != null) {
            mSensorManager.registerListener(mListener, mWakeupGyro,
                    SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "---WakeupGyroActivity onResume");

        if (mWakeupGyro == null) {
            mUpdateTimer.cancel();
            xWakeupGyro.setText("Device have not wake up gyro sensor.");
            yWakeupGyro.setText("");
            zWakeupGyro.setText("");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i(TAG, "---WakeupGyroActivity onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "---WakeupGyroActivity onDestroy");
        if (mUpdateTimer != null) {
            mUpdateTimer.cancel();
            mUpdateTimer = null;
        }
        if (mWakeupGyro != null) {
            mSensorManager.unregisterListener(mListener, mWakeupGyro);
            mWakeupGyro = null;
        }
    }

    private SensorEventListener mListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                mWakeupGyroData[0] = event.values[0];
                mWakeupGyroData[1] = event.values[1];
                mWakeupGyroData[2] = event.values[2];
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
                xWakeupGyro.setText("Wakeup Gyro X:\n" + mWakeupGyroData[0]);
                yWakeupGyro.setText("Wakeup Gyro Y:\n" + mWakeupGyroData[1]);
                zWakeupGyro.setText("Wakeup Gyro Z:\n" + mWakeupGyroData[2]);
            }
        });
    }

    private void buildView() {
        xWakeupGyro = (TextView) findViewById(R.id.xWakeupGyroText);
        yWakeupGyro = (TextView) findViewById(R.id.yWakeupGyroText);
        zWakeupGyro = (TextView) findViewById(R.id.zWakeupGyroText);
    }

}
