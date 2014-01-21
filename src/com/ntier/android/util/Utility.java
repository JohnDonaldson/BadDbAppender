/**
 * 
 */
package com.ntier.android.util;

import android.os.Environment;

/**
 * @author JD
 * see http://developer.android.com/training/basics/data-storage/files.html
 */
public class Utility {
private static final Utility singleton = new Utility();
private static final String ExternalStorageState = Environment.getExternalStorageState();
private Utility() {} //prevents instantiation.  We only need/want one of these.
//TODO what about MainActivity used in isAndroidOnline??? vs. singleton??
public static Utility getInstance() { return singleton; }

/* Checks if external storage is available for read and write */
public static boolean isExternalStorageWritable() {
    return Environment.MEDIA_MOUNTED.equals( ExternalStorageState );
	}

/* Checks if external storage is available to at least read */
public static boolean isExternalStorageReadable() {
    return Environment.MEDIA_MOUNTED_READ_ONLY.equals( ExternalStorageState ) ;
    }


	
}


