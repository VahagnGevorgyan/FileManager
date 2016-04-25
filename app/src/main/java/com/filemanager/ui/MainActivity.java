package com.filemanager.ui;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.filemanager.FileInfo;
import com.filemanager.R;
import com.filemanager.application.AppGlobals;
import com.filemanager.helper.ServiceHelper;
import com.filemanager.service.FileScanResultReceiver;
import com.filemanager.service.FileScanService;
import com.filemanager.utils.Logger;
import com.filemanager.utils.UpdateUITypes;
import com.filemanager.utils.Utils;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, FileScanResultReceiver.OnFileScanResultListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Button btnStart;
    private Button btnStop;

    private boolean doubleBackToExitPressedOnce = false;
    protected final FileScanResultReceiver fileScanResultReceiver = new FileScanResultReceiver();
    private ProgressBar progressBarScanning;
    private Handler handler = new Handler();
    private int progressStatus = 0;
    private TextView textViewTopFiles;
    private TextView textViewTopFilesTitle;
    private TextView textViewAverageSize;
    private TextView textViewAverageTitle;
    private TextView textViewFrequentFilesTitle;
    private TextView textViewFrequentFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (Button) findViewById(R.id.btnStart);
        btnStop = (Button) findViewById(R.id.btnStop);
        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);

        textViewTopFiles = (TextView) findViewById(R.id.textViewTopFiles);
        textViewTopFilesTitle = (TextView) findViewById(R.id.textViewTopFilesTitle);

        textViewFrequentFiles = (TextView) findViewById(R.id.textViewFrequentFiles);
        textViewFrequentFilesTitle = (TextView) findViewById(R.id.textViewFrequentFilesTitle);

        textViewAverageTitle = (TextView) findViewById(R.id.textViewAverageTitle);
        textViewAverageSize = (TextView) findViewById(R.id.textViewAverageSize);

        progressBarScanning = (ProgressBar) findViewById(R.id.progressBarScanning);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(AppGlobals.DEBUG) Logger.i(TAG, ":: MainActivity.onResume ");

        // set order status receiver
        this.fileScanResultReceiver.setListener(this);
        // filter for BroadcastReceiver
        IntentFilter intFilt = new IntentFilter(Utils.UPDATE_SCAN_RESULT);
        // register BroadcastReceiver
        registerReceiver(this.fileScanResultReceiver, intFilt);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // unregister BroadcastReceiver
        unregisterReceiver(this.fileScanResultReceiver);
    }

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
		Toast.makeText(this, getString(R.string.str_back_again_exit),
                Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;

            }
        }, 2000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStart:
                if(AppGlobals.DEBUG)Logger.i(TAG, ":: MainActivity.onClick : START ");
                // START SERVICE
                startFileScanning();
                // DISABLE START
                btnStart.setEnabled(false);
                // ENABLE STOP
                btnStop.setEnabled(true);
                // CHANGE START/STOP IMAGE
                btnStart.setBackgroundResource(R.drawable.ic_play_dis);
                btnStop.setBackgroundResource(R.drawable.ic_stop);
                break;
            case R.id.btnStop:
                if(AppGlobals.DEBUG)Logger.i(TAG, ":: MainActivity.onClick : STOP ");
                // STOP SERVICE
                stopFileScanning();
                // DISABLE STOP
                btnStop.setEnabled(false);
                // ENABLE START
                btnStart.setEnabled(true);
                // CHANGE START/STOP IMAGE
                btnStart.setBackgroundResource(R.drawable.ic_play);
                btnStop.setBackgroundResource(R.drawable.ic_stop_dis);
                break;
        }
    }


    /**
     * Method for starting file scanning service
     */
    public void startFileScanning() {
        if(AppGlobals.DEBUG)Logger.i(TAG, ":: MainActivity.startFileScanning ");

        if(!ServiceHelper.isScanServiceRunning(this, FileScanService.class)) {
            if(AppGlobals.DEBUG)Logger.i(TAG, ":: MainActivity.startFileScanning : START ");
            progressBarScanning.setVisibility(View.VISIBLE);
            startService(new Intent(this, FileScanService.class));
        }

    } // end method startFileScanning


    /**
     * Method for stopping file scanning service
     */
    public void stopFileScanning() {
        if(AppGlobals.DEBUG)Logger.i(TAG, ":: MainActivity.stopFileScanning ");

        if(ServiceHelper.isScanServiceRunning(this, FileScanService.class)) {
            if(AppGlobals.DEBUG)Logger.i(TAG, ":: MainActivity.stopFileScanning : STOP ");
            progressBarScanning.setVisibility(View.GONE);
            stopService(new Intent(this, FileScanService.class));
        }

    } // end method stopFileScanning

    @Override
    public void onReceiveFileScanResult(Context context, Intent intent) {
        if(AppGlobals.DEBUG)Logger.i(TAG, ":: MainActivity.onReceiveFileScanResult : intent : " + intent);

        int ui_update_type = (int) intent.getExtras().get(AppGlobals.EXTRA_UPDATE_UI_TYPE);
        if(AppGlobals.DEBUG)Logger.i(TAG, ":: MainActivity.onReceiveFileScanResult : ui_update_type : " + ui_update_type);
        switch (ui_update_type) {
            case UpdateUITypes.SET_PROGRESS_MAX:
                int max_count = (int) intent.getExtras().get(AppGlobals.EXTRA_PROGRESS_MAX);
                if(AppGlobals.DEBUG)Logger.i(TAG, ":: MainActivity.onReceiveFileScanResult : EXTRA_PROGRESS_MAX : max_count : " + max_count);
                break;
            case UpdateUITypes.SET_PROGRESS_VALUE:
                final int value = (int) intent.getExtras().get(AppGlobals.EXTRA_PROGRESS_VALUE);
                if(AppGlobals.DEBUG)Logger.i(TAG, ":: MainActivity.onReceiveFileScanResult : SET_PROGRESS_VALUE : value : " + value);
                new Thread(new Runnable() {
                    public void run() {
                        // Update the progress bar and display the
                        //current value in the text view
                        handler.post(new Runnable() {
                            public void run() {
                                progressBarScanning.setProgress(value);
                            }
                        });
                        try {
                            // Sleep for 200 milliseconds.
                            //Just to display the progress slowly
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case UpdateUITypes.UPDATE_FILE_RESULT:
                // DISABLE STOP
                btnStop.setEnabled(false);
                // ENABLE START
                btnStart.setEnabled(true);
                // CHANGE START/STOP IMAGE
                btnStart.setBackgroundResource(R.drawable.ic_play);
                btnStop.setBackgroundResource(R.drawable.ic_stop_dis);
                // HIDE PROGRESS BAR
                progressBarScanning.setVisibility(View.GONE);
                // SHOW TITLES
                textViewTopFilesTitle.setVisibility(View.VISIBLE);
                textViewFrequentFilesTitle.setVisibility(View.VISIBLE);
                textViewAverageTitle.setVisibility(View.VISIBLE);
                ArrayList<FileInfo> scanned_files = (ArrayList<FileInfo>) intent.getExtras().get(AppGlobals.EXTRA_SCANNED_FILES);
                ArrayList<FileInfo> scanned_ext = (ArrayList<FileInfo>) intent.getExtras().get(AppGlobals.EXTRA_SCANNED_EXTENSIONS);
                double averageSize = (double) intent.getExtras().get(AppGlobals.EXTRA_AVERAGE_SIZE);
                textViewAverageSize.setText(averageSize + " kb");

                if(AppGlobals.DEBUG)Logger.i(TAG, ":: MainActivity.onReceiveFileScanResult : UPDATE_FILE_RESULT : scanned_files : " + scanned_files);
                if(AppGlobals.DEBUG)Logger.i(TAG, ":: MainActivity.onReceiveFileScanResult : UPDATE_FILE_RESULT : scanned_ext : " + scanned_ext);
                if(AppGlobals.DEBUG)Logger.i(TAG, ":: MainActivity.onReceiveFileScanResult : UPDATE_FILE_RESULT : averageSize : " + averageSize);

                if(scanned_files != null && scanned_files.size() > 0) {
                    String files = "";
                    for (FileInfo item :
                            scanned_files) {
                        String file_size = "0";
                        if(item.getSize() != null)
                            file_size = String.valueOf(item.getSize()/1024);
                        files += "Name : " + item.getName() + " : Size : " + file_size + " kb\n";
                    }
                    if(AppGlobals.DEBUG)Logger.i(TAG, ":: MainActivity.onReceiveFileScanResult : UPDATE_FILE_RESULT : files : " + files);
                    textViewTopFiles.setText(files);
                }
                if(scanned_ext != null && scanned_ext.size() > 0) {
                    String files_ext = "";
                    for (FileInfo item :
                            scanned_ext) {
                        files_ext += "Extension : " + item.getExt() + " : Count : " + item.getCount() + " \n";
                    }
                    if(AppGlobals.DEBUG)Logger.i(TAG, ":: MainActivity.onReceiveFileScanResult : UPDATE_FILE_RESULT : files_ext : " + files_ext);
                    textViewFrequentFiles.setText(files_ext);
                }
                break;
        }

    } // end method onReceiveFileScanResult

    @Override
    protected void onPause() {
        if(AppGlobals.DEBUG)Logger.i(TAG, ":: MainActivity.onPause ");
        // clear receiver
        this.fileScanResultReceiver.clearListener();
        super.onPause();

    }


}
