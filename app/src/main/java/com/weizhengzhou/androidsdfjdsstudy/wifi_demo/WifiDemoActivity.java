package com.weizhengzhou.androidsdfjdsstudy.wifi_demo;

import android.app.ProgressDialog;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.weizhengzhou.androidsdfjdsstudy.R;
import com.weizhengzhou.androidsdfjdsstudy.base.IRecyclerListener;
import com.weizhengzhou.androidsdfjdsstudy.wifi_demo.adpter.WifiAdapter;
import com.weizhengzhou.androidsdfjdsstudy.wifi_demo.ui.WifiDialog;

import java.util.List;

public class WifiDemoActivity extends AppCompatActivity implements IRecyclerListener{
    private static final String TAG = WifiDemoActivity.class.getSimpleName();
    private LinkWifi mLinkWifi; //Wifi连接类
    private List<ScanResult> mScanResult = null;
    private WifiAdapter mAdpter = null;
    private RecyclerView mView;
    private ProgressDialog mProgressDialog;
    private WifiDialog mWifiDialog;
    private boolean wifiState = false;
    private wifiOpen mOpen;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    mLinkWifi.startScan();
                    mScanResult = mLinkWifi.getWifiList();
                    mAdpter.setList(mScanResult);
                    mProgressDialog.cancel();
                    mProgressDialog.dismiss();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_demo);
        mView = (RecyclerView) findViewById(R.id.wifi_rv);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("正在开启wifi");
        mOpen = new wifiOpen();
        mLinkWifi = LinkWifi.getInstance((WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE));
        Log.e(TAG , "wifi状态：" + wifiState);
        wifiState = mLinkWifi.checkWifiState();
        if (!wifiState){
            mLinkWifi.openWifi();
            mProgressDialog.show();
            mOpen.start();
        }
        mLinkWifi.startScan();
        mScanResult = mLinkWifi.getWifiList();
        mAdpter = new WifiAdapter(this, mScanResult , this);
        mView.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false));
        mView.setAdapter(mAdpter);

    }

    @Override
    public void onItemClick(View view, int pos) {
        ScanResult result = mScanResult.get(pos);
        Toast.makeText(WifiDemoActivity.this , "wifi名称:" + result.SSID , Toast.LENGTH_SHORT).show();
        mWifiDialog = new WifiDialog(this);
        mWifiDialog.show();
    }

    @Override
    public void onItemLongClick(View view, int pos) {

    }

    /**
     * 延迟开启
     */
    private class wifiOpen extends Thread{
        @Override
        public void run() {
            try {
                while (!wifiState){
                        Thread.sleep(4000);
                    wifiState = mLinkWifi.checkWifiState();
                }
                mHandler.sendEmptyMessage(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
