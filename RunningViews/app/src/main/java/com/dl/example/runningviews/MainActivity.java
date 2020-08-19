package com.dl.example.runningviews;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.app.Activity;

public class MainActivity extends Activity {
	private static final String TAG = "Demo";

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout frame = (FrameLayout) findViewById(R.id.page_layout);
        final RunningView runningView = new RunningView(MainActivity.this);

        runningView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                runningView.setX(event.getX());
                runningView.setY(event.getY());
                runningView.invalidate();
                return true;
            }
        });
        frame.addView(runningView);
    }
}
