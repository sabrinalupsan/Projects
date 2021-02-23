package com.example.casatorii;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class ImageActivity extends AppCompatActivity {

    private Intent intent;
    private ImageView img;
    public ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        img = findViewById(R.id.imageView);
        progressBar = findViewById(R.id.progressBar);
        intent = getIntent();
        String url = intent.getStringExtra("image");
        progressBar.setProgress(0);
        DownloadContent imageTask = new DownloadContent(url);
        Thread downloadThread = new Thread(imageTask);
        downloadThread.start();

        DownloadContent.handler = new Handler()
        {
            @Override
            public void handleMessage(@NonNull Message msg) {
                Bundle data = msg.getData();
                Bitmap image = data.getParcelable("image");
                img.setImageBitmap(image);
                progressBar.setProgress(100);
            }
        };
    }
}