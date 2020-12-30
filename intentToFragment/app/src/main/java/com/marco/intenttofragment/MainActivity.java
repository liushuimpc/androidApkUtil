package com.marco.intenttofragment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate");

        Intent intent = new Intent();
        intent.setClass(MainActivity.this, TmpActivity.class);
        intent.putExtra("flag",100);
        startActivity(intent);
        finish();
    }
}