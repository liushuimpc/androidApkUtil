package com.example.batterydemo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	BatteryView mBatteryView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mBatteryView = (BatteryView) findViewById(R.id.battery_view);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		register();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		unregister();
	}
	
	private void register() {
		registerReceiver(batteryChangedReceiver,  new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
	}
	
	private void unregister() {
		unregisterReceiver(batteryChangedReceiver);
	}

	// 接受广播
	private BroadcastReceiver batteryChangedReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
				int level = intent.getIntExtra("level", 0);
				int scale = intent.getIntExtra("scale", 100);
				int power = level * 100 / scale;
				Log.d("Deom", "电池电量：:" + power);
				mBatteryView.setPower(power);
			}
		}
	};
}
