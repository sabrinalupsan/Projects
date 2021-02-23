package com.example.examenbiler2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static java.net.HttpURLConnection.HTTP_OK;

public class DownloadAsync extends AsyncTask<String, Void, String> {
    private static final String TAG = DownloadAsync.class.getSimpleName();

    @Override
    protected String doInBackground(String... strings) {
        String result = null;
        Log.d(TAG,"Input string: " + strings[0]);
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(strings[0]);
            URLConnection connection = url.openConnection();
            if(connection instanceof HttpURLConnection)
            {
                httpURLConnection = (HttpURLConnection) connection;
                httpURLConnection.connect();
                int resultCode = httpURLConnection.getResponseCode();
                if(resultCode == HTTP_OK)
                {
                    InputStream is = httpURLConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is, Charset.forName(StandardCharsets.UTF_8.name()));
                    StringBuilder textBuilder = new StringBuilder();
                    try(Reader reader = new BufferedReader(isr))
                    {
                        int c = 0;
                        while((c = reader.read())!= -1)
                        {
                            textBuilder.append((char)c);
                        }
                    }
                    result = textBuilder.toString();
                    publishProgress();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        Log.d(TAG, "Progress achieved ...");
    }
}
