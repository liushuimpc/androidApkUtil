package com.example.t3;

import android.support.v7.app.AppCompatActivity;
import android.app.Activity;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.test.TestManager;
import android.os.test.TestEventListener;
import android.os.test.TestListenerEvent;
import android.view.View;
import android.widget.Button;
import android.util.Log;

public class MainActivity extends Activity {
    private static final String TAG = "T3";
    TestManager mTestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "marcoo--onCreate()");
        setContentView(R.layout.activity_main);
        mTestManager = (TestManager)getSystemService(Context.TEST_SERVICE);
        if (mTestManager != null) {
            Log.i(TAG, "marcoo--mTestManager OK");
        } else {
            Log.i(TAG, "marcoo--mTestManager null");
        }

        Button init = findViewById(R.id.button_init);
        Button hello = findViewById(R.id.button_hello);
        Button set = findViewById(R.id.button_setCallback);
        Button release = findViewById(R.id.button_release);
        init.setOnClickListener(mOnClickListener);
        hello.setOnClickListener(mOnClickListener);
        set.setOnClickListener(mOnClickListener);
        release.setOnClickListener(mOnClickListener);
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.button_init:
                    mTestManager.init(123,"marco-name=t3");
                    break;
                case R.id.button_hello:
                    String res = mTestManager.helloWorld("marco-HelloFromTestAPP");
                    Log.d(TAG, "res=" + res);
                    break;
                case R.id.button_setCallback:
                    mTestManager.setTestListener(new TestEventListener(){
                        @Override
                        public void onEvent(TestListenerEvent event) {
                            String msg = event.getMsg();
                            int what = event.getWhat();
                            Log.d(TAG, msg+" "+what);
                        }
                    });
                    break;
                case R.id.button_release:
                    Log.d(TAG, "marcoo--release");
                    mTestManager.release();
                    break;
                default:
                    break;
            }
        }
    };

}
