package com.dl.example.runningviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class RunningView extends View {
    private float bitmapX = 100.0f;
    private float bitmapY = 200.0f;
    private Paint paint;
    private Bitmap bitmap;

    public RunningView(Context context) {
        super(context);
        init();
    }

    private void init() {
        paint = new Paint();
        bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.running_view);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float x = bitmapX - (bitmap.getWidth() / 2) ;
        float y = bitmapY - (bitmap.getHeight() / 2) ;
        canvas.drawBitmap(bitmap, x, y, paint);
        if (bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }

    public void setX(float x) {
        bitmapX = x;
    }

    public void setY(float y) {
        bitmapY = y;
    }
}
