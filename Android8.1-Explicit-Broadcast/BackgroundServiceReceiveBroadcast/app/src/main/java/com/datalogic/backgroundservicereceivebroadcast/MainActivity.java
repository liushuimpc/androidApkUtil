package com.datalogic.backgroundservicereceivebroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "ReceiveBroadcast";
    public final static String TEST_ACTION = "sendbroadcast.TEST1";

/*
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.i(TAG, "onReceiver, action=" + action);

            if (action.equals(TEST_ACTION)) {
                Toast t = Toast.makeText(context, TAG + " app received" +
                        action, Toast.LENGTH_SHORT);
                t.show();
            }
        }
    };

    private BroadcastReceiver bootCompletedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "boot complete");
//            registerReceiver();
        }
    };

    private BroadcastReceiver staticTest1Receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "====staticTest1Receiver====");

            final String action = intent.getAction();
            Log.i(TAG, "staticTest1Receiver, action=" + action);

            if (action.equals(TEST_ACTION)) {
                Toast t = Toast.makeText(context, TAG + " app received" +
                        action, Toast.LENGTH_SHORT);
                t.show();
            }
        }
    };
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*registerReceiver();*/
    }

/*    private void registerReceiver() {
        IntentFilter filter = new IntentFilter(TEST_ACTION);
        registerReceiver(receiver, filter);
    }

    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }*/
}
