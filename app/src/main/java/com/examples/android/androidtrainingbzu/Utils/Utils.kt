package com.examples.android.androidtrainingbzu.Utils

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.os.Environment
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    private const val mPattern = "dd.MM.yyyy"
    fun getDateFromString(stringDate: String?, pattern: String?): Date? {
        val df: DateFormat = SimpleDateFormat(pattern)
        var startDate: Date
        return try {
            df.parse(stringDate)
        } catch (exx: ParseException) {
            null
        }
    }

    fun getDateFromString(stringDate: String?): Date? {
        return getDateFromString(stringDate, mPattern)
    }

    fun getDateFormatedString(stringDate: Date?, pattern: String?): String? {
        return try {
            SimpleDateFormat(pattern).format(stringDate)
        } catch (e: Exception) {
            null
        }
    }

    fun getDateFormatedString(stringDate: Date?): String? {
        return getDateFormatedString(stringDate, mPattern)
    }

    fun isDeviceConnectedInternet(context: Context): Boolean {
        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    fun getImageFromBath(path: String?): Bitmap? {
        val imgFile = File(path)
        return if (imgFile.exists()) {
            BitmapFactory.decodeFile(imgFile.absolutePath)
        } else null
    }

    val isSDCardPresent: Boolean
        get() = Environment.getExternalStorageState() ==
                Environment.MEDIA_MOUNTED

    fun checkAndRequestPermissions(
        appPermissions: Array<String>,
        activity: Activity?,
        requestCode: Int,
    ): Boolean {
/// Check which permissions are granted
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        for (perm in appPermissions) {
            if (ContextCompat.checkSelfPermission(activity!!,
                    perm) != PackageManager.PERMISSION_GRANTED
            ) {
                listPermissionsNeeded.add(perm)
            }
        }
        // Ask for non-granted permissions
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(activity!!, listPermissionsNeeded.toTypedArray(),
                requestCode
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
        activity: Activity?,
    ): AlertDialog {
        val builder = AlertDialog.Builder(
            activity!!)
        builder.setTitle(title)
        builder.setCancelable(isCancelAble)
        builder.setMessage(msg)
        builder.setPositiveButton(positiveLabel, positiveOnClick)
        builder.setNegativeButton(negativeLabel, negativeOnClick)
        val alert = builder.create()
        alert.show()
        return alert
    }
}