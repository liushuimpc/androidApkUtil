package com.example.unlockkeyguard;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

import static android.os.PowerManager.SCREEN_DIM_WAKE_LOCK;

public class BootCompletedReceiver extends BroadcastReceiver {
    private final static String TAG = "BootCompletedReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "marcoo---onReceive");
        Log.e(TAG, "marcooo--onReceive: action=" + intent.getAction());
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | SCREEN_DIM_WAKE_LOCK, TAG);
        wl.acquire();
        KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);

        KeyguardManager.KeyguardLock kl = km.newKeyguardLock(TAG);
        Log.i(TAG, "marcooo--try to disableKeyguard");
        kl.disableKeyguard();
        Log.i(TAG, "marcooo--finished disableKeyguard");
        Intent mainIntent = new Intent(context, MainActivity.class);
        //start a Activity in BroadcastReceiver, requires FLAG_ACTIVITY_NEW_TASK.
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mainIntent);
        Log.i(TAG, "marcoo--end Reciver");
    }
}
