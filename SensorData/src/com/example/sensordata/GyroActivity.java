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

public class GyroActivity extends Activity {
    private String TAG = "GyroActivity";

    private SensorManager mSensorManager;
    private Sensor mGyro = null;

    private Timer mUpdateTimer;

    TextView xGyro;
    TextView yGyro;
    TextView zGyro;

    private float[] mGyroData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "---GyroActivity onCreate");
        setContentView(R.layout.activity_gyro);
        buildView();

        mGyroData = new float[3];
        getSensorService();

        mUpdateTimer = new Timer("GyroActivity");
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
            mGyro = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "---GyroActivity onResume");

        if (mGyro != null) {
            mSensorManager.registerListener(mListener, mGyro,
                    SensorManager.SENSOR_DELAY_UI);
        } else {
            mUpdateTimer.cancel();
            xGyro.setText("Device have not gyro sensor.");
            yGyro.setText("");
            zGyro.setText("");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mGyro != null) {
            mSensorManager.unregisterListener(mListener, mGyro);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "---GyroActivity onDestroy");
        if (mUpdateTimer != null) {
            mUpdateTimer.cancel();
            mUpdateTimer = null;
        }
        if (mGyro != null) {
            mSensorManager.unregisterListener(mListener, mGyro);
            mGyro = null;
        }
    }

    private SensorEventListener mListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                mGyroData[0] = event.values[0];
                mGyroData[1] = event.values[1];
                mGyroData[2] = event.values[2];
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
                xGyro.setText("Gyro X: " + mGyroData[0]);
                yGyro.setText("Gyro Y: " + mGyroData[1]);
                zGyro.setText("Gyro Z: " + mGyroData[2]);
            }
        });
    }

    private void buildView() {
        xGyro = (TextView) findViewById(R.id.xGyroText);
        yGyro = (TextView) findViewById(R.id.yGyroText);
        zGyro = (TextView) findViewById(R.id.zGyroText);
    }

}
