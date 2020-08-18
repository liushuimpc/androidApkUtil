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
    public final static String TAG = "BatteryDemo";

    private BatteryView mBatteryView;

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
        init();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pause();
    }

    private void init() {
        registerReceiver(batteryChangedReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    private void pause() {
        unregisterReceiver(batteryChangedReceiver);
    }

    private BroadcastReceiver batteryChangedReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                int level = intent.getIntExtra("level", 0);
                int batteryLevel = level;
                Log.d(TAG, "BatteryLevel=" + batteryLevel);
                mBatteryView.setPower(batteryLevel);
            }
        }
    };
}
