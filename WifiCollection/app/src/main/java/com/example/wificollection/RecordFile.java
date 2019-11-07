package com.example.wificollection;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static android.os.Environment.MEDIA_MOUNTED;
import static android.os.Environment.MEDIA_MOUNTED_READ_ONLY;
import static android.os.Environment.getExternalStoragePublicDirectory;
import static android.os.Environment.getExternalStorageState;

public class RecordFile {
    private final static String TAG = "WifiCollection";

    private final static String DIRECTORY = "WifiCollection";
    private final static String SUFFIX = ".csv";

    private Context mContext;
    private String fileName;
//    private String currentTime;

    public RecordFile(Context context, String name) {
        mContext = context;
        fileName = name + SUFFIX;
    }

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = getExternalStorageState();
        if (MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        String state = getExternalStorageState();
        if (MEDIA_MOUNTED.equals(state) ||
                MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public void record(String str) throws IOException {
        if (!isExternalStorageWritable())
            return;

        File dir = getPrivateAlbumStorageDir(mContext, DIRECTORY);
        File file = new File(dir, fileName);
        Log.i(TAG, "filename=" + fileName);
        FileOutputStream outputStream = new FileOutputStream(file, true);
        OutputStreamWriter osw = new OutputStreamWriter(outputStream, "UTF-8");

        osw.write(str);

        osw.flush();
        outputStream.flush();
        osw.close();
        outputStream.close();
    }

    public File getPublicAlbumStorageDir(String albumName) {
        // Get the directory for the user's public downloads directory.
        File file = new File(getExternalStoragePublicDirectory(
                DIRECTORY_DOWNLOADS), albumName);
        if (!file.mkdirs()) {
            Log.e(TAG, "Public album directory not created");
        }
        return file;
    }

    public File getPrivateAlbumStorageDir(Context context, String albumName) {
        // Get the directory for the app's private downloads directory.
        File file = new File(context.getExternalFilesDir(
                DIRECTORY_DOWNLOADS), albumName);
        if (!file.mkdirs()) {
            Log.e(TAG, "Private album directory not created");
        }
        return file;
    }
}