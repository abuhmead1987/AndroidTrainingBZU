package com.examples.android.androidtrainingbzu.Utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import java.net.HttpURLConnection
import java.net.URL

class DownloadFileAsyncTask(
    var imgView: ImageView,
    var textView: TextView,
    var progressBar: ProgressBar,
) : AsyncTask<String?, String?, Bitmap?>() {
    override fun onPreExecute() {
        //imgView.setImageResource(R.drawable.loading_image);
        /**
         * To play gif file in image view we use third party library like Glide
         * to read more and see the documentation follow the link bellow
         * https://github.com/bumptech/glide
         */
        //  Glide.with(imgView.getContext()).load(R.drawable.loading_image).into(imgView);
        progressBar.visibility = View.VISIBLE
    }

    override fun onPostExecute(bitmap: Bitmap?) {
        progressBar.visibility = View.GONE
        imgView.visibility = View.VISIBLE
        imgView.setImageBitmap(bitmap)
    }

    override fun onProgressUpdate(vararg values: String?) {
        textView.text = "" + values[0]
    }

    override fun doInBackground(vararg strings: String?): Bitmap? {
        try {
            publishProgress("doInBackground started ...")
            Thread.sleep(2000)
            val url = URL(strings[0])
            val urlcon = url.openConnection() as HttpURLConnection
            publishProgress("Open the connection ...")
            Thread.sleep(2000)
            urlcon.doInput = true
            urlcon.connect()
            publishProgress("Connected...")
            Thread.sleep(2000)
            val `in` = urlcon.inputStream
            publishProgress("Input stream getted ...")
            Thread.sleep(2000)
            val mIcon = BitmapFactory.decodeStream(`in`)
            publishProgress("Loading Done!")
            Thread.sleep(2000)
            return mIcon
        } catch (ex: Exception) {
            Log.e(DownloadFileAsyncTask::class.java.simpleName, ex.message!!)
        }
        return null
    }
}