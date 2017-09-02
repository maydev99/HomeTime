package com.droidloft.hometime;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import java.util.Calendar;

/**
 * Created by michaelmay on 8/31/17.
 */

public class NewAppWidget extends AppWidgetProvider {


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        SharedPreferences hourDiffPrefs = context.getSharedPreferences("hour_diff_key", Context.MODE_PRIVATE);
        int hoursDiff = hourDiffPrefs.getInt("hour_diff_key", 0);

        Calendar cal = Calendar.getInstance();
        int currentHour = cal.get(Calendar.HOUR);
        int currentMinute = cal.get(Calendar.MINUTE);
        String homeMinuteStr;
        if(currentMinute < 10) {
            homeMinuteStr = "0" + Integer.toString(currentMinute);
        } else {
            homeMinuteStr = Integer.toString(currentMinute);
        }
        int currentAmPm = cal.get(Calendar.AM_PM);

        if(currentAmPm == 1) {
            currentHour = currentHour + 12;
        }
        int homeTimeHour = currentHour + hoursDiff;

        String ampmStr;

        if(homeTimeHour > 12) {
            ampmStr = "PM";
            homeTimeHour = homeTimeHour - 12;
        } else {
            ampmStr = "AM";
        }


        String homeTime = homeTimeHour + ":" + homeMinuteStr + " " + ampmStr;

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        views.setTextViewText(R.id.timeTextView, homeTime);
        //views.setTextViewText(R.id.descriptionTextView, description);

        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.widget_container, pendingIntent);



        appWidgetManager.updateAppWidget(appWidgetId, views);



    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        SharedPreferences hourDiffPrefs = context.getSharedPreferences("hour_diff_key", Context.MODE_PRIVATE);
        int hoursDiff = hourDiffPrefs.getInt("hour_diff_key", 0);

        Calendar cal = Calendar.getInstance();
        int currentHour = cal.get(Calendar.HOUR);
        int currentMinute = cal.get(Calendar.MINUTE);
        String homeMinuteStr;
        if(currentMinute < 10) {
            homeMinuteStr = "0" + Integer.toString(currentMinute);
        } else {
            homeMinuteStr = Integer.toString(currentMinute);
        }
        int currentAmPm = cal.get(Calendar.AM_PM);

        if(currentAmPm == 1) {
            currentHour = currentHour + 12;
        }
        int homeTimeHour = currentHour + hoursDiff;

        String ampmStr;

        if(homeTimeHour >= 12) {
            ampmStr = "PM";
            if(homeTimeHour > 12){
                homeTimeHour = homeTimeHour - 12;
            }

        } else {
            ampmStr = "AM";
        }


        String homeTime = homeTimeHour + ":" + homeMinuteStr + " " + ampmStr;

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        views.setTextViewText(R.id.timeTextView, homeTime);
        //views.setTextViewText(R.id.descriptionTextView, description);

        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.widget_container, pendingIntent);

        ComponentName thisWidget = new ComponentName(context,NewAppWidget.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(thisWidget, views);




        appWidgetManager.updateAppWidget(appWidgetIds, views);
    }




}
