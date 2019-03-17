package com.examples.android.androidtrainingbzu.Utils;

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

}
