package com.example.broadcast;

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
        Toast.makeText(context, "Show message:\n" + intent.getStringExtra
                ("msg"), Toast.LENGTH_SHORT).show();
    }
}
