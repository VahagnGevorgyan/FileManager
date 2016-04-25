package com.filemanager.service;

import android.app.IntentService;
import android.content.Intent;

import com.filemanager.application.AppGlobals;
import com.filemanager.utils.Logger;

/**
 * Created by Vahagn Gevorgyan
 * 21 April 2016
 * vahagngevorgyan1989@gmail.com
 * FileManager
 */
public abstract class CronJobsService extends IntentService {

    private static final String TAG = CronJobsService.class.getSimpleName();

    public CronJobsService(String name) {
        super(name);
    }

    protected abstract boolean doCronWork(Intent intent);

    @Override
    protected final void onHandleIntent(Intent intent) {
        if(AppGlobals.DEBUG) Logger.i(TAG, ":: CronJobsService.onHandleIntent : intent : " + intent);

//        if (doCronWork(intent))
//            OrderStatusReceiver.completeWakefulIntent(intent);
//        else {
//            OrderStatusReceiver.cancelAlarms(getApplicationContext());
//        }
    }
}