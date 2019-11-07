package com.example.getproperties;

import android.os.SystemProperties;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "GetProperties";

    private final String BUILD_DATE = "ro.build.date";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getProp(BUILD_DATE);
        getProp2(BUILD_DATE);
    }

    private String getProp(String key) {
        String value = null;

        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class, String.class);
            value = (String) get.invoke(c, key, "unknown");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Get propperty error");
        }

        Log.e(TAG, "Get property: " + key + " = " + value);
        return value;
    }

    private String getProp2(String key) {
        String value = SystemProperties.get(key);
        Log.e(TAG, "marco-----Get Property2: " + key + " = " + value);
        return value;
    }
}
