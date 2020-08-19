package com.example.charginganimation;

import android.content.Context;
import android.graphics.drawable.ClipDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class CustomClipLoading extends FrameLayout {
    private final static int MSG_ID1 = 1001;
    private final static int MAX_PROGRESS = 10000;

    private ClipDrawable mClipDrawable;
    private ImageView imageView;
    private int mProgress = 0;
    private Thread mThread;
    private boolean running = false;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_ID1:
                    mClipDrawable.setLevel(mProgress);
                    break;
                default:
                    break;
            }
        }
    };

    public CustomClipLoading(Context context) {
        this(context, null, 0);
    }

    public CustomClipLoading(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomClipLoading(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_loading, null);
        addView(view);
        imageView = (ImageView) findViewById(R.id.iv_progress);
        mClipDrawable = (ClipDrawable) imageView.getDrawable();
    }

    public void setBg(int source) {
        if (imageView != null) {
            imageView.setBackgroundResource(source);
        }
    }

    public void start() {
        if (mThread == null && running == false) {
            running = true;
            mThread = new Thread(r);
            mThread.start();
        }
    }

    public void stop() {
        if (mThread != null) {
            running = false;
            mProgress = 0;
            mThread = null;
        }
    }

    Runnable r = new Runnable() {
        @Override
        public void run() {
            while (running) {
                handler.sendEmptyMessage(MSG_ID1);
                if (mProgress > MAX_PROGRESS) {
                    mProgress = 0;
                }
                mProgress += 80;
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
}
