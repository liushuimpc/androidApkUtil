package com.example.appinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listview = null;
    private List<AppInformation> mlistAppInfo = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_app_list);

        listview = (ListView) findViewById(R.id.listviewApp);
        mlistAppInfo = new ArrayList<AppInformation>();
        queryAppInfo(); // 查询所有应用程序信息
        BrowseAppInfoAdapter browseAppAdapter = new BrowseAppInfoAdapter(
                this, mlistAppInfo);
        listview.setAdapter(browseAppAdapter);
        listview.setOnItemClickListener(this);
    }

    // 点击跳转至该应用程序
    public void onItemClick(AdapterView<?> arg0, View view, int position,
                            long arg3) {
        // TODO Auto-generated method stub
        Intent intent = mlistAppInfo.get(position).getIntent();
        startActivity(intent);
    }

    // 获得所有启动Activity的信息，类似于Launch界面
    public void queryAppInfo() {
        PackageManager pm = this.getPackageManager(); //获得PackageManager对象

        List<PackageInfo> packageInfos = pm.getInstalledPackages(PackageManager.GET_ACTIVITIES);
        for (PackageInfo i : packageInfos) {
            Log.i("marcooo", "marcoooo--info: " + i.packageName);
            String packageName = i.packageName;
            Intent intent = pm.getLaunchIntentForPackage(packageName);
            Drawable icon = i.applicationInfo.loadIcon(pm);
            String label = (String) pm.getApplicationLabel(i.applicationInfo);

            AppInformation appInfo = new AppInformation();
            appInfo.setAppLabel(label);
            appInfo.setPkgName(packageName);
            appInfo.setAppIcon(icon);
            appInfo.setIntent(intent);
            mlistAppInfo.add(appInfo);
        }
    }
}
