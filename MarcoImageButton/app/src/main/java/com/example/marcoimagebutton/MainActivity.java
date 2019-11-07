package com.example.marcoimagebutton;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";

    private LinearLayout llbtDataConfig = null;  //main布局中包裹本按钮的容器
    private MarcoImageButton btDataConfig = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btDataConfig = new MarcoImageButton(this, R.drawable.fs_good, R.string
                .app_name);

        //获取包裹本按钮的容器
        llbtDataConfig = (LinearLayout) findViewById(R.id.ll_bt_data_config);

        //将我们自定义的Button添加进这个容器
        llbtDataConfig.addView(btDataConfig);

        //设置按钮的监听
        btDataConfig.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                btDataConfig.setText("Clicked");
            }
        });

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id
                .activity_main);

        TextView v = new TextView(this);
        v.setTextSize(30);
        v.setTextColor(ContextCompat.getColor(this, R.color.passText));
        v.setBackgroundColor(ContextCompat.getColor(this, R.color.passBG));
        v.setText("PASS");

        TextView v2 = new TextView(this);
        v2.setTextSize(30);
        v2.setTextColor(ContextCompat.getColor(this, R.color.failedText));
        v2.setBackgroundColor(ContextCompat.getColor(this, R.color.failedBG));
        v2.setText("FAILED");

        linearLayout.addView(v);
        linearLayout.addView(v2);
    }

    @Override
    protected void onStart() {
        Log.e(TAG, "marco--onStart");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.e(TAG, "marco--onstop");
        super.onStop();
    }

    @Override
    protected void onRestart() {
        Log.e(TAG, "marco--onRestart");
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        Log.e(TAG, "marco--onDestroy");
        super.onDestroy();
    }
}