package com.dong.compasslevel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by marco on 5/31/17.
 */

public class LevelView extends View {
    Bitmap back;
    Bitmap bubble;

    int bubbleX;
    int bubbleY;

    public LevelView(Context context, AttributeSet attrs) {
        super(context, attrs);

        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        int screenWidth = metrics.widthPixels;
        back = Bitmap.createBitmap(screenWidth, screenWidth, Bitmap.Config
                .ARGB_8888);

        Canvas canvas = new Canvas(back);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        Shader shader = new LinearGradient(0, screenWidth, 0.8f *
                screenWidth, 0.2f * screenWidth, Color.YELLOW, Color.WHITE,
                Shader.TileMode.MIRROR);
        paint.setShader(shader);
        canvas.drawCircle(screenWidth / 2 - 2, screenWidth / 2 - 2, screenWidth
                / 2 - 2, paint);

        Paint paint2 = new Paint();
        paint2.setAntiAlias(true);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeWidth(2);
        paint2.setColor(Color.BLACK);
        canvas.drawCircle(screenWidth / 2 - 2, screenWidth / 2 - 2, screenWidth
                / 2 - 2, paint2);

        canvas.drawLine(0, screenWidth / 2, screenWidth, screenWidth / 2,
                paint2);
        canvas.drawLine(screenWidth / 2, 0, screenWidth / 2, screenWidth,
                paint2);
        paint2.setStrokeWidth(10);
        paint2.setColor(Color.RED);

        canvas.drawLine(screenWidth / 2 - 30, screenWidth / 2, screenWidth /
                2 + 30, screenWidth / 2, paint2);
        canvas.drawLine(screenWidth / 2, screenWidth / 2 - 30, screenWidth / 2,
                screenWidth / 2 + 30, paint2);

        bubble = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
        bubbleX = (screenWidth - bubble.getWidth()) / 2;
        bubbleY = (screenWidth - bubble.getWidth()) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(back, 0, 0, null);
        canvas.drawBitmap(bubble, bubbleX, bubbleY, null);
    }
}
