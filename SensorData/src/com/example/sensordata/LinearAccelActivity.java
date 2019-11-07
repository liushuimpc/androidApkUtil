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

public class LinearAccelActivity extends Activity {
    private String TAG = "LinearAccelActivity";

    private SensorManager mSensorManager;
    private Sensor mLinearAccel = null;

    private Timer mUpdateTimer;

    TextView xLinearAccel;
    TextView yLinearAccel;
    TextView zLinearAccel;

    private float[] mLinearAccelData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "---LinearAccelActivity onCreate");
        setContentView(R.layout.activity_linear_accel);
        buildView();

        mLinearAccelData = new float[3];
        getSensorService();

        mUpdateTimer = new Timer("LinearAccelActivity");
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
            mLinearAccel = mSensorManager
                    .getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "---LinearAccelActivity onResume");

        if (mLinearAccel != null) {
            mSensorManager.registerListener(mListener, mLinearAccel,
                    SensorManager.SENSOR_DELAY_UI);
        } else {
            mUpdateTimer.cancel();
            xLinearAccel.setText("Device have not linear accel sensor.");
            yLinearAccel.setText("");
            zLinearAccel.setText("");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mLinearAccel != null) {
            mSensorManager.unregisterListener(mListener, mLinearAccel);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "---LinearAccelActivity onDestroy");
        if (mUpdateTimer != null) {
            mUpdateTimer.cancel();
            mUpdateTimer = null;
        }
        if (mLinearAccel != null) {
            mSensorManager.unregisterListener(mListener, mLinearAccel);
            mLinearAccel = null;
        }
    }

    private SensorEventListener mListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
                mLinearAccelData[0] = event.values[0];
                mLinearAccelData[1] = event.values[1];
                mLinearAccelData[2] = event.values[2];
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
                xLinearAccel.setText("Accel X: " + mLinearAccelData[0]);
                yLinearAccel.setText("Accel Y: " + mLinearAccelData[1]);
                zLinearAccel.setText("Accel Z: " + mLinearAccelData[2]);
            }
        });
    }

    private void buildView() {
        xLinearAccel = (TextView) findViewById(R.id.xLinearAccelText);
        yLinearAccel = (TextView) findViewById(R.id.yLinearAccelText);
        zLinearAccel = (TextView) findViewById(R.id.zLinearAccelText);
    }

}
