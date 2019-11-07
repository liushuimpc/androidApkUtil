package com.dong.compasslevel;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.math.BigDecimal;

public class LevelActivity extends AppCompatActivity {
    private final static String TAG = "LevelActivity";

    private final static int MAX_ANGLE = 30;
    private final static float transformFormula = (float) (180 / Math.PI);

    private final static int MSG_UPDATE_ORIENTATION = 1001;

    private LevelView show;

    private SensorManager sensorManager;
    private Sensor accelSensor;
    private Sensor magnSensor;

    private float[] mAccelData;
    private float[] mMagnData;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_UPDATE_ORIENTATION:
                    handleData();
                    break;

                default:
                    break;
            }
        }
    };

    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {

            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                mAccelData = event.values;

            } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                mMagnData = event.values;
            }

            mHandler.obtainMessage(MSG_UPDATE_ORIENTATION).sendToTarget();
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        buildPrivateData();
        buildView();
        getService();
    }

    private void buildPrivateData() {
        mAccelData = new float[3];
        mMagnData = new float[3];
    }

    private void buildView() {
        show = (LevelView) findViewById(R.id.level_view);
    }

    private void getService() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnSensor = sensorManager.getDefaultSensor(Sensor
                .TYPE_MAGNETIC_FIELD);
    }

    protected void onResume() {
        super.onResume();

        Log.i(TAG, "marco----------------onResume");
        sensorManager.registerListener(listener, accelSensor, SensorManager
                .SENSOR_DELAY_GAME);
        sensorManager.registerListener(listener, magnSensor, SensorManager
                .SENSOR_DELAY_GAME);
    }

    protected void onPause() {
        sensorManager.unregisterListener(listener);
        super.onPause();
    }

    protected void onStop() {
        sensorManager.unregisterListener(listener);
        super.onStop();
    }

    private void handleData() {
        if (mAccelData == null || mMagnData == null)
            return;

        BigDecimal zero = new BigDecimal(0);
        float[] values = new float[3];
        float[] R = new float[9];

        SensorManager.getRotationMatrix(R, null, mAccelData, mMagnData);
        SensorManager.getOrientation(R, values);

        float yAngle = values[1] * transformFormula;
        float zAngle = values[2] * transformFormula;
        int x = (show.back.getWidth() - show.bubble.getWidth()) / 2;
        int y = (show.back.getHeight() - show.bubble.getHeight()) / 2;

        if (Math.abs(zAngle) <= MAX_ANGLE) {
            int deltaX = (int) ((show.back.getWidth() - show.bubble.getWidth
                    ()) / 2 * zAngle / MAX_ANGLE);
            x -= deltaX;

        } else if (zAngle > MAX_ANGLE) {
            x = 0;

        } else {
            x = show.back.getWidth() - show.bubble.getWidth();
        }

        if (Math.abs(yAngle) <= MAX_ANGLE) {
            int deltaY = (int) ((show.back.getHeight() - show.bubble
                    .getHeight()) / 2 * yAngle / MAX_ANGLE);
            y += deltaY;

        } else if (yAngle > MAX_ANGLE) {
            y = show.back.getHeight() - show.bubble.getHeight();

        } else {
            y = 0;
        }

        if (isContain(x, y)) {
            show.bubbleX = x;
            show.bubbleY = y;
        }

        show.postInvalidate();
    }

    private boolean isContain(int x, int y) {
        int bubbleCenterX = x + show.bubble.getWidth() / 2;
        int bubbleCenterY = y + show.bubble.getHeight() / 2;

        int backCenterX = show.back.getWidth() / 2;
        int backCenterY = show.back.getHeight() / 2;

        double distance = Math.sqrt((bubbleCenterX - backCenterX) *
                (bubbleCenterX - backCenterX) + (bubbleCenterY - backCenterY)
                * (bubbleCenterY - backCenterY));

        if (distance < (show.back.getWidth() - show.bubble.getWidth()) / 2) {
            return true;

        } else {
            return false;
        }
    }
}
