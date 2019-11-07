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

public class AccelActivity extends Activity {
    private String TAG = "AccelActivity";

    private SensorManager mSensorManager;
    private Sensor mAccel = null;

    private Timer mUpdateTimer;

    TextView xAccel;
    TextView yAccel;
    TextView zAccel;

    private float[] mAccelData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "---AccelActivity onCreate");
        setContentView(R.layout.activity_accel);
        buildView();

        mAccelData = new float[3];
        getSensorService();

        mUpdateTimer = new Timer("AccelActivity");
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
            mAccel = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "---AccelActivity onResume");

        if (mAccel != null) {
            mSensorManager.registerListener(mListener, mAccel,
                    SensorManager.SENSOR_DELAY_UI);
        } else {
            mUpdateTimer.cancel();
            xAccel.setText("Device have not accel sensor.");
            yAccel.setText("");
            zAccel.setText("");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mAccel != null) {
            mSensorManager.unregisterListener(mListener, mAccel);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "---AccelActivity onDestroy");
        if (mUpdateTimer != null) {
            mUpdateTimer.cancel();
            mUpdateTimer = null;
        }
        if (mAccel != null) {
            mSensorManager.unregisterListener(mListener, mAccel);
            mAccel = null;
        }
    }

    private SensorEventListener mListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                mAccelData[0] = event.values[0];
                mAccelData[1] = event.values[1];
                mAccelData[2] = event.values[2];
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
                xAccel.setText("Accel X: " + mAccelData[0]);
                yAccel.setText("Accel Y: " + mAccelData[1]);
                zAccel.setText("Accel Z: " + mAccelData[2]);
            }
        });
    }

    private void buildView() {
        xAccel = (TextView) findViewById(R.id.xAccelText);
        yAccel = (TextView) findViewById(R.id.yAccelText);
        zAccel = (TextView) findViewById(R.id.zAccelText);
    }

}
