package com.examples.android.androidtrainingbzu.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    private static String mPattern="dd.MM.yyyy";
    public static Date getDateFromString(String stringDate, String pattern){
        DateFormat df = new SimpleDateFormat(pattern);
        Date startDate;
        try {
            return df.parse(stringDate);
        } catch (ParseException exx) {
            return null;
        }
    }
    public static Date getDateFromString(String stringDate){
        return getDateFromString(stringDate,mPattern);
    }

    public static String getDateFormatedString(Date stringDate, String pattern){
        try {
            return new SimpleDateFormat(pattern).format(stringDate);
        }catch (Exception e){
            return null;
        }
    }
    public static String getDateFormatedString(Date stringDate){
        return getDateFormatedString(stringDate,mPattern);
    }

    public static boolean isDeviceConnectedInternet(Context context){
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
    public static Bitmap getImageFromBath(String path){
        File imgFile = new  File(path);
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            return myBitmap;
        }else
            return null;
    }
    public  static boolean isSDCardPresent() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

}
