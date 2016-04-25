package com.filemanager.helper;

import android.content.Context;
import android.os.Environment;

import com.filemanager.FileInfo;
import com.filemanager.application.AppGlobals;
import com.filemanager.utils.Logger;
import com.filemanager.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Vahagn Gevorgyan
 * 21 April 2016
 * vahagngevorgyan1989@gmail.com
 * FileManager
 */
public class FileManager {

    private static final String TAG = FileManager.class.getSimpleName();

    private static FileManager mInstance = null;

    private final String SD_CARD_PATH = Environment.getExternalStorageDirectory().getPath() + "/";

    private List<FileInfo> scanned_list = new ArrayList<FileInfo>();
    private HashMap<String, Integer> scanned_extensions = new HashMap<>();

    private boolean stopped = true;

    private long filesSize = 0;
    private long filesCount = 0;

    public static FileManager getInstance() {
        if(mInstance == null)
            mInstance = new FileManager();
        return mInstance;
    }

    /**
     * Method for getting all files from path
     * @return - list of files
     */
    public File[] getFileList() {
        File[] listFiles = new File[0];
        if(SD_CARD_PATH != null) {
            File sd_card_dir = new File(SD_CARD_PATH);
            listFiles = sd_card_dir.listFiles();
        }
        if(AppGlobals.DEBUG) Logger.i(TAG, ":: FileManager.getFileList : listFiles : " + Arrays.toString(listFiles));
        return listFiles;

    } // end method getFileList


    /**
     * Method for start scanning files
     * @param context - app context
     */
    public void startScanningFiles(Context context) {
        if(AppGlobals.DEBUG)Logger.i(TAG, ":: FileManager.scanFiles : scanned_list : " + scanned_list);
        stopped = false;
        File[] fileList = getFileList();
        // scan files
        if(scanned_list.size() < fileList.length) {
            for (int i=0; i<fileList.length; i++) {
                if(!stopped) {
                    File itemFile = fileList[i];
                    if (itemFile.isDirectory()) {
                        scanDirectory(context, itemFile);
                    } else {
                        addToScanFiles(context, itemFile);
                    }
                } else {
                    break;
                }
            }
            double averageSize = 0;
            if(filesCount > 0)
                averageSize = filesSize/filesCount;
            // sort ext hash map
            scanned_extensions = (HashMap<String, Integer>) sortByComparator(scanned_extensions, false);
            Utils.updateResultUI(context, getFileTopList(9), averageSize, getFirstItems(4));
        } else {
            scanned_list.clear();
        }

    } // end method startScanningFiles

    /**
     * Method for stopping scanning process
     */
    public void stopScanningFiles() {
        if(AppGlobals.DEBUG)Logger.i(TAG, ":: FileManager.stopScanningFiles ");
        stopped = true;

    } // end method stopScanningFiles

    /**
     * Method for getting top count elements
     * @param count
     * @return
     */
    public ArrayList<FileInfo> getFileTopList(int count) {
        if(AppGlobals.DEBUG)Logger.i(TAG, ":: FileManager.getFileTopList : count : " + count);

        ArrayList<FileInfo> topList = new ArrayList<>();
        if(scanned_list != null && scanned_list.size() > 0) {
            Collections.sort(scanned_list, new CustomComparator());
            for(int i=0; i<scanned_list.size(); i++) {
                if(i > count) break;
                topList.add(scanned_list.get(i));
            }
        }
        if(AppGlobals.DEBUG)Logger.i(TAG, ":: FileManager.getFileTopList : topList : " + topList);
        return topList;

    } // end method getFileTopList

    public class CustomComparator implements Comparator<FileInfo> {
        @Override
        public int compare(FileInfo o1, FileInfo o2) {
            return o2.getSize().compareTo(o1.getSize());
        }
    }

    /**
     * Method for adding file to scanned files
     * @param context - app context
     * @param file - file item
     */
    private void addToScanFiles(Context context, File file) {
        if(AppGlobals.DEBUG)Logger.i(TAG, ":: FileManager.addToScanFiles : file : " + file);

        FileInfo fileInfo = new FileInfo();
        fileInfo.setExt(getFileExt(file.getName()));
        fileInfo.setName(file.getName());
        fileInfo.setSize(file.length());
        fileInfo.setCount(0);

        filesCount++;
        filesSize += file.length();

        scanned_list.add(fileInfo);

        String fileExt = getFileExt(file.getName());
        if(AppGlobals.DEBUG)Logger.i(TAG, ":: FileManager.addToScanFiles : fileExt : " + fileExt);
        if(scanned_extensions.containsKey(fileExt)) {
            Integer fileValue = scanned_extensions.get(fileExt);
            fileValue++;
            if(AppGlobals.DEBUG)Logger.i(TAG, ":: FileManager.addToScanFiles : fileExt : " + fileExt + " : fileValue : " + fileValue);
            scanned_extensions.put(fileExt, fileValue);
        } else {
            scanned_extensions.put(fileExt, 1);
        }

    } // end method addToScanFiles

    /**
     *
     * @param context
     * @param directory
     */
    private void scanDirectory(Context context, File directory) {
        if (directory != null) {
            File[] listFiles = directory.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file : listFiles) {
                    if(!stopped) {
                        if (file.isDirectory()) {
                            scanDirectory(context, file);
                        } else {
                            addToScanFiles(context, file);
                        }
                    } else {
                        break;
                    }
                }
            }
        }
    } // end method scanDirectory

    /**
     * Method for getting file extension
     * @param fileName
     * @return
     */
    private String getFileExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
    }

    private ArrayList<FileInfo> getFirstItems(int count) {
        if(AppGlobals.DEBUG)Logger.i(TAG, ":: FileManager.getFirstItems : count : " + count);
        ArrayList<FileInfo> fileInfos = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : scanned_extensions.entrySet()) {
            if (fileInfos.size() > count) break;
            FileInfo fileInfo = new FileInfo();
            fileInfo.setCount(entry.getValue());
            fileInfo.setExt(entry.getKey());
            fileInfo.setName("");
            fileInfo.setSize(0L);
            fileInfos.add(fileInfo);
        }

        return fileInfos;

    } // end method getFirstItems

    /**
     * Method for sorting map by value
     * @param unSortMap
     * @param order
     * @return
     */
    private static Map<String, Integer> sortByComparator(Map<String, Integer> unSortMap, final boolean order) {

        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(unSortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                if (order) {
                    return o1.getValue().compareTo(o2.getValue());
                } else {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;

    } // end method sortByComparator

}
