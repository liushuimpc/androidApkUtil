package com.datalogic.backgroundservicereceivebroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

public class BootCompletedReceiver extends BroadcastReceiver {
    private final static String TAG = "BootCompletedReceiver";

    private Context mContext;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.i(TAG, "BootCompletedReceiver, action=" + action);

            if (action.equals(MainActivity.TEST_ACTION)) {
                Toast t = Toast.makeText(context, TAG + " app received" +
                        action, Toast.LENGTH_SHORT);
                t.show();
            }
        }
    };

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "====BootCompletedReceiver====");
        final String action = intent.getAction();
        Log.i(TAG, "BootCompletedReceiver, action=" + action);
        mContext = context;
//        registerReceiver();
    }

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter(MainActivity.TEST_ACTION);
        mContext.registerReceiver(receiver, filter);
        Log.i(TAG, "registered receiver TEST_ACTION");
    }

}
