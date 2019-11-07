package com.example.musicservice;


import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by marco on 4/19/17.
 */

public class MusicService extends Service implements ParamUtils {
    private final static String TAG = "MusicService";

    private MediaPlayer mediaPlayer;
    private boolean isStop = true;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "marco----onBind");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "marco---------onCreate");

        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            Log.e(TAG, "marco---------onCreate new mediaPlayer");

            mediaPlayer.setOnCompletionListener(new MediaPlayer
                    .OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    Intent intent = new Intent();
                    intent.setAction("com.complete");
                    sendBroadcast(intent);
                }
            });
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "marco---onStartCommand");

        switch (intent.getIntExtra("type", -1)) {
            case START_MUSIC:
                Log.e(TAG, "marco-------PLAY_MUSIC");
                if (isStop) {
                    Log.e(TAG, "marco----isStop");
                    mediaPlayer.reset();
                    mediaPlayer = MediaPlayer.create(this, R.raw.yanse);
                    mediaPlayer.start();
                    mediaPlayer.setLooping(false);
                    isStop = false;
                } else if (!isStop && mediaPlayer.isPlaying() &&
                        mediaPlayer != null) {
                    Log.e(TAG, "marco----! isStop");
                    mediaPlayer.start();
                }
                break;

            case PAUSE_MUSIC:
                Log.e(TAG, "marco-------PAUSE_MUSIC");
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
                break;

            case STOP_MUSIC:
                Log.e(TAG, "marco-------STOP_MUSIC");
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    isStop = true;
                }
                break;

            default:
                Log.e(TAG, "marco----default");
                break;
        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
