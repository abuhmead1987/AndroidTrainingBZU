package com.examples.android.androidtrainingbzu

import android.Manifest
import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.*
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.Settings
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class CustomDownloadManagerActivity : AppCompatActivity(), View.OnClickListener {
    private val PERMISSIONS_REQUEST_CODE = 1
    var appPermissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private var downloadManager: DownloadManager? = null
    private var Image_DownloadId: Long = 0
    private var AndroidBook_DownloadId: Long = 0
    private val downloadReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

            //check if the broadcast message is for our Enqueued download
            val referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            val filePath = intent.getStringExtra(DownloadManager.COLUMN_LOCAL_URI)
            if (referenceId == Image_DownloadId) {
                val toast = Toast.makeText(this@CustomDownloadManagerActivity,
                    "Image Download Complete, $filePath", Toast.LENGTH_LONG)
                toast.setGravity(Gravity.TOP, 25, 400)
                toast.show()
            } else if (referenceId == AndroidBook_DownloadId) {
                val toast = Toast.makeText(this@CustomDownloadManagerActivity,
                    "AndroidBook Download Complete, $filePath", Toast.LENGTH_LONG)
                toast.setGravity(Gravity.TOP, 25, 400)
                toast.show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_download_manager)

        //Download Image from URL
        val DownloadImage = findViewById<View>(R.id.DownloadImage) as Button
        DownloadImage.setOnClickListener(this)

        //Download AndroidBook from URL
        val DownloadAndroidBook = findViewById<View>(R.id.DownloadAndroidBook) as Button
        DownloadAndroidBook.setOnClickListener(this)

        //Check Download status
        val DownloadStatus = findViewById<View>(R.id.DownloadStatus) as Button
        DownloadStatus.setOnClickListener(this)
        DownloadStatus.isEnabled = false

        //Cancel Current Download
        val CancelDownload = findViewById<View>(R.id.CancelDownload) as Button
        CancelDownload.setOnClickListener(this)
        CancelDownload.isEnabled = false

        //set filter to only when download is complete and register broadcast receiver
        val filter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        registerReceiver(downloadReceiver, filter)
        checkAndRequestPermissions()
    }

    fun checkAndRequestPermissions(): Boolean {
// Check which permissions are granted
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        for (perm in appPermissions) {
            if (ContextCompat.checkSelfPermission(this,
                    perm) != PackageManager.PERMISSION_GRANTED
            ) {
                listPermissionsNeeded.add(perm)
            }
        }
        // Ask for non-granted permissions
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toTypedArray(),
                PERMISSIONS_REQUEST_CODE
            )
            return false
        }
        // App has all permissions. Proceed ahead
        return true
    }

    fun showDialog(
        title: String?,
        msg: String?,
        positiveLabel: String?,
        positiveOnClick: DialogInterface.OnClickListener?,
        negativeLabel: String?,
        negativeOnClick: DialogInterface.OnClickListener?,
        isCancelAble: Boolean,
    ): AlertDialog {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setCancelable(isCancelAble)
        builder.setMessage(msg)
        builder.setPositiveButton(positiveLabel, positiveOnClick)
        builder.setNegativeButton(negativeLabel, negativeOnClick)
        val alert = builder.create()
        alert.show()
        return alert
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            val permissionResults = HashMap<String, Int>()
            var deniedCount = 0
            for (i in grantResults.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    permissionResults[permissions[i]] = grantResults[i]
                    deniedCount++
                }
            }
            if (deniedCount == 0) {
                //Start the piece of code which use the permission
            } else {
                for ((permName, permResult) in permissionResults) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, permName)) {
                        showDialog("",
                            "This app needs Location and Storage permissions to work wihout and problems.",
                            "Yes, Grant permissions",
                            { dialogInterface, i ->
                                dialogInterface.dismiss()
                                checkAndRequestPermissions()
                            },
                            "No, Exit app",
                            { dialogInterface, i ->
                                dialogInterface.dismiss()
                                finish()
                            },
                            false)
                    } else {
                        showDialog("You have denied some permissions. Allow all permissions at [Setting] > [Permissions]",
                            "Go to Settings", "Yes, Grant permissions",
                            { dialogInterface, i ->
                                dialogInterface.dismiss()
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.fromParts("package", packageName, null))
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                                finish()
                            }, "No, Exit app",
                            { dialogInterface, i ->
                                dialogInterface.dismiss()
                                finish()
                            }, false)
                    }
                }
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.DownloadImage -> {
                val image_uri =
                    Uri.parse("https://images.idgesg.net/images/article/2017/08/android_robot_logo_by_ornecolorada_cc0_via_pixabay1904852_wide-100732483-large.jpg")
                Image_DownloadId = DownloadData(image_uri, v)
            }
            R.id.DownloadAndroidBook -> {
                val AndroidBook_uri =
                    Uri.parse("http://enos.itcollege.ee/~jpoial/allalaadimised/reading/Android-Programming-Cookbook.pdf") //"https://fsb.zobj.net/download/b8hQnFyk7WHPUig8NCHyJNiBxks3qW2ab8lEerF9WxqOw32ZA3ra1RoNuX_D-c0QulPwmYjuDi4-8k6uzyDdhs3DGZxz87OE0lKm3ae8PC7nC8xLG3umKSJwUpfs/?a=web&c=72&f=turkish_tone.mp3&special=1553197754-Vul1WpZgm2thWl%2BF7WuRo6OCh7g%2B%2BGQAwo0orUG3%2Fko%3D");
                AndroidBook_DownloadId = DownloadData(AndroidBook_uri, v)
            }
            R.id.DownloadStatus -> {
                Check_Image_Status(Image_DownloadId)
                Check_AndroidBook_Status(AndroidBook_DownloadId)
            }
            R.id.CancelDownload -> {
                downloadManager!!.remove(Image_DownloadId)
                downloadManager!!.remove(AndroidBook_DownloadId)
            }
        }
    }

    private fun Check_Image_Status(Image_DownloadId: Long) {
        val ImageDownloadQuery = DownloadManager.Query()
        //set the query filter to our previously Enqueued download
        ImageDownloadQuery.setFilterById(Image_DownloadId)

        //Query the download manager about downloads that have been requested.
        val cursor = downloadManager!!.query(ImageDownloadQuery)
        if (cursor.moveToFirst()) {
            DownloadStatus(cursor, Image_DownloadId)
        }
    }

    private fun Check_AndroidBook_Status(AndroidBook_DownloadId: Long) {
        val AndroidBookDownloadQuery = DownloadManager.Query()
        //set the query filter to our previously Enqueued download
        AndroidBookDownloadQuery.setFilterById(AndroidBook_DownloadId)

        //Query the download manager about downloads that have been requested.
        val cursor = downloadManager!!.query(AndroidBookDownloadQuery)
        if (cursor.moveToFirst()) {
            DownloadStatus(cursor, AndroidBook_DownloadId)
        }
    }

    @SuppressLint("Range")
    private fun DownloadStatus(cursor: Cursor, DownloadId: Long) {

        //column for download  status
        val columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
        val status = cursor.getInt(columnIndex)
        //column for reason code if the download failed or paused
        val columnReason = cursor.getColumnIndex(DownloadManager.COLUMN_REASON)
        val reason = cursor.getInt(columnReason)
        //get the download filename

        // int filenameIndex = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
        //String filename = downloadManager.getUriForDownloadedFile(DownloadId).getPath();//cursor.getString(filenameIndex);
        var statusText = ""
        var reasonText = ""
        when (status) {
            DownloadManager.STATUS_FAILED -> {
                statusText = "STATUS_FAILED"
                when (reason) {
                    DownloadManager.ERROR_CANNOT_RESUME -> reasonText = "ERROR_CANNOT_RESUME"
                    DownloadManager.ERROR_DEVICE_NOT_FOUND -> reasonText = "ERROR_DEVICE_NOT_FOUND"
                    DownloadManager.ERROR_FILE_ALREADY_EXISTS -> reasonText =
                        "ERROR_FILE_ALREADY_EXISTS"
                    DownloadManager.ERROR_FILE_ERROR -> reasonText = "ERROR_FILE_ERROR"
                    DownloadManager.ERROR_HTTP_DATA_ERROR -> reasonText = "ERROR_HTTP_DATA_ERROR"
                    DownloadManager.ERROR_INSUFFICIENT_SPACE -> reasonText =
                        "ERROR_INSUFFICIENT_SPACE"
                    DownloadManager.ERROR_TOO_MANY_REDIRECTS -> reasonText =
                        "ERROR_TOO_MANY_REDIRECTS"
                    DownloadManager.ERROR_UNHANDLED_HTTP_CODE -> reasonText =
                        "ERROR_UNHANDLED_HTTP_CODE"
                    DownloadManager.ERROR_UNKNOWN -> reasonText = "ERROR_UNKNOWN"
                }
            }
            DownloadManager.STATUS_PAUSED -> {
                statusText = "STATUS_PAUSED"
                when (reason) {
                    DownloadManager.PAUSED_QUEUED_FOR_WIFI -> reasonText = "PAUSED_QUEUED_FOR_WIFI"
                    DownloadManager.PAUSED_UNKNOWN -> reasonText = "PAUSED_UNKNOWN"
                    DownloadManager.PAUSED_WAITING_FOR_NETWORK -> reasonText =
                        "PAUSED_WAITING_FOR_NETWORK"
                    DownloadManager.PAUSED_WAITING_TO_RETRY -> reasonText =
                        "PAUSED_WAITING_TO_RETRY"
                }
            }
            DownloadManager.STATUS_PENDING -> statusText = "STATUS_PENDING"
            DownloadManager.STATUS_RUNNING -> statusText = "STATUS_RUNNING"
            DownloadManager.STATUS_SUCCESSFUL -> {
                statusText = "STATUS_SUCCESSFUL"
                reasonText = """
                    Filename:
                    ${cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_URI))}
                    """.trimIndent()
            }
        }
        if (DownloadId == AndroidBook_DownloadId) {
            val toast = Toast.makeText(this@CustomDownloadManagerActivity,
                """
                      AndroidBook Download Status:
                      $statusText
                      $reasonText
                      """.trimIndent(),
                Toast.LENGTH_LONG)
            toast.setGravity(Gravity.TOP, 25, 400)
            toast.show()
        } else {
            val toast = Toast.makeText(this@CustomDownloadManagerActivity,
                """
                      Image Download Status:
                      $statusText
                      $reasonText
                      """.trimIndent(),
                Toast.LENGTH_LONG)
            toast.setGravity(Gravity.TOP, 25, 400)
            toast.show()

            // Make a delay of 3 seconds so that next toast (AndroidBook Status) will not merge with this one.
            val handler = Handler()
            handler.postDelayed({ }, 3000)
        }
    }

    private fun DownloadData(uri: Uri, v: View): Long {
        var downloadReference: Long = 10
        downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        val request = DownloadManager.Request(uri)
            .setTitle("Dummy File") // Title of the Download Notification
            .setDescription("Downloading") // Description of the Download Notification
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setAllowedOverRoaming(true)
        request.setAllowedOverRoaming(false)
        request.setVisibleInDownloadsUi(true)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE or DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED) // Visibility of the download Notification
        //.setDestinationUri(Uri.fromFile(file));// Uri of the destination file
//                .setRequiresCharging(false)// Set if charging is required to begin the download
//                .setAllowedOverMetered(true)// Set if download is allowed on Mobile network
//                .setAllowedOverRoaming(true);// Set if download is allowed on roaming network
        //Setting title of request
        request.setTitle("Data Download")

        //Setting description of request
        request.setDescription("Android Data download using DownloadManager.")

        //Set the local destination for the downloaded file to a path within the application's external files directory
        if (v.id == R.id.DownloadAndroidBook) request.setDestinationInExternalFilesDir(this@CustomDownloadManagerActivity,
            Environment.DIRECTORY_DOWNLOADS,
            "android_dev_book.pdf") else if (v.id == R.id.DownloadImage) request.setDestinationInExternalFilesDir(
            this@CustomDownloadManagerActivity,
            Environment.DIRECTORY_DOWNLOADS,
            "jpg_image.jpg")

        //Enqueue download and save the referenceId
        downloadReference = downloadManager!!.enqueue(request)
        val DownloadStatus = findViewById<View>(R.id.DownloadStatus) as Button
        DownloadStatus.isEnabled = true
        val CancelDownload = findViewById<View>(R.id.CancelDownload) as Button
        CancelDownload.isEnabled = true


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
        return downloadReference
    }
}