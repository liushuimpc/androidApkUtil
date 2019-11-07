package com.dong.compasslevel;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import java.math.BigDecimal;

public class CompassActivity extends AppCompatActivity {
    private final static String TAG = "CompassActivity";

    private final static float defaultRotation = 135;
    private final static float transformFormula = (float) (180 / Math.PI);

    private final static int MSG_UPDATE_ORIENTATION = 1001;

    private ImageView imageView;

    private SensorManager sensorManager;
    private Sensor accelSensor;
    private Sensor magnSensor;

    private float currentDegree;
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
        setContentView(R.layout.activity_compass);

        buildPrivateData();
        buildView();
        getService();
    }

    private void buildPrivateData() {
        mAccelData = new float[3];
        mMagnData = new float[3];
    }

    private void buildView() {
        imageView = (ImageView) findViewById(R.id.compass_image_view);
    }

    private void getService() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnSensor = sensorManager.getDefaultSensor(Sensor
                .TYPE_MAGNETIC_FIELD);
    }

    protected void onResume() {
        super.onResume();

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

        float[] values = new float[3];
        float[] R = new float[9];

        SensorManager.getRotationMatrix(R, null, mAccelData, mMagnData);
        SensorManager.getOrientation(R, values);

        BigDecimal x = new BigDecimal(values[0]);
        x = x.setScale(12, BigDecimal.ROUND_HALF_UP);
//        Log.i(TAG, "ORIE X: " + ((x.compareTo(zero) != -1) ? "+" : " ") + x);

        BigDecimal y = new BigDecimal(values[1]);
        y = y.setScale(12, BigDecimal.ROUND_HALF_UP);
//        Log.i(TAG, "ORIE Y: " + ((y.compareTo(zero) != -1) ? "+" : " ") + y);

        BigDecimal z = new BigDecimal(values[2]);
        z = z.setScale(12, BigDecimal.ROUND_HALF_UP);
//        Log.i(TAG, "ORIE Z: " + ((z.compareTo(zero) != -1) ? "+" : " ") + z);

        float degree = values[0] * transformFormula;
        RotateAnimation rotateAnimation = new RotateAnimation(currentDegree,
                -degree + defaultRotation, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(50);
        imageView.startAnimation(rotateAnimation);
        currentDegree = -degree + defaultRotation;
    }
}

