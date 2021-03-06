package com.filemanager.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import com.filemanager.FileInfo;
import com.filemanager.application.AppGlobals;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Vahagn Gevorgyan
 * 21 April 2016
 * vahagngevorgyan1989@gmail.com
 * FileManager
 */
public class Utils {

    private static final String TAG = Utils.class.getSimpleName();

    public static final String UPDATE_SCAN_RESULT = "com.citymobil.UPDATE_SCAN_RESULT";


    /**
     * Method for updating UI files results
     * @param context - context of app
     * @param scanned_files - list of scanned files
     * @param averageSize
     * @param filesExt
     */
    public static void updateResultUI(final Context context,
                                      ArrayList<FileInfo> scanned_files, double averageSize, ArrayList<FileInfo> filesExt) {
        if(AppGlobals.DEBUG)Logger.i(TAG, ":: Utils.updateResultUI : result : " + scanned_files);

        Intent intent = new Intent(Utils.UPDATE_SCAN_RESULT);
        intent.putExtra(AppGlobals.EXTRA_UPDATE_UI_TYPE, UpdateUITypes.UPDATE_FILE_RESULT);
        intent.putExtra(AppGlobals.EXTRA_SCANNED_FILES, scanned_files);
        intent.putExtra(AppGlobals.EXTRA_SCANNED_EXTENSIONS, filesExt);
        intent.putExtra(AppGlobals.EXTRA_AVERAGE_SIZE, averageSize);
        context.sendBroadcast(intent);

    } // end method updateResultUI


}
