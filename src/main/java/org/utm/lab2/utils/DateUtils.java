package org.utm.lab2.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    private DateUtils() {
    }

    public static String formatTimestamp(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(new Date(timestamp));
    }
}

