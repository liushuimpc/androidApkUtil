package com.marco.intenttofragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

public class TmpActivity extends AppCompatActivity {
    private static final String TAG = "TmpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tmp);

        Intent intent = getIntent();
        int flag = intent.getIntExtra("flag", 0);
        Log.i(TAG, "flag=" + flag);
        FirstFragment firstFragment = new FirstFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.tmp, firstFragment).commit();
    }
}
