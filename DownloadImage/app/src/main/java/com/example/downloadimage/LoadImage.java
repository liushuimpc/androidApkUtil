package com.example.downloadimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class LoadImage extends AsyncTask<String, Void, Bitmap> {
    private final static String TAG = "LoadImage";
    private final static int CONNECT_TIMEOUT = 3000;
    private final static int READ_TIMEOUT = 3000;

    Context context;
    ProgressBar progressBar;
    ImageView imageView;

    public LoadImage(Context context, ProgressBar progressBar, ImageView
            imageView) {
        this.context = context;
        this.progressBar = progressBar;
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        String url = strings[0];
        Bitmap bitmap = null;
        URLConnection connection;
        InputStream inputStream;

        Log.i(TAG, "url=" + url);
        try {
            connection = new URL(url).openConnection();
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.connect();

            inputStream = connection.getInputStream();
            if (inputStream != null) {
                BufferedInputStream bis = new BufferedInputStream(inputStream);
                bitmap = BitmapFactory.decodeStream(bis);
                bis.close();
                inputStream.close();
                Log.i(TAG, "input stream != null");

            } else {
                Log.i(TAG, "input stream == null");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "doInBackground finished");
        return bitmap;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        Log.i(TAG, "onPostExecute--bitmap=" + bitmap);
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
        if (imageView != null) {
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);

            } else {
                imageView.setImageDrawable(context.getResources().getDrawable
                        (R.drawable.cry_512));
            }
        }
    }
}
