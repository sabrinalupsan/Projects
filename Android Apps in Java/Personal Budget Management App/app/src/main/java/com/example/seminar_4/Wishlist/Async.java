package com.example.seminar_4.Wishlist;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Async extends AsyncTask<String,Void, String> {
    private static final String TAG =Async.class.getSimpleName() ;
//    private  String url;
//    private JSONObject jsonObject;
//    public Async(String url, JSONObject jsonObject){
//        this.url=url;
//        this.jsonObject=jsonObject;
//    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection httpURLConnection=null;

        try {
            URL url = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            byte[] postData       = strings[1].getBytes( StandardCharsets.UTF_8 );

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=*****");

            connection.setRequestProperty("charset", "utf-8");
//            connection.setRequestProperty("Content-Disposition", "form-data; name=\"myfile\"; filename=\"LepirdaDamon.json\"");
            connection.setRequestProperty("Connection", "keep-alive");

            connection.setDoOutput(true);



            try( DataOutputStream wr = new DataOutputStream( connection.getOutputStream())) {
                wr.writeBytes("--*****--\\r\\n");
                wr.writeBytes("Content-Disposition: form-data; name=\"myfile\"; filename=\"LepirdaDamon.json\"\\r\\n"
                        );
                wr.write( postData );
                wr.writeBytes("\\r\\n--*****--");
            }

            connection.connect();
            int responseCode = connection.getResponseCode();
            Log.d(TAG, String.valueOf(responseCode));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}
