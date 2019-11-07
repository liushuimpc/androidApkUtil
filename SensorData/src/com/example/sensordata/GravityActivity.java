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

public class GravityActivity extends Activity {
    private String TAG = "GravityActivity";

    private SensorManager mSensorManager;
    private Sensor mGravity = null;

    private Timer mUpdateTimer;

    TextView xGravity;
    TextView yGravity;
    TextView zGravity;

    private float[] mGravityData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "---GravityActivity onCreate");
        setContentView(R.layout.activity_gravity);
        buildView();

        mGravityData = new float[3];
        getSensorService();

        mUpdateTimer = new Timer("GravityActivity");
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
            mGravity = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "---GravityActivity onResume");

        if (mGravity != null) {
            mSensorManager.registerListener(mListener, mGravity,
                    SensorManager.SENSOR_DELAY_UI);
        } else {
            mUpdateTimer.cancel();
            xGravity.setText("Device have not gravity sensor.");
            yGravity.setText("");
            zGravity.setText("");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mGravity != null) {
            mSensorManager.unregisterListener(mListener, mGravity);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "---GravityActivity onDestroy");
        if (mUpdateTimer != null) {
            mUpdateTimer.cancel();
            mUpdateTimer = null;
        }
        if (mGravity != null) {
            mSensorManager.unregisterListener(mListener, mGravity);
            mGravity = null;
        }
    }

    private SensorEventListener mListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {
                mGravityData[0] = event.values[0];
                mGravityData[1] = event.values[1];
                mGravityData[2] = event.values[2];
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
                xGravity.setText("Gravity X: " + mGravityData[0]);
                yGravity.setText("Gravity Y: " + mGravityData[1]);
                zGravity.setText("Gravity Z: " + mGravityData[2]);
            }
        });
    }

    private void buildView() {
        xGravity = (TextView) findViewById(R.id.xGravityText);
        yGravity = (TextView) findViewById(R.id.yGravityText);
        zGravity = (TextView) findViewById(R.id.zGravityText);
    }

}
