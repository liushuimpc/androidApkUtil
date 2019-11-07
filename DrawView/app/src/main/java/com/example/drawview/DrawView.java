package com.example.drawview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by marco on 4/18/17.
 */

public class DrawView extends View {
    public float currentX = 40;
    public float currentY = 50;

    Paint p = new Paint();

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        p.setColor(Color.RED);
        canvas.drawCircle(currentX, currentY, 15, p);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        currentX = event.getX();
        currentY = event.getY();

        Log.e("TEST", "marco----x=" + currentX + "y=" + currentY);
        this.invalidate();
        return true;
    }

}
