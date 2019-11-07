package com.example.downloadimage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    private String URL = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1546089393145&di=b67be651d8440768666f2f71c08d3965&imgtype=0&src=http%3A%2F%2Fpic35.photophoto.cn%2F20150630%2F0020033071683339_b.jpg";

    private Button button;
    private ImageView imageView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buildPrivateData();
        buildViews();
    }

    private void buildPrivateData() {

    }

    private void buildViews() {
        button = (Button) findViewById(R.id.button);
        imageView = (ImageView) findViewById(R.id.image_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoadImage(MainActivity.this, progressBar, imageView)
                        .execute(URL);
            }
        });
    }
}

