package com.dong.compasslevel;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";

    private final static int MSG_SELECT_ITEM = 1001;

    private final static int ITEM_NUM = 2;
    private final static int ITEM_COMPASS = 0;
    private final static int ITEM_LEVEL = 1;

    private final static String ACTIVITY_COMPASS = ".CompassActivity";
    private final static String ACTIVITY_LEVEL = ".LevelActivity";

    private ListView listView;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SELECT_ITEM:
                    handleItem((Integer) msg.obj);
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buildPrivateData();
        buildView();
    }

    private void buildPrivateData() {

    }

    private void buildView() {
        listView = (ListView) findViewById(R.id.menu_list_view);

        for (int i = 0; i < ITEM_NUM; i++) {

        }
        String arr[] = {"Compass", "Level"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout
                .array_item, arr);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int i, long l) {
                mHandler.obtainMessage(MSG_SELECT_ITEM, i).sendToTarget();
            }
        });
    }

    private void handleItem(int item) {
        switch (item) {
            case ITEM_COMPASS:
                Log.i(TAG, "marco-----jump ITEM_COMPASS");
                Intent intent = new Intent();
                intent.setClassName(getPackageName(), getPackageName
                        () + ACTIVITY_COMPASS);
                startActivity(intent);
                break;

            case ITEM_LEVEL:
                Log.i(TAG, "marco-----jump ITEM_LEVEL");
                Intent intent2 = new Intent();
                intent2.setClassName(getPackageName(), getPackageName
                        () + ACTIVITY_LEVEL);
                startActivity(intent2);
                break;

            default:
                break;
        }
    }
}
