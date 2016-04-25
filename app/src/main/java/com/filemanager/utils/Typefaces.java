package com.filemanager.utils;

import java.util.Hashtable;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

/**
 * Created by Vahagn Gevorgyan
 * 21 April 2016
 * vahagngevorgyan1989@gmail.com
 * FileManager
 */
public class Typefaces {
	
	private static final String TAG = Typefaces.class.getSimpleName();
	
	private static final Hashtable<String, Typeface> cache = new Hashtable<String, Typeface>();
	
	public static Typeface get(Context c, String assetPath) {
        synchronized (cache) {
            if (!cache.containsKey(assetPath)) {
                try {
                    Typeface t = Typeface.createFromAsset(c.getAssets(),
                            assetPath);
                    cache.put(assetPath, t);
                } catch (Exception e) {
                    Log.e(TAG, "Could not get typeface '" + assetPath
                            + "' because " + e.getMessage());
                    return null;
                }
            }
            return cache.get(assetPath);
        }
    }

}
