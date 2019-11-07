package com.example.marcotest;

import android.util.Log;

class HelloWorld {
	private static final String TAG = "HelloWorld";
	private static final String libraryName = "MarcoJniTest";

	static {
		System.loadLibrary(libraryName);
	}

	HelloWorld() {
		Log.e(TAG, "-----------------constructor");
		float ret = process(888);
		Log.e(TAG, "-----------------ret = " + ret);
	}

	public static native float process(int i);
}
