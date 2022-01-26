package com.datalogic.sendbroadcast;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "SendBroadcast";
    private final static String TEST_ACTION = "sendbroadcast.TEST1";
    private final static String targetPackage = "com.datalogic.backgroundservicereceivebroadcast";
    private final static String targetReceiver = ".Test2Receiver";

    private Button sendBroadcastButton;

//    private BroadcastReceiver receiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            final String action = intent.getAction();
//            Log.i(TAG, "onReceiver, action=" + action);
//
//            if (action.equals(TEST_ACTION)) {
//                Toast t = Toast.makeText(context, "SendBroadcast app " +
//                        "received" + action, Toast.LENGTH_SHORT);
//                t.setGravity(Gravity.TOP, 0, 0);
//                t.show();
//            }
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buildViews();
        buildData();
//        registerReceiver();
    }

    private void buildViews() {
        sendBroadcastButton = (Button) findViewById(R.id.send_broadcast);
        sendBroadcastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Clicked button");
                Intent intent = new Intent(TEST_ACTION);
                intent.setComponent(new ComponentName(targetPackage,
                        targetPackage + targetReceiver));
                sendBroadcast(intent);
                Log.i(TAG, "Sending broadcast");
            }
        });
    }

    private void buildData() {

    }

//    private void registerReceiver() {
//        IntentFilter filter = new IntentFilter(TEST_ACTION);
//        registerReceiver(receiver, filter);
//    }
//
//    protected void onDestroy() {
//        unregisterReceiver(receiver);
//        super.onDestroy();
//    }
}
