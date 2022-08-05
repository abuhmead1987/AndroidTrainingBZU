package com.examples.android.androidtrainingbzu.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Utils {

    private static String mPattern = "dd.MM.yyyy";

    public static Date getDateFromString(String stringDate, String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        Date startDate;
        try {
            return df.parse(stringDate);
        } catch (ParseException exx) {
            return null;
        }
    }

    public static Date getDateFromString(String stringDate) {
        return getDateFromString(stringDate, mPattern);
    }

    public static String getDateFormatedString(Date stringDate, String pattern) {
        try {
            return new SimpleDateFormat(pattern).format(stringDate);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getDateFormatedString(Date stringDate) {
        return getDateFormatedString(stringDate, mPattern);
    }

    public static boolean isDeviceConnectedInternet(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static Bitmap getImageFromBath(String path) {
        File imgFile = new File(path);
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            return myBitmap;
        } else
            return null;
    }

    public static boolean isSDCardPresent() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    public static boolean checkAndRequestPermissions(String[] appPermissions, Activity activity, int requestCode) {
/// Check which permissions are granted
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String perm : appPermissions) {
            if (ContextCompat.checkSelfPermission(activity, perm) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(perm);
            }
        }
// Ask for non-granted permissions
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(activity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    requestCode
            );
            return false;
        }
// App has all permissions. Proceed ahead
        return true;
    }

    public static AlertDialog showDialog(String title, String msg, String positiveLabel, DialogInterface.OnClickListener positiveOnClick, String negativeLabel,
                                  DialogInterface.OnClickListener negativeOnClick, boolean isCancelAble, Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);
        builder.setCancelable(isCancelAble);
        builder.setMessage(msg);
        builder.setPositiveButton(positiveLabel, positiveOnClick);
        builder.setNegativeButton(negativeLabel, negativeOnClick);
        AlertDialog alert = builder.create();
        alert.show();
        return alert;
    }
}
