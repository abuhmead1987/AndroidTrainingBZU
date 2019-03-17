package com.examples.android.androidtrainingbzu.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.examples.android.androidtrainingbzu.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadFileAsyncTask  extends AsyncTask<String,String, Bitmap> {
    ImageView imgView;
    TextView textView;
    ProgressBar progressBar;
    public DownloadFileAsyncTask(ImageView imgView, TextView textView, ProgressBar progressBar) {
        this.imgView = imgView;
        this.textView=textView;
        this.progressBar=progressBar;
    }

    @Override
    protected void onPreExecute() {
        //imgView.setImageResource(R.drawable.loading_image);
        /**
         * To play gif file in image view we use third party library like Glide
         * to read more and see the documentation follow the link bellow
         * https://github.com/bumptech/glide
         */
      //  Glide.with(imgView.getContext()).load(R.drawable.loading_image).into(imgView);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        progressBar.setVisibility(View.GONE);
        imgView.setVisibility(View.VISIBLE);
        imgView.setImageBitmap(bitmap);
    }

    @Override
    protected void onProgressUpdate(String... values) {
        textView.setText(""+values[0]);
    }

    @Override
    protected Bitmap doInBackground(String... strings) {

        try {
            publishProgress("doInBackground started ...");
            Thread.sleep(2000);
            URL url = new URL(strings[0]);
            HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
            publishProgress("Open the connection ...");
            Thread.sleep(2000);
            urlcon.setDoInput(true);
            urlcon.connect();
            publishProgress("Connected...");
            Thread.sleep(2000);
            InputStream in = urlcon.getInputStream();
            publishProgress("Input stream getted ...");
            Thread.sleep(2000);
            Bitmap mIcon = BitmapFactory.decodeStream(in);
            publishProgress("Loading Done!");
            Thread.sleep(2000);
            return  mIcon;
        }catch (Exception ex){
            Log.e(DownloadFileAsyncTask.class.getSimpleName(),ex.getMessage());
        }
        return null;
    }
}
