package com.luteh.kampusonlinenonakademik.common;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import com.luteh.kampusonlinenonakademik.R;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import dmax.dialog.SpotsDialog;
import timber.log.Timber;

/**
 * Created by Luthfan Maftuh on 08/11/2018.
 * Email luthfanmaftuh@gmail.com
 */
public class Common {

    public static String currentUID = null;

    public static List<String> semesterLists = new ArrayList<>();
    public static List<String> semesterListCollectionNames = new ArrayList<>();
    public static List<String> ujianSemesterList = new ArrayList<>();
    public static List<String> ujianSemesterListChildNames = new ArrayList<>();
    public static List<String> susulanSemesterList = new ArrayList<>();
    public static List<String> susulanSemesterListChildNames = new ArrayList<>();

    private static AlertDialog.Builder builder;
    private static AlertDialog dialog;
    private static android.app.AlertDialog waitingDialog;

    public static boolean isFrsDialogShowed = false;

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private static SimpleDateFormat dateAlphanumericFormat = new SimpleDateFormat("dd MMM yyyy");
    private static SimpleDateFormat defaultDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");

    public static void showProgressBar(Context context) {
        builder = new AlertDialog.Builder(context);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog);

        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        if (!dialog.isShowing()) dialog.show();
    }

    public static void dismissProgressBar() {
        if (dialog.isShowing()) dialog.dismiss();
    }

    public static void showSpotsProgress(Context context) {
        showSpotsProgress(context, context.getResources().getString(R.string.title_message_loading));
    }

    public static void showSpotsProgress(Context context, String message) {
        waitingDialog = new SpotsDialog.Builder()
                .setContext(context)
                .setMessage(message)
                .setCancelable(false)
                .setTheme(R.style.CustomSpotsDialog)
                .build();
        if (!waitingDialog.isShowing()) {
            waitingDialog.show();
        }
    }

    public static void dismissSpotsProgress() {
        if (waitingDialog.isShowing())
            waitingDialog.dismiss();
    }

    public static void showSuccessMessage(Context context, String message) {
        FancyToast.makeText(context, message, 0, FancyToast.SUCCESS, false).show();
    }

    public static void showInfoMessage(Context context, String message) {
        FancyToast.makeText(context, message, 1, FancyToast.INFO, false).show();
    }

    public static void showErrorMessage(Context context, String message) {
        FancyToast.makeText(context, message, 1, FancyToast.ERROR, false).show();
    }

    public static boolean isValidEmail(EditText editText) {
        return (!TextUtils.isEmpty(editText.getText()) && Patterns.EMAIL_ADDRESS.matcher(editText.getText()).matches());
    }

    public static boolean isEmpty(CharSequence charSequence) {
        return TextUtils.isEmpty(charSequence);
    }

    public static void showToastMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static String formatChildName(String string) {
        return string.replace(" ", "_")
                .replace("/", "-")
                .toLowerCase();
    }

    public static boolean isPermissionGranted(Context context, String permission) {
        return ActivityCompat.checkSelfPermission(context, permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    public static SpannableString paragraphTextStyle(String text) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new android.text.style.LeadingMarginSpan.Standard(60, 0), 0, 1, 0);
        return spannableString;
    }

    private static Date getStartOfDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private static Date getStartOfHour() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
//        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private static Date getStartOfMinute() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
//        calendar.set(Calendar.HOUR_OF_DAY, 0);
//        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private static Date getStartOfSecond() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
//        calendar.set(Calendar.HOUR_OF_DAY, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static String convertDateToString(Date date) {
        return dateFormat.format(date);
    }

    public static String convertDateToStringAlphanumeric(Date date) {
        return dateAlphanumericFormat.format(date);
    }

    public static Date convertStringToDate(String date) throws ParseException {
//        return dateFormat.parse(date);
        return defaultDateFormat.parse(date);
    }

    public static String convertTimeToString(Date date) {
        return timeFormat.format(date);
    }

    public static Date convertStringToTime(String time) throws ParseException {
        return timeFormat.parse(time);
    }

    public static long getSecondsAgo(Date date) {
        final long diff = getStartOfSecond().getTime() - date.getTime();

        return (diff < 0) ? 0 : TimeUnit.MILLISECONDS.toSeconds(diff);
    }

    public static long getMinutesAgo(Date date) {
        final long diff = getStartOfMinute().getTime() - date.getTime();

        return (diff < 0) ? 0 : TimeUnit.MILLISECONDS.toMinutes(diff);
    }

    public static long getHoursAgo(Date date) {
        final long diff = getStartOfHour().getTime() - date.getTime();

        return (diff < 0) ? 0 : TimeUnit.MILLISECONDS.toHours(diff);
    }

    public static long getDaysAgo(Date date) {
        final long diff = getStartOfDay().getTime() - date.getTime();

        /*if (diff < 0) {
            // if the input date millisecond > today's 12:00am millisecond it is today
            // (this won't work if you input tomorrow)
            return 0;
        } else {
            return TimeUnit.MILLISECONDS.toDays(diff);
        }*/

        return (diff < 0) ? 0 : TimeUnit.MILLISECONDS.toDays(diff);
    }

    public static String getStringDateRange(String date) {

        try {
            Date mDate = Common.convertStringToDate(date);

            Timber.d("Time ago: %s", DateUtils.getRelativeTimeSpanString(mDate.getTime()));

            long secondsAgo = getSecondsAgo(mDate) + 1;
            long minutesAgo = getMinutesAgo(mDate) + 1;
            long hoursAgo = getHoursAgo(mDate) + 1;
            long daysAgo = getDaysAgo(mDate) + 1;

            if (secondsAgo > 60) {
                if (minutesAgo > 60) {
                    if (hoursAgo > 24) {
                        if (daysAgo > 7) {
                            return convertDateToStringAlphanumeric(convertStringToDate(date));
                        }
                        return daysAgo + " days ago";
                    }
                    return hoursAgo + " hours ago";
                }
                return minutesAgo + " minutes ago";
            }
            return secondsAgo + " seconds ago";

            /*return (daysAgo < 8) ?
                    daysAgo + " days ago" :
                    convertDateToStringAlphanumeric(convertStringToDate(date));*/
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean isAdmin() {
        return (AccountHelper.getUser().isHasAccess &&
                AccountHelper.getUser().isMember);
    }
}
