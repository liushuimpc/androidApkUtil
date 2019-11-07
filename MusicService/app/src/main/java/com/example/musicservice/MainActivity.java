package com.example.musicservice;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener, ParamUtils {
    private final static String TAG = "MainActivity";

    private ServiceBroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.start_music).setOnClickListener(this);
        findViewById(R.id.pause_music).setOnClickListener(this);
        findViewById(R.id.stop_music).setOnClickListener(this);

//        receiver = new ServiceBroadcastReceiver();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction("com.complete");
//        registerReceiver(receiver, filter);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_music:
                Log.e(TAG, "marco------start_music");
                playMusic(START_MUSIC);
                break;

            case R.id.pause_music:
                Log.e(TAG, "marco------pause_music");
                playMusic(PAUSE_MUSIC);
                break;

            case R.id.stop_music:
                Log.e(TAG, "marco------stop_music");
                playMusic(STOP_MUSIC);
                break;

            default:
                break;
        }
    }

    private void playMusic(int type) {
        Intent intent = new Intent(this, MusicService.class);
//        intent.putExtra("type", type);
        startService(intent);
        Log.e(TAG, "marco-------playMusic type=" + type);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "marco-----onDestroy");
//        unregisterReceiver(receiver);
    }
}
