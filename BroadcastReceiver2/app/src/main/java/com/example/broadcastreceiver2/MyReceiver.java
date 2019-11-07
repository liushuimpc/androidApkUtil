package com.example.broadcastreceiver2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by marco on 5/2/17.
 */

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "APK2 Show message:\n" + intent.getStringExtra
                ("msg"), Toast.LENGTH_SHORT).show();
    }
}
