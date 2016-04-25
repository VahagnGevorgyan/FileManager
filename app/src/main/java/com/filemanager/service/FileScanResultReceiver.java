package com.filemanager.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.filemanager.application.AppGlobals;
import com.filemanager.utils.Logger;

/**
 * Created by Vahagn Gevorgyan
 * 21 April 2016
 * vahagngevorgyan1989@gmail.com
 * FileManager
 */
public class FileScanResultReceiver extends BroadcastReceiver {

    private static final String TAG = FileScanResultReceiver.class.getSimpleName();

    public FileScanResultReceiver() {
        super();
        if(AppGlobals.DEBUG)Logger.i(TAG, ":: FileScanResultReceiver : Constructor ");
    }

    public interface OnFileScanResultListener {

        void onReceiveFileScanResult(Context context, Intent intent);
    }

    /** Listener of this receiver */
    private OnFileScanResultListener listener = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(AppGlobals.DEBUG) Logger.i(TAG, ":: FileScanResultReceiver.onReceive : intent : " + intent);

        if (null != this.listener) {
            this.listener.onReceiveFileScanResult(context, intent);
        } else {
            if(AppGlobals.DEBUG)Logger.i(TAG, ":: FileScanResultReceiver.onReceiveResult : listener = null");
        }
    }

    /**
     * @param listener
     *            the listener to set
     */
    public void setListener(final   OnFileScanResultListener listener) {
        this.listener = listener;
        if(AppGlobals.DEBUG)Logger.i(TAG, ":: FileScanResultReceiver.setListener : Listener : " + listener.hashCode());
    }

    /**
     * Clear listener
     */
    public void clearListener() {
        if(AppGlobals.DEBUG)Logger.i(TAG, ":: FileScanResultReceiver.clearListener : Clear Listener : " + listener.hashCode());
        this.listener = null;
    }

}
