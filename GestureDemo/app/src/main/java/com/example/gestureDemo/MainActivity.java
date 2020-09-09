package com.example.gestureDemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener, GestureDetector.OnGestureListener {
    private final static String TAG = "GestureDemo";
    private static final float FLING_MIN_DISTANCE = 40;
    private static final float FLING_MIN_VELOCITY = 100;

    private Button button;
    private TextView textView;
    private GestureDetector gestureDetector;

    private enum DIRECTION {
        NONE,
        LEFT,
        RIGHT,
        UP,
        DOWN
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "onCreate");
        init();
        getViews();

        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.l1);
        constraintLayout.setLongClickable(true);
        constraintLayout.setOnTouchListener(this);
    }

    private void init() {
        gestureDetector = new GestureDetector(this, this);
    }

    private void getViews() {
        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.text);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        Log.i(TAG, "onDown, event=" + motionEvent.getAction());

        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
        Log.i(TAG, "onShowPress, event=" + motionEvent.getAction());

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        Log.i(TAG, "onSingleTapUp, event=" + motionEvent.getAction());

        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float distanceX, float distanceY) {
        final int MIN_DISTANCE = 20;

        DIRECTION direction = DIRECTION.NONE;
        float x0 = motionEvent.getX();
        float y0 = motionEvent.getY();
        float x1 = motionEvent1.getX();
        float y1 = motionEvent1.getY();
        Log.i(TAG, "onScroll, event=" + motionEvent.getAction() + ", event1=" + motionEvent1.getAction());
        Log.i(TAG, "onScroll, v=" + distanceX + ", v1=" + distanceY);
        Log.i(TAG, "onScroll, x0=" + x0 + ", x1=" + x1 + "y0=" + y0 + ", y1=" + y1);

        if (distanceX > MIN_DISTANCE) {
            direction = DIRECTION.LEFT;
            Toast.makeText(this, "LEFT", Toast.LENGTH_SHORT).show();
        }
        if (distanceX < -MIN_DISTANCE) {
            direction = DIRECTION.RIGHT;
            Toast.makeText(this, "RIGHT", Toast.LENGTH_SHORT).show();
        }
        if (distanceY > MIN_DISTANCE) {
            direction = DIRECTION.UP;
            Toast.makeText(this, "UP", Toast.LENGTH_SHORT).show();
        }
        if (distanceY < -MIN_DISTANCE) {
            direction = DIRECTION.DOWN;
            Toast.makeText(this, "DOWN", Toast.LENGTH_SHORT).show();
        }
        Log.i(TAG, "--------onScroll, direction=" + direction);
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        Log.i(TAG, "onLongPress, event=" + motionEvent.getAction());

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        DIRECTION direction = DIRECTION.NONE;
        float x0 = motionEvent.getX();
        float y0 = motionEvent.getY();
        float x1 = motionEvent1.getX();
        float y1 = motionEvent1.getY();
        Log.i(TAG, "onFling, event=" + motionEvent.getAction() + ", event1=" + motionEvent1.getAction());
        Log.i(TAG, "onFling, v=" + v + ", v1=" + v1);
        Log.i(TAG, "onFling, x0=" + x0 + ", x1=" + x1 + "y0=" + y0 + ", y1=" + y1);

        if (x0 - x1 > FLING_MIN_DISTANCE && Math.abs(v) > FLING_MIN_VELOCITY) {
            direction = DIRECTION.LEFT;
            Toast.makeText(this, "Fling-LEFT", Toast.LENGTH_SHORT).show();
        }
        if (x1 - x0 > FLING_MIN_DISTANCE && Math.abs(v) > FLING_MIN_VELOCITY) {
            direction = DIRECTION.RIGHT;
            Toast.makeText(this, "Fling-RIGHT", Toast.LENGTH_SHORT).show();
        }
        if (y0 - y1 > FLING_MIN_DISTANCE && Math.abs(v1) > FLING_MIN_VELOCITY) {
            direction = DIRECTION.UP;
            Toast.makeText(this, "Fling-UP", Toast.LENGTH_SHORT).show();
        }
        if (y1 - y0 > FLING_MIN_DISTANCE && Math.abs(v1) > FLING_MIN_VELOCITY) {
            direction = DIRECTION.DOWN;
            Toast.makeText(this, "Fling-DOWN", Toast.LENGTH_SHORT).show();
        }
        Log.i(TAG, "========onFling, direction=" + direction);
        return true;
    }
}