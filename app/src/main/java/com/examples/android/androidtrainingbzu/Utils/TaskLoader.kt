package com.examples.android.androidtrainingbzu.Utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.loader.content.AsyncTaskLoader
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class TaskLoader(context: Context, var murl: String?) : AsyncTaskLoader<Bitmap?>(context) {
    override fun loadInBackground(): Bitmap? {
        var conn: HttpURLConnection? = null
        var isr: InputStream? = null
        try {
            val requestURL = URL(murl)
            conn = requestURL.openConnection() as HttpURLConnection
            conn!!.readTimeout = 10000
            conn.connectTimeout = 15000
            conn.requestMethod = "GET"
            conn.doInput = true
            conn.connect()
            return if (conn.responseCode == HttpsURLConnection.HTTP_OK) {
                isr = conn.inputStream
                BitmapFactory.decodeStream(isr)
            } else {
                null
            }
        } catch (ex: Exception) {
            Log.e(DownloadFileAsyncTask::class.java.simpleName, ex.message!!)
        } finally {
            conn?.disconnect()
            if (isr != null) {
                try {
                    isr.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return null
    }
}