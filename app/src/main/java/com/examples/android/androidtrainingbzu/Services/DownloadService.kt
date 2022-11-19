package com.examples.android.androidtrainingbzu.Services

import android.app.Activity
import android.app.IntentService
import android.content.Intent
import android.os.Environment
import android.util.Log
import java.io.*
import java.net.URL

/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 *
 *
 * helper methods.
 */
class DownloadService : IntentService("DownloadService") {
    private var result = Activity.RESULT_CANCELED

    // will be called asynchronously by Android
    override fun onHandleIntent(intent: Intent?) {
        val urlPath = intent!!.getStringExtra(URL)
        val fileName = intent.getStringExtra(FILENAME)
        val folder: String
        var count: Int
        try {
            val url = URL(urlPath)
            val connection = url.openConnection()
            connection.connect()
            // getting file length
            val lengthOfFile = connection.contentLength
            // input stream to read file - with 8k buffer
            val input: InputStream = BufferedInputStream(url.openStream(), 8192)
            //External directory path to save file
            folder = Environment.getExternalStorageDirectory().toString() + File.separator
            //Create a folder if it does not exist i cache directory
//            File directory = new File(getCacheDir().getAbsolutePath());
//            if (!directory.exists()) {
//                directory.mkdirs();
//            }
            // Output stream to write file
            val output: OutputStream = FileOutputStream(folder + fileName)
            val data = ByteArray(1024)
            var total: Long = 0
            while (input.read(data).also { count = it } != -1) {
                total += count.toLong()
                // publishing the progress, if you use asynctask
//                Log.d(TAG, "Progress: " + (int) ((total * 100) / lengthOfFile));
                // writing data to file
                output.write(data, 0, count)
            }
            // flushing output
            output.flush()
            // closing streams
            output.close()
            input.close()
            result = Activity.RESULT_OK
            publishResults(folder + fileName, result)
        } catch (e: Exception) {
            Log.e("Error: ", e.message!!)
            publishResults("", Activity.RESULT_CANCELED)
        }
    }

    private fun publishResults(outputPath: String, result: Int) {
        val intent = Intent(NOTIFICATION)
        intent.putExtra(FILEPATH, outputPath)
        intent.putExtra(RESULT, result)
        sendBroadcast(intent)
    }

    companion object {
        const val URL = "urlpath"
        const val FILENAME = "filename"
        const val FILEPATH = "filepath"
        const val RESULT = "result"
        const val NOTIFICATION = "Brodcasts.DownloadFileFinishedReceiver"
        private const val TAG = "Download Task"
    }
}