package com.examples.android.androidtrainingbzu;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomDownloadManagerActivity extends AppCompatActivity implements View.OnClickListener {

    private final int PERMISSIONS_REQUEST_CODE = 1;
    String[] appPermissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private DownloadManager downloadManager;
    private long Image_DownloadId, AndroidBook_DownloadId;
    private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            //check if the broadcast message is for our Enqueued download
            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            String filePath = intent.getStringExtra(DownloadManager.COLUMN_LOCAL_URI);
            if (referenceId == Image_DownloadId) {

                Toast toast = Toast.makeText(CustomDownloadManagerActivity.this,
                        "Image Download Complete, " + filePath, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();
            } else if (referenceId == AndroidBook_DownloadId) {

                Toast toast = Toast.makeText(CustomDownloadManagerActivity.this,
                        "AndroidBook Download Complete, " + filePath, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_download_manager);

        //Download Image from URL
        Button DownloadImage = (Button) findViewById(R.id.DownloadImage);
        DownloadImage.setOnClickListener(this);

        //Download AndroidBook from URL
        Button DownloadAndroidBook = (Button) findViewById(R.id.DownloadAndroidBook);
        DownloadAndroidBook.setOnClickListener(this);

        //Check Download status
        Button DownloadStatus = (Button) findViewById(R.id.DownloadStatus);
        DownloadStatus.setOnClickListener(this);
        DownloadStatus.setEnabled(false);

        //Cancel Current Download
        Button CancelDownload = (Button) findViewById(R.id.CancelDownload);
        CancelDownload.setOnClickListener(this);
        CancelDownload.setEnabled(false);

        //set filter to only when download is complete and register broadcast receiver
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(downloadReceiver, filter);
        checkAndRequestPermissions();
    }

    public boolean checkAndRequestPermissions() {
// Check which permissions are granted
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String perm : appPermissions) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(perm);
            }
        }
// Ask for non-granted permissions
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    PERMISSIONS_REQUEST_CODE
            );
            return false;
        }
// App has all permissions. Proceed ahead
        return true;
    }

    public AlertDialog showDialog(String title, String msg, String positiveLabel, DialogInterface.OnClickListener positiveOnClick, String negativeLabel,
                                  DialogInterface.OnClickListener negativeOnClick, boolean isCancelAble) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setCancelable(isCancelAble);
        builder.setMessage(msg);
        builder.setPositiveButton(positiveLabel, positiveOnClick);
        builder.setNegativeButton(negativeLabel, negativeOnClick);
        AlertDialog alert = builder.create();
        alert.show();
        return alert;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            HashMap<String, Integer> permissionResults = new HashMap<>();
            int deniedCount = 0;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    permissionResults.put(permissions[i], grantResults[i]);
                    deniedCount++;
                }
            }
            if (deniedCount == 0){
                //Start the piece of code which use the permission
            }
            else {
                for (Map.Entry<String, Integer > entry :permissionResults.entrySet()){
                    String permName = entry.getKey();
                   int permResult = entry.getValue();
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, permName)){
                        showDialog("", "This app needs Location and Storage permissions to work wihout and problems.", "Yes, Grant permissions",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        checkAndRequestPermissions();
                                    }
                                },"No, Exit app",

                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        finish();
                                    }
                                }, false);
                    }else{
                        showDialog("You have denied some permissions. Allow all permissions at [Setting] > [Permissions]",
                                "Go to Settings", "Yes, Grant permissions",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", getPackageName(), null));
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); startActivity( intent);
                                        finish();
                                    }
                                },"No, Exit app",

                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        finish();
                                    }
                                }, false);
                    }
                }

            }

        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            //Download Image
            case R.id.DownloadImage:
                Uri image_uri = Uri.parse("https://images.idgesg.net/images/article/2017/08/android_robot_logo_by_ornecolorada_cc0_via_pixabay1904852_wide-100732483-large.jpg");
                Image_DownloadId = DownloadData(image_uri, v);
                break;

            //Download AndroidBook
            case R.id.DownloadAndroidBook:
                Uri AndroidBook_uri = Uri.parse("http://enos.itcollege.ee/~jpoial/allalaadimised/reading/Android-Programming-Cookbook.pdf");//"https://fsb.zobj.net/download/b8hQnFyk7WHPUig8NCHyJNiBxks3qW2ab8lEerF9WxqOw32ZA3ra1RoNuX_D-c0QulPwmYjuDi4-8k6uzyDdhs3DGZxz87OE0lKm3ae8PC7nC8xLG3umKSJwUpfs/?a=web&c=72&f=turkish_tone.mp3&special=1553197754-Vul1WpZgm2thWl%2BF7WuRo6OCh7g%2B%2BGQAwo0orUG3%2Fko%3D");
                AndroidBook_DownloadId = DownloadData(AndroidBook_uri, v);
                break;

            //check the status of all downloads
            case R.id.DownloadStatus:

                Check_Image_Status(Image_DownloadId);
                Check_AndroidBook_Status(AndroidBook_DownloadId);

                break;

            //cancel the ongoing download
            case R.id.CancelDownload:

                downloadManager.remove(Image_DownloadId);
                downloadManager.remove(AndroidBook_DownloadId);
                break;

        }
    }

    private void Check_Image_Status(long Image_DownloadId) {

        DownloadManager.Query ImageDownloadQuery = new DownloadManager.Query();
        //set the query filter to our previously Enqueued download
        ImageDownloadQuery.setFilterById(Image_DownloadId);

        //Query the download manager about downloads that have been requested.
        Cursor cursor = downloadManager.query(ImageDownloadQuery);
        if (cursor.moveToFirst()) {
            DownloadStatus(cursor, Image_DownloadId);
        }

    }

    private void Check_AndroidBook_Status(long AndroidBook_DownloadId) {

        DownloadManager.Query AndroidBookDownloadQuery = new DownloadManager.Query();
        //set the query filter to our previously Enqueued download
        AndroidBookDownloadQuery.setFilterById(AndroidBook_DownloadId);

        //Query the download manager about downloads that have been requested.
        Cursor cursor = downloadManager.query(AndroidBookDownloadQuery);
        if (cursor.moveToFirst()) {
            DownloadStatus(cursor, AndroidBook_DownloadId);
        }

    }

    private void DownloadStatus(Cursor cursor, long DownloadId) {

        //column for download  status
        int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
        int status = cursor.getInt(columnIndex);
        //column for reason code if the download failed or paused
        int columnReason = cursor.getColumnIndex(DownloadManager.COLUMN_REASON);
        int reason = cursor.getInt(columnReason);
        //get the download filename

        // int filenameIndex = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
        //String filename = downloadManager.getUriForDownloadedFile(DownloadId).getPath();//cursor.getString(filenameIndex);

        String statusText = "";
        String reasonText = "";

        switch (status) {
            case DownloadManager.STATUS_FAILED:
                statusText = "STATUS_FAILED";
                switch (reason) {
                    case DownloadManager.ERROR_CANNOT_RESUME:
                        reasonText = "ERROR_CANNOT_RESUME";
                        break;
                    case DownloadManager.ERROR_DEVICE_NOT_FOUND:
                        reasonText = "ERROR_DEVICE_NOT_FOUND";
                        break;
                    case DownloadManager.ERROR_FILE_ALREADY_EXISTS:
                        reasonText = "ERROR_FILE_ALREADY_EXISTS";
                        break;
                    case DownloadManager.ERROR_FILE_ERROR:
                        reasonText = "ERROR_FILE_ERROR";
                        break;
                    case DownloadManager.ERROR_HTTP_DATA_ERROR:
                        reasonText = "ERROR_HTTP_DATA_ERROR";
                        break;
                    case DownloadManager.ERROR_INSUFFICIENT_SPACE:
                        reasonText = "ERROR_INSUFFICIENT_SPACE";
                        break;
                    case DownloadManager.ERROR_TOO_MANY_REDIRECTS:
                        reasonText = "ERROR_TOO_MANY_REDIRECTS";
                        break;
                    case DownloadManager.ERROR_UNHANDLED_HTTP_CODE:
                        reasonText = "ERROR_UNHANDLED_HTTP_CODE";
                        break;
                    case DownloadManager.ERROR_UNKNOWN:
                        reasonText = "ERROR_UNKNOWN";
                        break;
                }
                break;
            case DownloadManager.STATUS_PAUSED:
                statusText = "STATUS_PAUSED";
                switch (reason) {
                    case DownloadManager.PAUSED_QUEUED_FOR_WIFI:
                        reasonText = "PAUSED_QUEUED_FOR_WIFI";
                        break;
                    case DownloadManager.PAUSED_UNKNOWN:
                        reasonText = "PAUSED_UNKNOWN";
                        break;
                    case DownloadManager.PAUSED_WAITING_FOR_NETWORK:
                        reasonText = "PAUSED_WAITING_FOR_NETWORK";
                        break;
                    case DownloadManager.PAUSED_WAITING_TO_RETRY:
                        reasonText = "PAUSED_WAITING_TO_RETRY";
                        break;
                }
                break;
            case DownloadManager.STATUS_PENDING:
                statusText = "STATUS_PENDING";
                break;
            case DownloadManager.STATUS_RUNNING:
                statusText = "STATUS_RUNNING";
                break;
            case DownloadManager.STATUS_SUCCESSFUL:
                statusText = "STATUS_SUCCESSFUL";
                reasonText = "Filename:\n" + cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_URI));
                ;
                break;
        }

        if (DownloadId == AndroidBook_DownloadId) {

            Toast toast = Toast.makeText(CustomDownloadManagerActivity.this,
                    "AndroidBook Download Status:" + "\n" + statusText + "\n" +
                            reasonText,
                    Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();

        } else {

            Toast toast = Toast.makeText(CustomDownloadManagerActivity.this,
                    "Image Download Status:" + "\n" + statusText + "\n" +
                            reasonText,
                    Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();

            // Make a delay of 3 seconds so that next toast (AndroidBook Status) will not merge with this one.
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                }
            }, 3000);

        }

    }

    private long DownloadData(Uri uri, View v) {

        long downloadReference = 10;

        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);


        DownloadManager.Request request=new DownloadManager.Request(uri)
                .setTitle("Dummy File")// Title of the Download Notification
                .setDescription("Downloading");// Description of the Download Notification

        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE).setAllowedOverRoaming(true);
        request.setAllowedOverRoaming(false);
        request.setVisibleInDownloadsUi(true);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE|DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);// Visibility of the download Notification
                //.setDestinationUri(Uri.fromFile(file));// Uri of the destination file
//                .setRequiresCharging(false)// Set if charging is required to begin the download
//                .setAllowedOverMetered(true)// Set if download is allowed on Mobile network
//                .setAllowedOverRoaming(true);// Set if download is allowed on roaming network
        //Setting title of request
        request.setTitle("Data Download");

        //Setting description of request
        request.setDescription("Android Data download using DownloadManager.");

        //Set the local destination for the downloaded file to a path within the application's external files directory
        if(v.getId() == R.id.DownloadAndroidBook)
            request.setDestinationInExternalFilesDir(CustomDownloadManagerActivity.this, Environment.DIRECTORY_DOWNLOADS,"android_dev_book.pdf");
        else if(v.getId() == R.id.DownloadImage)
            request.setDestinationInExternalFilesDir(CustomDownloadManagerActivity.this, Environment.DIRECTORY_DOWNLOADS,"jpg_image.jpg");

        //Enqueue download and save the referenceId
        downloadReference = downloadManager.enqueue(request);

        Button DownloadStatus = (Button) findViewById(R.id.DownloadStatus);
        DownloadStatus.setEnabled(true);
        Button CancelDownload = (Button) findViewById(R.id.CancelDownload);
        CancelDownload.setEnabled(true);


//        DownloadManager.Request request = new DownloadManager.Request(uri);
//
//        //allow download to take place over wifi, mobile network and roaming
//
//
//        //name to show while downloading
//        request.setTitle("file name");
//
//        //description to show while downloading
//        request.setDescription("Downloading file name");
//
//        //show on navigation
//
//
//        //download path
//        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS.toString(), "/" + "FileName");
//
//        //file open when item on navigation is clicked
//        request.allowScanningByMediaScanner();
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//        long downloadId = downloadManager.enqueue(request);
        return downloadReference;
    }


}
