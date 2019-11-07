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

public class LightActivity extends Activity {
    private String TAG = "LightActivity";

    private SensorManager mSensorManager;
    private Sensor mLight = null;

    private Timer mUpdateTimer;

    TextView light;

    private float mLightData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "---LightActivity onCreate");
        setContentView(R.layout.activity_light);
        buildView();

        getSensorService();

        mUpdateTimer = new Timer("LightActivity");
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
            mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "---LightActivity onResume");

        if (mLight != null) {
            mSensorManager.registerListener(mListener, mLight,
                    SensorManager.SENSOR_DELAY_UI);
        } else {
            mUpdateTimer.cancel();
            light.setText("Device have not light sensor.");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mLight != null) {
            mSensorManager.unregisterListener(mListener, mLight);
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
        if (mLight != null) {
            mSensorManager.unregisterListener(mListener, mLight);
            mLight = null;
        }
    }

    private SensorEventListener mListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
                mLightData = event.values[0];
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
                light.setText("Light: " + mLightData);
            }
        });
    }

    private void buildView() {
        light = (TextView) findViewById(R.id.lightText);
    }

}
