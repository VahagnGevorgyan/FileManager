package com.filemanager.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by Vahagn Gevorgyan
 * 21 April 2016
 * vahagngevorgyan1989@gmail.com
 * FileManager
 */
public class FileScanReceiver extends WakefulBroadcastReceiver {
    private static final int PERIOD = 5000; // 10 seconds
    private static final int INITIAL_DELAY = 1500; // 5 seconds

    @Override
    public void onReceive(Context ctxt, Intent i) {
        if (i.getAction() == null) {
            startWakefulService(ctxt, new Intent(ctxt,
                    FileScanService.class));
        } else {
            scheduleAlarms(ctxt);
        }
    }

    public static void scheduleAlarms(Context ctxt) {
        AlarmManager mgr = (AlarmManager) ctxt
                .getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(ctxt, FileScanReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(ctxt, 0, i, 0);

        mgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + INITIAL_DELAY, PERIOD, pi);

    }

    public static void cancelAlarms(Context ctxt) {
        AlarmManager mgr = (AlarmManager) ctxt
                .getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(ctxt, FileScanReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(ctxt, 0, i, 0);

        mgr.cancel(pi);

    }

}

