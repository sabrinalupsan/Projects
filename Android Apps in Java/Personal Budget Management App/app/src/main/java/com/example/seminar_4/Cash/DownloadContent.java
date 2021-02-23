package com.example.seminar_4.Cash;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.seminar_4.model.Image;
import com.example.seminar_4.model.Wish;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import static java.net.HttpURLConnection.HTTP_OK;

public class DownloadContent implements Runnable {

    private static final String TAG = DownloadContent.class.getSimpleName();
    String url;
    String category;
    public static Handler handler;
    public DownloadContent(Wish wish) {
        this.url = wish.getUrl();
        this.category=wish.getCategory();
    }

    @Override
    public void run() {
        Log.d(TAG, "----------download content run------------");
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(this.url);
            URLConnection connection = url.openConnection();
            if(connection instanceof HttpURLConnection)
            {
                httpURLConnection = (HttpURLConnection) connection;
                httpURLConnection.connect();
                int resultCode = httpURLConnection.getResponseCode();
                if(resultCode == HTTP_OK)
                {
                    InputStream is = httpURLConnection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    Message message = handler.obtainMessage();
                    Bundle bundle = new Bundle();
//                    Image img=new Image(bitmap,this.category);
                    bundle.putParcelable("image", bitmap);
                    bundle.putString("category", this.category);

                    message.setData(bundle);
                    handler.sendMessage(message);
                    Log.d(TAG, "----------download finished with success------------");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
