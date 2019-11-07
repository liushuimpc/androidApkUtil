package com.example.viewtest;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "ViewTest";

    private final static int SET_IMAGE_ALPHA = 1;
    private Button plus = null;
    private Button minus = null;
    private Button next = null;
    private ImageView image1 = null;
    private ImageView image2 = null;
    private int currentImg = 2;
    private int alpha = 255;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SET_IMAGE_ALPHA:
                    Log.e(TAG, "marco----alpha=" + alpha);
                    image1.setImageAlpha(alpha);
                    break;

                default:
                    break;
            }
        }
    };

    private OnClickListener listener = new OnClickListener() {

        @Override
        public void onClick(View v) {

            if (v == plus) {
                alpha += 20;
            } else if (v == minus) {
                alpha -= 20;
            }

            if (alpha >= 255) {
                alpha = 255;
            } else if (alpha <= 0) {
                alpha = 0;
            }

            mHandler.obtainMessage(SET_IMAGE_ALPHA).sendToTarget();
        }

    };
    private int[] images = new int[]{
            R.drawable.leonard,
            R.drawable.spurs,
            R.drawable.q3,
            R.drawable.quicktime,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buildView();
    }

    private void buildView() {
        plus = (Button) findViewById(R.id.button1);
        minus = (Button) findViewById(R.id.button2);
        next = (Button) findViewById(R.id.button3);
        image1 = (ImageView) findViewById(R.id.image1);
        image2 = (ImageView) findViewById(R.id.image2);

        next.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                image1.setImageResource(images[++currentImg % images.length]);
            }
        });

        plus.setOnClickListener(listener);
        minus.setOnClickListener(listener);

        image1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) image1
                        .getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();

                double scale = 1.0 * bitmap.getHeight() / image1.getHeight();
                int x = (int) (event.getX() * scale);
                int y = (int) (event.getY() * scale);
                if (x + 120 > bitmap.getWidth()) {
                    x = bitmap.getWidth() - 120;
                }
                if (y + 120 > bitmap.getHeight()) {
                    y = bitmap.getHeight() - 120;
                }

                image2.setImageBitmap(Bitmap.createBitmap(bitmap, x, y, 120,
                        120));
                image2.setImageAlpha(alpha);
                return false;
            }
        });
    }
}
