package com.example.musicservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by marco on 4/19/17.
 */

public class ServiceBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Music end", Toast.LENGTH_SHORT).show();
        Log.e("xxx", "marco----onReceive");
    }
}
