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

public class MotionActivity extends Activity {
    private String TAG = "MotionActivity";

    private SensorManager mSensorManager;
    private Sensor mMotion = null;

    private Timer mUpdateTimer;

    TextView motion;

    private float mMotionData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "---MotionActivity onCreate");
        setContentView(R.layout.activity_motion);
        buildView();

        getSensorService();

        mUpdateTimer = new Timer("MotionActivity");
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
            mMotion = mSensorManager.getDefaultSensor(Sensor.TYPE_SIGNIFICANT_MOTION);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "---AccelActivity onResume");

        if (mMotion != null) {
            mSensorManager.registerListener(mListener, mMotion,
                    SensorManager.SENSOR_DELAY_UI);
        } else {
            mUpdateTimer.cancel();
            motion.setText("Device have not motion sensor.");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mMotion != null) {
            mSensorManager.unregisterListener(mListener, mMotion);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "---MotionActivity onDestroy");
        if (mUpdateTimer != null) {
            mUpdateTimer.cancel();
            mUpdateTimer = null;
        }
        if (mMotion != null) {
            mSensorManager.unregisterListener(mListener, mMotion);
            mMotion = null;
        }
    }

    private SensorEventListener mListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_SIGNIFICANT_MOTION) {
                mMotionData = event.values[0];
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
                motion.setText("Accel X: " + mMotionData);
            }
        });
    }

    private void buildView() {
        motion = (TextView) findViewById(R.id.motionText);
    }

}
