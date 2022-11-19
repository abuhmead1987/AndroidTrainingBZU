package com.examples.android.androidtrainingbzu

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import com.examples.android.androidtrainingbzu.Utils.DownloadFileAsyncTask
import com.examples.android.androidtrainingbzu.Utils.TaskLoader
import com.examples.android.androidtrainingbzu.Utils.Utils

class LoadImagesAsyncAndLoaderActivity : AppCompatActivity(),
    LoaderManager.LoaderCallbacks<Bitmap> {
    var txtvu_loader: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_images_async_and_loader)
        txtvu_loader = findViewById(R.id.txtvu_loader)
    }

    fun loadImageFromWeb(view: View?) {
        val downloadFileAsyncTask =
            DownloadFileAsyncTask(findViewById<View>(R.id.img_frominternet) as ImageView,
                findViewById<View>(R.id.txtvu_progessresult) as TextView,
                findViewById<View>(R.id.progressBar) as ProgressBar)
        downloadFileAsyncTask.execute("https://www.android.com/static/2016/img/hero-carousel/banner-android-p-2.jpg")
    }

    fun loadFromLoader(view: View?) {
        /**
         * getSupportLoaderManager(The ID of the loader, if you have multiple loaders yeas different ID for each,
         * Bundle parameter to pass bundle of data to the Loader to deal with,
         * Callbacks Object which will be notified whn the loader started, processing, done...
         * to use the call back methods by implement "LoaderManager.LoaderCallbacks<The Return Data type>" in you Activity or create an object of that interface
         * then use "forceLoad()" to force the loader starts its lifecycle
        </The> */
        val dataBundle = Bundle()
        dataBundle.putString("Url",
            "https://www.android.com/static/2016/img/hero-carousel/banner-android-p-2.jpg")
        if (Utils.isDeviceConnectedInternet(this)) LoaderManager.getInstance(this)
            .initLoader(0, dataBundle, this).forceLoad() else txtvu_loader!!.text =
            "Not connected to the Internet"
        /**
         * Or use the following to restart the loader if its previously created
         */
        //getSupportLoaderManager().restartLoader (0, dataBundle,this ).forceLoad();
    }

    fun reLoadFromLoader(view: View?) {
        val dataBundle = Bundle()
        dataBundle.putString("Url",
            "https://www.android.com/static/2016/img/hero-carousel/banner-android-p-2.jpg")
        /**
         * use the following to restart the loader if its previously created
         */
        if (Utils.isDeviceConnectedInternet(this)) LoaderManager.getInstance(this)
            .restartLoader(0, dataBundle, this).forceLoad() else txtvu_loader!!.text =
            "Not connected to the Internet"
    }

    override fun onCreateLoader(id: Int, args: Bundle?): TaskLoader {
        txtvu_loader!!.text = "The loader created"
        //return new object of Taskloader and pass the url to it
        return TaskLoader(this, args!!.getString("Url"))
    }

    override fun onLoadFinished(loader: Loader<Bitmap>, bitmap: Bitmap) {
        (findViewById<View>(R.id.img_load) as ImageView).setImageBitmap(bitmap)
    }

    override fun onLoaderReset(loader: Loader<Bitmap>) {
        txtvu_loader!!.text = "The loader : " + loader.id + ", rest called"
    }
}