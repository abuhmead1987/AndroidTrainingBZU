package com.examples.android.androidtrainingbzu;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.examples.android.androidtrainingbzu.Utils.DownloadFileAsyncTask;
import com.examples.android.androidtrainingbzu.Utils.TaskLoader;
import com.examples.android.androidtrainingbzu.Utils.Utils;


public class LoadImagesAsyncAndLoaderActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Bitmap> {

    TextView txtvu_loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_images_async_and_loader);
        txtvu_loader = findViewById(R.id.txtvu_loader);

    }

    public void loadImageFromWeb(View view) {
        DownloadFileAsyncTask downloadFileAsyncTask = new DownloadFileAsyncTask((ImageView) findViewById(R.id.img_frominternet),
                (TextView) findViewById(R.id.txtvu_progessresult), (ProgressBar) findViewById(R.id.progressBar));
        downloadFileAsyncTask.execute("https://www.android.com/static/2016/img/hero-carousel/banner-android-p-2.jpg");

    }

    public void loadFromLoader(View view) {
        /**
         * getSupportLoaderManager(The ID of the loader, if you have multiple loaders yeas different ID for each,
         * Bundle parameter to pass bundle of data to the Loader to deal with,
         * Callbacks Object which will be notified whn the loader started, processing, done...
         * to use the call back methods by implement "LoaderManager.LoaderCallbacks<The Return Data type>" in you Activity or create an object of that interface
         * then use "forceLoad()" to force the loader starts its lifecycle
         */
        Bundle dataBundle = new Bundle();
        dataBundle.putString("Url", "https://www.android.com/static/2016/img/hero-carousel/banner-android-p-2.jpg");
        if (Utils.isDeviceConnectedInternet(this))
            LoaderManager.getInstance(this).initLoader(0, dataBundle, this).forceLoad();
        else
            txtvu_loader.setText("Not connected to the Internet");
        /**
         * Or use the following to restart the loader if its previously created
         */
        //getSupportLoaderManager().restartLoader (0, dataBundle,this ).forceLoad();
    }

    public void reLoadFromLoader(View view) {
        Bundle dataBundle = new Bundle();
        dataBundle.putString("Url", "https://www.android.com/static/2016/img/hero-carousel/banner-android-p-2.jpg");
        /**
         * use the following to restart the loader if its previously created
         */
        if (Utils.isDeviceConnectedInternet(this))
            LoaderManager.getInstance(this).restartLoader(0, dataBundle, this).forceLoad();
        else
            txtvu_loader.setText("Not connected to the Internet");
    }

    @NonNull
    @Override
    public Loader<Bitmap> onCreateLoader(int id, @Nullable Bundle args) {
        txtvu_loader.setText("The loader created");
        //return new object of Taskloader and pass the url to it
        return new TaskLoader(this, args.getString("Url"));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Bitmap> loader, Bitmap bitmap) {
        ((ImageView) findViewById(R.id.img_load)).setImageBitmap(bitmap);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Bitmap> loader) {
        txtvu_loader.setText("The loader : " + loader.getId() + ", rest called");
    }
}
