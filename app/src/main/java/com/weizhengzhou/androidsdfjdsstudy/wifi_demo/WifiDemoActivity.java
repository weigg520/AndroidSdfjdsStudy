package com.weizhengzhou.androidsdfjdsstudy.wifi_demo;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.weizhengzhou.androidsdfjdsstudy.R;
import com.weizhengzhou.androidsdfjdsstudy.wifi_demo.adpter.WifiAdapter;

import java.util.List;

public class WifiDemoActivity extends AppCompatActivity {
    private static final String TAG = WifiDemoActivity.class.getSimpleName();
    private LinkWifi mLinkWifi; //Wifi连接类
    private List<ScanResult> mScanResult = null;
    private WifiAdapter mAdpter = null;
    private RecyclerView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_demo);
        mView = (RecyclerView) findViewById(R.id.wifi_rv);
        //初始化
        init();

        //测试
        testLog();
    }

    private void testLog() {
        mScanResult = mLinkWifi.getWifiList();
        if (mLinkWifi != null) {
            mAdpter = new WifiAdapter(this, mScanResult);
            Log.e(TAG , "进来了");
            mView.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false));
            mView.setAdapter(mAdpter);
        }
    }

    private void init() {
        //注册监听广播
        //获取工具栏实例
        mLinkWifi = LinkWifi.getInstance((WifiManager) getSystemService(WIFI_SERVICE));
        //扫描wifi
        mLinkWifi.startScan();
    }
}
