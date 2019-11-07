package com.example.wificollection;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "WifiCollection";

    private final static int PERMISSIONS_REQUEST_CODE = 1001;
    private final static int RATIONALE_PERMISSIONS_REQUEST_CODE = 2;

    private final static String NONE = "<unknown ssid>";
    private final static String BLANK_MAC_ADDRESS = "00:00:00:00:00:00";
    private final static String DEFAULT_MAC_ADDRESS = "02:00:00:00:00:00";
    private final static int DEFAULT_DELAY = 2 * 1000; //2 seconds

    private final static int MIN_DELAY_SECONDS = 2;
    private final static int MAX_DELAY_SECONDS = 30;

    private final static int MSG_START_TEST = 1001;
    private final static int MSG_STOP_TEST = 1002;
    private final static int MSG_GET_DATA = 1003;
    private final static int MSG_NETWORK_CONNECTED = 1004;
    private final static int MSG_NETWORK_DISCONNECTED = 1005;

    private final static int INIT_STATUS = 0;
    private final static int DISCONNECTED_STATUS = 1;
    private final static int CONNECTED_STATUS = 2;

    /* TODO
        Try to use string.xml replace below strings.
     */
    private final static String START_STATUS = "Start collecting......";
    private final static String STOP_STATUS = "Stop collect";
    private final static String SSID = "SSID: ";
    private final static String BSSID = "BSSID: ";
    private final static String FREQ = "FREQ: ";
    private final static String SPEED = "SPEED: ";
    private final static String RSSI = "RSSI: ";
    private final static String ROAMING = "ROAMING: ";
    private final static String BLANK = "";
    private final static String NO_CONNECTED_AP = "No connected AP!!\n";
    private final static String CONNECTED = "CONNECTED\n";
    private final static String DISCONNECTED = "DISCONNECTED\n";
    private final static String RECONNECTION = "Reconnection: ";

    private Button startButton;
    private Button stopButton;
    private TextView statusView;
    private TextView contentView;
    private TextView reconnectView;
    private Spinner spinner;

    private ConnectivityManager connectivityManager;

    private int delay = DEFAULT_DELAY;
    private int speed;
    private String ssid;
    private String bssid = DEFAULT_MAC_ADDRESS;
    private String lastBssid = DEFAULT_MAC_ADDRESS;
    private int freq;
    private int rssi;

    private int roamingTimes;
    private int reconnection;
    private int connectionStatus = INIT_STATUS;

    private PlayTimer playTimer;
    private RecordFile recordFile;
    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_START_TEST:
                    startTest();
                    break;

                case MSG_STOP_TEST:
                    stopTest();
                    break;

                case MSG_GET_DATA:
                    handleGetData();
                    break;

                case MSG_NETWORK_CONNECTED:
                    showConnectionStatus(true);
                    break;

                case MSG_NETWORK_DISCONNECTED:
                    showConnectionStatus(false);
                    break;

                default:
                    break;
            }
        }
    };
    private ConnectivityManager.NetworkCallback networkCallback = new
            ConnectivityManager.NetworkCallback() {
                @Override
                public void onAvailable(Network network) {
                    super.onAvailable(network);

                    if (connectionStatus == DISCONNECTED_STATUS) {
                        connectionStatus = CONNECTED_STATUS;
                        reconnection++;
                    }
                    mHandler.obtainMessage(MSG_NETWORK_CONNECTED)
                            .sendToTarget();
                }

                @Override
                public void onLost(Network network) {
                    super.onLost(network);

                    mHandler.obtainMessage(MSG_NETWORK_DISCONNECTED)
                            .sendToTarget();
                    connectionStatus = DISCONNECTED_STATUS;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buildPrivateData();
        buildViews();

        checkPermissions();
//        testing();
    }

    private void checkPermissions() {
        for (int i = 0; i < RequestRuntimePermissions.permissions.length; i++) {
            checkPermission(i);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission(int permissionIndex) {
        String permission = RequestRuntimePermissions.permissions[permissionIndex];
        int checkSelfPermission = checkSelfPermission(permission);

        if (checkSelfPermission != PERMISSION_GRANTED) {
            Log.i(TAG, "Permission was not granted, index=" + permissionIndex);
            requestPermissions(RequestRuntimePermissions.permissions, PERMISSIONS_REQUEST_CODE);

/*            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                Log.i(TAG, "requestPermission shouldShowRequestPermissionRationale");
                shouldShowRationale(this, RATIONALE_PERMISSIONS_REQUEST_CODE, permission);

            } else {
                Log.d(TAG, "requestPermission else");
                requestPermissions(RequestRuntimePermissions.permissions, PERMISSIONS_REQUEST_CODE);
            }*/
        }
    }

    private static void shouldShowRationale(final Activity activity, final int requestCode, final String requestPermission) {
        //TODO
        String permissionsHint = "No this permission, please grant it.\n\n" + requestPermission;
        showMessageOKCancel(activity, "Rationale: " + permissionsHint, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.requestPermissions(activity, new String[]{requestPermission},
                        requestCode);
                Log.d(TAG, "showMessageOKCancel requestPermissions:" + requestPermission);
            }
        });
    }

    private static void showMessageOKCancel(final Activity context, String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean isGetAllPermissions() {
        for (String per : RequestRuntimePermissions.permissions) {
            if (checkSelfPermission(per) != PERMISSION_GRANTED)
                return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.i(TAG, "onRequestPermissionsResult, permissions.length=" + permissions.length);
        for (int i = 0; i < permissions.length; i++) {
            Log.i(TAG, "permissions=" + permissions[i]);
        }

        Log.i(TAG, "onRequestPermissionsResult, grantResults.length=" + grantResults.length);
        for (int i = 0; i < grantResults.length; i++) {
            Log.i(TAG, "grantResults=" + grantResults[i]);
            if (grantResults[i] != PERMISSION_GRANTED) {

                if (Build.VERSION.SDK_INT >= 11) {
                    recreate();
                } else {
                    finish();
                    startActivity(getIntent());
                }
            }
        }
    }

    private void testing() {
        //////////////// Testing ////////////////
        boolean isExWritable = RecordFile.isExternalStorageWritable();
        boolean isExReadable = RecordFile.isExternalStorageReadable();
        File exStDir = Environment.getExternalStorageDirectory();
        File exStPuDir = Environment.getExternalStoragePublicDirectory
                (DIRECTORY_DOWNLOADS);
        File exFileDir = getExternalFilesDir(DIRECTORY_DOWNLOADS);
        Log.i(TAG, "isExWritable:" + isExWritable);
        Log.i(TAG, "isExReadable:" + isExReadable);
        Log.i(TAG, "ExternalStorageDirectory:" + exStDir);
        Log.i(TAG, "ExternalStoragePublicDirectory Downloads:" +
                exStPuDir);
        Log.i(TAG, "exFileDir:" + exFileDir);
    }

    private void buildPrivateData() {
    }

    private void buildViews() {
        startButton = (Button) findViewById(R.id.start_button);
        stopButton = (Button) findViewById(R.id.stop_button);
        statusView = (TextView) findViewById(R.id.status);
        contentView = (TextView) findViewById(R.id.content);
        reconnectView = (TextView) findViewById(R.id.reconnect);
        spinner = (Spinner) findViewById(R.id.delay_spinner);

        startButton.setEnabled(true);
        stopButton.setEnabled(false);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Click start button");
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                statusView.setText(START_STATUS);
                mHandler.obtainMessage(MSG_START_TEST).sendToTarget();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Click stop button");
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
                statusView.setText(STOP_STATUS);
                mHandler.obtainMessage(MSG_STOP_TEST).sendToTarget();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android
                .R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        for (int i = MIN_DELAY_SECONDS; i <= MAX_DELAY_SECONDS; i++) {
            adapter.add(String.valueOf(i));
        }
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView
                .OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int
                    position, long id) {
                Log.i(TAG, "id=" + id);
                delay = (int) ((id + MIN_DELAY_SECONDS) * 1000);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getWifiInfo() {
        WifiManager wifiManager = (WifiManager) getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        ssid = wifiInfo.getSSID();
        Log.i(TAG, "ssid=" + ssid);
        if (ssid.equals(NONE)) {
            Log.i(TAG, "NO WIFI connection");
        } else {
            Log.i(TAG, "WIFI: " + ssid);
        }

        bssid = wifiInfo.getBSSID();
        Log.i(TAG, "bssid=" + bssid);
        if (bssid != null) {
            if (!lastBssid.equals(bssid) &&
                    !lastBssid.equals(DEFAULT_MAC_ADDRESS) &&
                    !bssid.equals(BLANK_MAC_ADDRESS) &&
                    !bssid.equals(DEFAULT_MAC_ADDRESS)) {
                roamingTimes++;
            }
            lastBssid = bssid;
        }

        freq = wifiInfo.getFrequency();
        Log.i(TAG, "freq=" + freq);

        speed = wifiInfo.getLinkSpeed();
        Log.i(TAG, "speed=" + speed);

        rssi = wifiInfo.getRssi();
        Log.i(TAG, "rssi=" + rssi);
    }

    private void handleGetData() {
        showWifiInfo();

        try {
            storeWifiInfo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showWifiInfo() {
        statusView.setText(START_STATUS);

        if (ssid.equals(NONE)) {
            contentView.setText(NO_CONNECTED_AP);
        } else {
            contentView.setText(SSID + ssid + "\n");
            contentView.append(BSSID + bssid + "\n");
            contentView.append(FREQ + freq + "MHz\n");
            contentView.append(SPEED + speed + "Mbps\n");
            contentView.append(RSSI + rssi + "\n");
            contentView.append(ROAMING + roamingTimes + "\n");
        }
    }

    private void storeWifiInfo() throws IOException {
        if (recordFile == null)
            return;

        if (ssid.equals(NONE)) {
            recordFile.record(NO_CONNECTED_AP);
        } else {
            recordFile.record(ssid + "," + bssid + "," + freq + "," + speed +
                    "," + rssi + "," + roamingTimes + "\n");
        }
    }

    private void startTest() {
        refreshPrivateData();
        refreshViews();

        getRecordFile();
        startPlayTimer();

        monitorWifiConnection();
    }

    private void stopTest() {
        stopPlayTimer();
        freeRecordFile();
        stopMonitorWifiConnection();
    }

    private void refreshPrivateData() {
        reconnection = 0;
    }

    private void refreshViews() {
        if (isWifiConnected()) {
            mHandler.obtainMessage(MSG_NETWORK_CONNECTED).sendToTarget();
        } else {
            mHandler.obtainMessage(MSG_NETWORK_DISCONNECTED).sendToTarget();
        }
    }

    private boolean isWifiConnected() {
        WifiManager wifiManager = (WifiManager) getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        ssid = wifiInfo.getSSID();
        Log.i(TAG, "isWifiConnected()-ssid=" + ssid);

        if (ssid != null) {
            return true;
        } else {
            return false;
        }
    }

    private void getRecordFile() {
        SimpleDateFormat formatter = new SimpleDateFormat
                ("yyyy-MM-dd-HH_mm_ss");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        recordFile = new RecordFile(MainActivity.this, str);
    }

    private void freeRecordFile() {
        recordFile = null;
    }

    protected synchronized void startPlayTimer() {
        stopPlayTimer();
        if (playTimer == null) {
            playTimer = new PlayTimer();
            Timer timer = new Timer();
            timer.schedule(playTimer, 0, delay);
        }
    }

    /**
     * stop Timer
     */
    protected synchronized void stopPlayTimer() {
        try {
            if (playTimer != null) {
                playTimer.cancel();
                playTimer = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        contentView.setText(BLANK);
    }

    protected void onDestroy() {
        stopPlayTimer();
        super.onDestroy();
    }

    private void monitorWifiConnection() {
        connectivityManager = (ConnectivityManager) getSystemService(Context
                .CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            connectivityManager.requestNetwork(new NetworkRequest.Builder()
                    .build(), networkCallback);
        }
    }

    private void stopMonitorWifiConnection() {
        if (connectivityManager != null) {
            connectivityManager.unregisterNetworkCallback(networkCallback);
        }
    }

    private void showConnectionStatus(boolean isConnected) {
        if (isConnected) {
            reconnectView.setText(CONNECTED);
        } else {
            reconnectView.setText(DISCONNECTED);
        }
        reconnectView.append(RECONNECTION + reconnection);

        try {
            storeReconnectionInfo(isConnected);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void storeReconnectionInfo(boolean isConnected) throws IOException {
        if (recordFile == null)
            return;

        if (isConnected) {
            recordFile.record("Connected-reconnection=" + reconnection + "\n");
        } else {
            recordFile.record("Disconnected-reconnection=" + reconnection +
                    "\n");
        }
    }

    public class PlayTimer extends TimerTask {

        public PlayTimer() {
            roamingTimes = 0;
        }

        public void run() {
            //execute task
            getWifiInfo();
            mHandler.obtainMessage(MSG_GET_DATA).sendToTarget();
        }
    }
}
