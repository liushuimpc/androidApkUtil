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

public class WakeupStepCounterActivity extends Activity {
    private String TAG = "WakeupStepCounterActivity";

    private SensorManager mSensorManager;
    private Sensor mWakeupStepCounter = null;

    private Timer mUpdateTimer;

    TextView WakeupStepCounter;

    private float mWakeupStepCounterData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "---WakeupStepCounterActivity onCreate");
        setContentView(R.layout.activity_wakeup_step_counter);
        buildView();

        getSensorService();

        mUpdateTimer = new Timer("WakeupStepCounterActivity");
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
            mWakeupStepCounter = mSensorManager.getDefaultSensor(
                    Sensor.TYPE_STEP_COUNTER, true);
        }

        if (mWakeupStepCounter != null) {
            mSensorManager.registerListener(mListener, mWakeupStepCounter,
                    SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "---WakeupStepCounterActivity onResume");

        if (mWakeupStepCounter == null) {
            mUpdateTimer.cancel();
            WakeupStepCounter.setText("Device have not wake up step counter sensor.");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i(TAG, "---WakeupStepCounterActivity  onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "---WakeupStepCounterActivity onDestroy");
        if (mUpdateTimer != null) {
            mUpdateTimer.cancel();
            mUpdateTimer = null;
        }
        if (mWakeupStepCounter != null) {
            mSensorManager.unregisterListener(mListener, mWakeupStepCounter);
            mWakeupStepCounter = null;
        }
    }

    private SensorEventListener mListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
                mWakeupStepCounterData = event.values[0];
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
                WakeupStepCounter.setText("WakeupStepCounter: "
                        + mWakeupStepCounterData);
            }
        });
    }

    private void buildView() {
        WakeupStepCounter = (TextView) findViewById(R.id.WakeupStepCounterText);
    }

}
