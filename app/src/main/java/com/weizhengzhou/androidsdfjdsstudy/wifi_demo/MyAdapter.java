package com.weizhengzhou.androidsdfjdsstudy.wifi_demo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.weizhengzhou.androidsdfjdsstudy.R;

import java.util.List;
import static com.weizhengzhou.androidsdfjdsstudy.wifi_demo.WifiConnectActivity.TAG;

/**
 * Created by 75213 on 2018/1/23.
 */

public class MyAdapter extends BaseAdapter {
    public int level;

    LayoutInflater inflater;
    List<ScanResult> list;
    public MyAdapter(Context context, List<ScanResult> list){
        this.inflater=LayoutInflater.from(context);
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @SuppressLint({ "ViewHolder", "InflateParams" })
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        view=inflater.inflate(R.layout.wifi_listitem, null);
        ScanResult scanResult = list.get(position);
        TextView wifi_ssid=(TextView) view.findViewById(R.id.ssid);
        ImageView wifi_level=(ImageView) view.findViewById(R.id.wifi_level);
        wifi_ssid.setText(scanResult.SSID);
        Log.i(TAG, "scanResult.SSID="+scanResult);
        level= WifiManager.calculateSignalLevel(scanResult.level,5);
        if(scanResult.capabilities.contains("WEP")||scanResult.capabilities.contains("PSK")||
                scanResult.capabilities.contains("EAP")){
            wifi_level.setImageResource(R.drawable.wifi_signal_lock);
        }else{
            wifi_level.setImageResource(R.drawable.wifi_signal_open);
        }
        wifi_level.setImageLevel(level);
        //判断信号强度，显示对应的指示图标
        return view;
    }
}
