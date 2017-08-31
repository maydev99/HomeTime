package com.droidloft.hometime;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    String version = "0.1", buildDate = "8-31-2017";
    TimePicker timePicker;
    int setHour, minute, hoursDiff, deviceHour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        timePicker = (TimePicker)findViewById(R.id.timePicker);

        getTheTime();


    }

    private void getTheTime() {
        Calendar cal = Calendar.getInstance();
        deviceHour = cal.get(Calendar.HOUR);
        int ampm = cal.get(Calendar.AM_PM);
        if(ampm == 1) {
            deviceHour = deviceHour + 12;
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.set_time) {

            setTheTime();
            //Toast.makeText(MainActivity.this, "Time Set", Toast.LENGTH_SHORT).show();
        }

        if(item.getItemId() == R.id.about){
            AlertDialog.Builder aboutAlert = new AlertDialog.Builder(this);
            aboutAlert.setTitle("HomeTime v" + version);
            aboutAlert.setMessage("Build Date: " + buildDate + "\n" + "by Michael May" + "\n" + "DroidLoft");
            aboutAlert.setIcon(R.mipmap.ic_launcher);
            aboutAlert.setCancelable(true);
            aboutAlert.show();
        }

        return super.onOptionsItemSelected(item);
    }


    private void setTheTime() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setHour = timePicker.getHour();
            //Toast.makeText(MainActivity.this, "Set Hour: " + setHour, Toast.LENGTH_SHORT).show();

        }

        hoursDiff = setHour - deviceHour;
        savePrefs();
        updateAppWidget();
        finish();

        //Toast.makeText(MainActivity.this, "Time Diff: " + hoursDiff, Toast.LENGTH_LONG).show();


    }

    private void savePrefs() {
        SharedPreferences hourDiffPrefs = getSharedPreferences("hour_diff_key", MODE_PRIVATE);
        SharedPreferences.Editor hourDiffEditor = hourDiffPrefs.edit();
        hourDiffEditor.putInt("hour_diff_key", hoursDiff);
        hourDiffEditor.apply();
    }

    private void loadPrefs() {
        SharedPreferences hourDiffPrefs = getSharedPreferences("hour_diff_key", MODE_PRIVATE);
        hoursDiff = hourDiffPrefs.getInt("hour_diff_key", 0);
    }

    private void updateAppWidget() {
        Intent intent = new Intent(this, NewAppWidget.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        int ids[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), NewAppWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        sendBroadcast(intent);

    }
}
