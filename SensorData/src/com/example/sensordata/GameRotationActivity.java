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

public class GameRotationActivity extends Activity {
	private String TAG = "GameRotationActivity";

	private SensorManager mSensorManager;
	private Sensor mGameRotation = null;

	private Timer mUpdateTimer;

	TextView xGameRotation;
	TextView yGameRotation;
	TextView zGameRotation;
	TextView timeStampView;

	private float[] mGameRotationData;
	private long timeStamp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.i(TAG, "---GameRotationActivity onCreate");
		setContentView(R.layout.activity_game_rotation);
		buildView();

		mGameRotationData = new float[3];
		getSensorService();

		mUpdateTimer = new Timer("GameRotationActivity");
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
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		if (mSensorManager == null) {
			Log.e(TAG, "----getSystemService failed");

		} else {
			mGameRotation = mSensorManager
					.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.i(TAG, "---GameRotationActivity onResume");

		if (mGameRotation != null) {
			mSensorManager.registerListener(mListener, mGameRotation,
					SensorManager.SENSOR_DELAY_UI);
		} else {
			mUpdateTimer.cancel();
			xGameRotation.setText("Device have not game rotation sensor.");
			yGameRotation.setText("");
			zGameRotation.setText("");
		}
	}

	@Override
	protected void onPause() {
		super.onPause();

		if (mGameRotation != null) {
			mSensorManager.unregisterListener(mListener, mGameRotation);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		Log.i(TAG, "---GameRotationActivity onDestroy");
		if (mUpdateTimer != null) {
			mUpdateTimer.cancel();
			mUpdateTimer = null;
		}
		if (mGameRotation != null) {
			mSensorManager.unregisterListener(mListener, mGameRotation);
			mGameRotation = null;
		}
	}

	private SensorEventListener mListener = new SensorEventListener() {

		@Override
		public void onSensorChanged(SensorEvent event) {
			if (event.sensor.getType() == Sensor.TYPE_GAME_ROTATION_VECTOR) {
				mGameRotationData[0] = event.values[0];
				mGameRotationData[1] = event.values[1];
				mGameRotationData[2] = event.values[2];
				timeStamp = event.timestamp;
			}
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub

		}
	};

	protected void updateUI() {
		this.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				xGameRotation
						.setText("GameRotation X: " + mGameRotationData[0]);
				yGameRotation
						.setText("GameRotation Y: " + mGameRotationData[1]);
				zGameRotation
						.setText("GameRotation Z: " + mGameRotationData[2]);
				timeStampView.setText("time stamp:     " + timeStamp);
			}
		});
	}

	private void buildView() {
		xGameRotation = (TextView) findViewById(R.id.xGameRotationText);
		yGameRotation = (TextView) findViewById(R.id.yGameRotationText);
		zGameRotation = (TextView) findViewById(R.id.zGameRotationText);
		timeStampView = (TextView) findViewById(R.id.timeStampText);
	}

}
