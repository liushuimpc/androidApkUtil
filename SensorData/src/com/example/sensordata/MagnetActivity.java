package com.example.sensordata;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.example.sensordata.utils.RecordFile;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MagnetActivity extends Activity {
	private String TAG = "MagnetActivity";
	private String fileName1 = "magnetic_x_y.txt";
	private String fileName2 = "magnetic_x_z.txt";
	private String fileName3 = "magnetic_y_z.txt";

	// private String sdFileName1 = "/sdcard/magnetic_x_y.txt";
	// private String sdFileName2 = "/sdcard/magnetic_x_z.txt";
	// private String sdFileName3 = "/sdcard/magnetic_y_z.txt";

	private SensorManager mSensorManager;
	private Sensor mMagnet = null;

	private Timer mUpdateTimer;

	TextView xMagnet;
	TextView yMagnet;
	TextView zMagnet;

	private float[] mMagnetData;
	private double mVectorData;

	private RecordFile recordFile1;
	private RecordFile recordFile2;
	private RecordFile recordFile3;

	private PowerManager powerManager;
	private WakeLock wakeLock;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.i(TAG, "---MagnetActivity onCreate");
		setContentView(R.layout.activity_magnet);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		buildView();
		getLock();
		buildPrivateData();

		mMagnetData = new float[3];
		getSensorService();

		mUpdateTimer = new Timer("MagnetActivity");
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

	private void getLock() {
		powerManager = (PowerManager) getSystemService(POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
				"MagneticActivity");
		wakeLock.acquire();
	}

	private void buildPrivateData() {
		recordFile1 = new RecordFile(this, fileName1);
		recordFile2 = new RecordFile(this, fileName2);
		recordFile3 = new RecordFile(this, fileName3);
	}

	private void getSensorService() {
		// TODO Auto-generated method stub
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		if (mSensorManager == null) {
			Log.e(TAG, "----getSystemService failed");

		} else {
			Log.e(TAG, "000000000000000000000000000000000000");
			mMagnet = mSensorManager
					.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.i(TAG, "---MagnetActivity onResume");

		if (mMagnet != null) {
			Log.e(TAG, "11111111111111111111111111111111111111");
			mSensorManager.registerListener(mListener, mMagnet,
					SensorManager.SENSOR_DELAY_UI);
		} else {
			Log.e(TAG, "22222222222222222222222222222222222222");
			mUpdateTimer.cancel();
			xMagnet.setText("Device have not magnetic sensor.");
			yMagnet.setText("");
			zMagnet.setText("");
		}
	}

	@Override
	protected void onPause() {
		super.onPause();

		if (mMagnet != null) {
			mSensorManager.unregisterListener(mListener, mMagnet);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		Log.i(TAG, "---MagnetActivity onDestroy");
		if (mUpdateTimer != null) {
			mUpdateTimer.cancel();
			mUpdateTimer = null;
		}
		if (mMagnet != null) {
			mSensorManager.unregisterListener(mListener, mMagnet);
			mMagnet = null;
		}

		wakeLock.release();
		wakeLock = null;
		powerManager = null;
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	private SensorEventListener mListener = new SensorEventListener() {

		@Override
		public void onSensorChanged(SensorEvent event) {
			if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
				mMagnetData[0] = event.values[0];
				mMagnetData[1] = event.values[1];
				mMagnetData[2] = event.values[2];
				Log.e(TAG, "marco--1x,y=" + mMagnetData[0] + "\t"
						+ mMagnetData[1]);

				Log.e(TAG, "marco--2x,z=" + mMagnetData[0] + "\t"
						+ mMagnetData[2]);

				Log.e(TAG, "marco--3y,z=" + mMagnetData[1] + "\t"
						+ mMagnetData[2]);

				if (recordFile1 != null && recordFile2 != null
						&& recordFile3 != null) {
					try {
						recordFile1.record(mMagnetData[0] + "\t"
								+ mMagnetData[1] + "\n");
						recordFile2.record(mMagnetData[0] + "\t"
								+ mMagnetData[2] + "\n");
						recordFile3.record(mMagnetData[1] + "\t"
								+ mMagnetData[2] + "\n");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Log.e(TAG, "marco-error");
					}
				}

				double tmp = Math.pow(mMagnetData[0], 2)
						+ Math.pow(mMagnetData[1], 2)
						+ Math.pow(mMagnetData[2], 2);

				mVectorData = Math.sqrt(tmp);
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
				if (mMagnetData != null) {
					xMagnet.setText("Magnet X: " + mMagnetData[0]);
					yMagnet.setText("Magnet Y: " + mMagnetData[1]);
					zMagnet.setText("Magnet Z: " + mMagnetData[2]
							+ "\nCount:    " + mVectorData);
				}
			}
		});
	}

	private void buildView() {
		xMagnet = (TextView) findViewById(R.id.xMagnetText);
		yMagnet = (TextView) findViewById(R.id.yMagnetText);
		zMagnet = (TextView) findViewById(R.id.zMagnetText);
	}

}
