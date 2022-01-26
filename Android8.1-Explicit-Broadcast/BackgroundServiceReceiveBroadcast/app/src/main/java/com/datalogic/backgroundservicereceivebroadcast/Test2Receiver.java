package com.datalogic.backgroundservicereceivebroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class Test2Receiver extends BroadcastReceiver {
    private final static String TAG = "Test2Receiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "===Test2Receiver--action=" + intent.getAction());
        Toast t = Toast.makeText(context, "Test2Receiver received " + intent
                .getAction(), Toast.LENGTH_SHORT);
        t.show();
    }
}
