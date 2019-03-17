package com.examples.android.androidtrainingbzu.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class TaskLoader extends AsyncTaskLoader<Bitmap> {
    String murl;
    public TaskLoader(@NonNull Context context, String url) {
        super(context);
        this.murl=url;
    }


    @Nullable
    @Override
    public Bitmap loadInBackground() {
        HttpURLConnection conn=null;
        InputStream is=null;
        try {
            URL requestURL = new URL(murl);
            conn =(HttpURLConnection) requestURL.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            if(conn.getResponseCode() == HttpsURLConnection.HTTP_OK){
                is = conn.getInputStream();
                Bitmap mIcon = BitmapFactory.decodeStream(is);
                return  mIcon;
            }else {
                return null;
            }
        }catch (Exception ex){
            Log.e(DownloadFileAsyncTask.class.getSimpleName(),ex.getMessage());
        }finally {
            if(conn!=null)
                conn.disconnect();

            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
