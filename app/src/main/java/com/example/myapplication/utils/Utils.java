package com.example.myapplication.utils;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.View;

import static android.text.format.DateUtils.*;

import com.example.myapplication.BuildConfig;

import java.util.Date;

import io.reactivex.rxjava3.disposables.Disposable;

public class Utils {

    public static CharSequence formatDateTime(Context context, Date dateTime) {
        return DateUtils.getRelativeDateTimeString(
                context,
                dateTime.getTime(),
                MINUTE_IN_MILLIS,
                5 * DAY_IN_MILLIS,
                FORMAT_ABBREV_RELATIVE);
    }

    public static void disposeSafe(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed())
            disposable.dispose();
    }

    public static void setViewVisibility(View view, boolean shouldShow) {
        if (view == null) return;

        int visibility = shouldShow ? View.VISIBLE : View.GONE;
        view.setVisibility(visibility);
    }

    public static boolean isDebug() {
        return BuildConfig.DEBUG;
    }
}
