package com.example.sensordata.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import android.content.Context;
import android.util.Log;

public class RecordFile {
	private final static String TAG = "RecordFile";

	private final static String SDCARD_PREFIX = "/sdcard/";

	private Context mContext;
	private FileOutputStream logStream;
	private String fileName;
	private String currentTime;

	public RecordFile(Context context, String name) {
		mContext = context;
		fileName = name;
	}

	public void record(String str) throws IOException {
		logStream = mContext.getApplicationContext().openFileOutput(fileName,
				Context.MODE_APPEND);

		if (logStream == null) {
			Log.e(TAG, "logStream == null");
			return;
		}

		OutputStreamWriter osw = new OutputStreamWriter(logStream, "UTF-8");

		osw.write(str);

		osw.flush();
		logStream.flush();
		osw.close();
		osw = null;
		logStream.close();
		logStream = null;

		// Other method, store in directory /sdcard/
		FileWriter writer = new FileWriter(SDCARD_PREFIX + fileName, true);
		writer.write(str);
		writer.close();
	}
}
