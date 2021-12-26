package com.example.myapplication;

import android.content.Context;
import android.text.format.DateUtils;

import static android.text.format.DateUtils.*;

import java.util.Date;

public class Utils {

    public static CharSequence formatDateTime(Context context, Date dateTime) {
        return DateUtils.getRelativeDateTimeString(
                context,
                dateTime.getTime(),
                MINUTE_IN_MILLIS,
                5 * DAY_IN_MILLIS,
                FORMAT_ABBREV_RELATIVE);
    }
}
