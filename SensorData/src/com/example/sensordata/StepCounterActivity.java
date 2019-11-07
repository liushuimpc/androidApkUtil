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

public class StepCounterActivity extends Activity {
    private String TAG = "StepCounterActivity";

    private SensorManager mSensorManager;
    private Sensor mStepCounter = null;

    private Timer mUpdateTimer;

    TextView StepCounter;

    private float mStepCounterData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "---StepCounterActivity onCreate");
        setContentView(R.layout.activity_step_counter);
        buildView();

        getSensorService();

        mUpdateTimer = new Timer("StepCounterActivity");
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
            mStepCounter = mSensorManager
                    .getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "---StepCounterActivity onResume");

        if (mStepCounter != null) {
            mSensorManager.registerListener(mListener, mStepCounter,
                    SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            mUpdateTimer.cancel();
            StepCounter.setText("Device have not step counter sensor.");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mStepCounter != null) {
            mSensorManager.unregisterListener(mListener, mStepCounter);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "---StepCounterActivity onDestroy");
        if (mUpdateTimer != null) {
            mUpdateTimer.cancel();
            mUpdateTimer = null;
        }
        if (mStepCounter != null) {
            mSensorManager.unregisterListener(mListener, mStepCounter);
            mStepCounter = null;
        }
    }

    private SensorEventListener mListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
                mStepCounterData = event.values[0];
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
                StepCounter.setText("StepCounter: " + mStepCounterData);
            }
        });
    }

    private void buildView() {
        StepCounter = (TextView) findViewById(R.id.StepCounterText);
    }

}
