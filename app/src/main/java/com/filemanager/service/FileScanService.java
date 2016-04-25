package com.filemanager.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.filemanager.application.AppGlobals;
import com.filemanager.helper.FileManager;
import com.filemanager.helper.ServiceHelper;
import com.filemanager.utils.Logger;
import com.filemanager.utils.Utils;

import java.io.File;
import java.util.Arrays;


/**
 * Created by Vahagn Gevorgyan
 * 21 April 2016
 * vahagngevorgyan1989@gmail.com
 * FileManager
 */
public class FileScanService extends Service {

    private static final String TAG = FileScanService.class .getSimpleName();

    public FileScanService() {
        super();
    }

    public void onCreate() {
        super.onCreate();
        if(AppGlobals.DEBUG)Logger.i(TAG, ":: FileScanService.onCreate ");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        if(AppGlobals.DEBUG)Logger.i(TAG, ":: FileScanService.onStartCommand ");
        FileManager.getInstance().startScanningFiles(this);
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
        if(AppGlobals.DEBUG)Logger.i(TAG, ":: FileScanService.onDestroy ");
        FileManager.getInstance().stopScanningFiles();
    }

    public IBinder onBind(Intent intent) {
        if(AppGlobals.DEBUG)Logger.i(TAG, ":: FileScanService.onBind ");
        return null;
    }

}