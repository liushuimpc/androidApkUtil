package com.example.batterydemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class BatteryView extends View {
    private final static String TAG = "BatteryView";

    private int BATTERY_SCALE = 100;
    private float mPower = 100;

    public BatteryView(Context context) {
        super(context);
    }

    public BatteryView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int battery_left = 0;
        int battery_top = 0;
        int battery_width = 60;
        int battery_height = 30;

        int battery_head_width = 6;
        int battery_head_height = 10;

        int battery_inside_margin = 3;

        // Draw battery cover
        Paint batteryCoverPaint = new Paint();
        batteryCoverPaint.setColor(Color.WHITE);
        batteryCoverPaint.setAntiAlias(true);
        batteryCoverPaint.setStyle(Style.STROKE);
        batteryCoverPaint.setStrokeWidth(3);        // set the width of paint brush
//        Rect rect = new Rect(battery_left, battery_top,
//                battery_left + battery_width, battery_top + battery_height);
//        canvas.drawRect(rect, batteryCoverPaint);
        RectF rect = new RectF(battery_left, battery_top,
                battery_left + battery_width, battery_top + battery_height);
        canvas.drawRoundRect(rect, 10f, 10f, batteryCoverPaint);

        // Draw battery head
        Paint batteryHeadPaint = new Paint();
        batteryHeadPaint.setColor(Color.WHITE);
        batteryHeadPaint.setStyle(Style.FILL_AND_STROKE);
        int h_left = battery_left + battery_width;
        int h_top = battery_top + battery_height / 2 - battery_head_height / 2;
        int h_right = h_left + battery_head_width;
        int h_bottom = h_top + battery_head_height;
//        Rect headRect = new Rect(h_left, h_top, h_right, h_bottom);
//        canvas.drawRect(headRect, batteryHeadPaint);
        RectF headRect = new RectF(h_left, h_top, h_right, h_bottom);
        canvas.drawRoundRect(headRect, 1f, 1f, batteryHeadPaint);

        // Draw battery core
        float powerPercent = mPower / 100.0f;
        Paint batteryCorePaint = new Paint();
        batteryCorePaint.setColor(Color.BLUE);
        batteryCorePaint.setStyle(Style.FILL);
        if (powerPercent > 0 && powerPercent <= BATTERY_SCALE) {
            int p_left = battery_left + battery_inside_margin;
            int p_top = battery_top + battery_inside_margin;
            int p_right = p_left - battery_inside_margin + (int) ((battery_width - battery_inside_margin) * powerPercent);
            int p_bottom = p_top + battery_height - battery_inside_margin * 2;
            Log.i("marcoo", "value=" + p_left + ", " + p_top + ", " + p_right + ", " + p_bottom + ".");
//            Rect coreRect = new Rect(p_left, p_top, p_right, p_bottom);
//            canvas.drawRect(coreRect, batteryCorePaint);
            RectF coreRect = new RectF(p_left, p_top, p_right, p_bottom);
            canvas.drawRoundRect(coreRect, 8f, 8f, batteryCorePaint);
        }
    }

    public void setPower(int power) {
        mPower = power;
        if (mPower < 0) {
            mPower = 0;
        }
        Log.i(TAG, "mPower=" + mPower);
        invalidate();
    }
}
