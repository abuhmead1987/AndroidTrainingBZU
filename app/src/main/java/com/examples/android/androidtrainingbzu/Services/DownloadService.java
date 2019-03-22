package com.examples.android.androidtrainingbzu.Services;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * helper methods.
 */
public class DownloadService extends IntentService {

    private int result = Activity.RESULT_CANCELED;
    public static final String URL = "urlpath";
    public static final String FILENAME = "filename";
    public static final String FILEPATH = "filepath";
    public static final String RESULT = "result";
    public static final String NOTIFICATION = "Brodcasts.DownloadFileFinishedReceiver";
    private static final String TAG = "Download Task";
    public DownloadService() {
        super("DownloadService");
    }

    // will be called asynchronously by Android
    @Override
    protected void onHandleIntent(Intent intent) {

        String urlPath = intent.getStringExtra(URL);
        String fileName = intent.getStringExtra(FILENAME);
        String folder;
        int count;
        try {
            java.net.URL url = new URL(urlPath);
            URLConnection connection = url.openConnection();
            connection.connect();
            // getting file length
            int lengthOfFile = connection.getContentLength();
            // input stream to read file - with 8k buffer
            InputStream input = new BufferedInputStream(url.openStream(), 8192);
            //External directory path to save file
            folder = Environment.getExternalStorageDirectory() + File.separator;
            //Create a folder if it does not exist i cache directory
//            File directory = new File(getCacheDir().getAbsolutePath());
//            if (!directory.exists()) {
//                directory.mkdirs();
//            }
            // Output stream to write file
            OutputStream output = new FileOutputStream(folder + fileName);
            byte data[] = new byte[1024];
            long total = 0;
            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress, if you use asynctask
//                Log.d(TAG, "Progress: " + (int) ((total * 100) / lengthOfFile));
                // writing data to file
                output.write(data, 0, count);
            }
            // flushing output
            output.flush();
            // closing streams
            output.close();
            input.close();
            result = Activity.RESULT_OK;
            publishResults(folder + fileName, result);
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
            publishResults("", Activity.RESULT_CANCELED);
        }
    }

    private void publishResults(String outputPath, int result) {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(FILEPATH, outputPath);
        intent.putExtra(RESULT, result);
        sendBroadcast(intent);
    }
}