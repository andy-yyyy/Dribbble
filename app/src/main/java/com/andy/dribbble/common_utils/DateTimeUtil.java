/*
 * Copyright (C) 2017 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package com.andy.dribbble.common_utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by lixn on 2017/9/4.
 */

public class DateTimeUtil {

    public static final String PATTERN_SERVER = "yyyy-MM-dd'T'hh:mm:ss'Z'";
    public static final String PATTERN_LOCAL_DATE = "yyyy-MM-dd";
    public static final String PATTERN_LOCAL_TIME = "hh:mm:ss";

    public static final int DELTA_MIN = 60;
    public static final int DELTA_HOUR = DELTA_MIN*60;
    public static final int DELTA_DAY = DELTA_HOUR*24;
    public static final int DELTA_MONTH = DELTA_DAY*30;

    public static String formatDate(String dateStr) {
        Date date = parseDateFromServer(dateStr);
        if (date == null) {
            return "";
        }
        Date now = new Date();
        long delta = (now.getTime() - date.getTime()) / 1000;  // 距离目前的时间（秒）
        if (delta < DELTA_MIN) {
            return getFriendlyTime((int) delta, "second");
        } else if (delta <DELTA_HOUR) {
            int m = (int) (delta / DELTA_MIN);
            return getFriendlyTime(m, "minute");
        } else if (delta < DELTA_DAY) {
            int h = (int) (delta / DELTA_HOUR);
            return getFriendlyTime(h, "hour");
        } else if (delta < DELTA_MONTH){
            int d = (int) (delta / DELTA_DAY);
            return getFriendlyTime(d, "day");
        } else {
            SimpleDateFormat df = new SimpleDateFormat(PATTERN_LOCAL_DATE, Locale.getDefault());
            return df.format(date);
        }
    }

    private static String getFriendlyTime(int num, String unit) {
        if (num ==1) {
            return num + " " + unit + " ago";
        } else if (num > 1) {
            return num + " " + unit + "s ago";
        } else {
            return "";
        }
    }
    public static String formatTime(String dateStr) {
        Date date = parseDateFromServer(dateStr);
        if (date == null) {
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat(PATTERN_LOCAL_TIME, Locale.getDefault());
        return df.format(date);
    }

    /**
     * 将服务器的时间格式解析成Date
     * @param dateStr
     * @return
     */
    public static Date parseDateFromServer(String dateStr) {
        SimpleDateFormat df = new SimpleDateFormat(PATTERN_SERVER, Locale.getDefault());
        try {
            return df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
