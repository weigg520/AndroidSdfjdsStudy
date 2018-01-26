package com.weizhengzhou.androidsdfjdsstudy.wifi_demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.weizhengzhou.androidsdfjdsstudy.R;

import java.util.List;

public class WifiConnectActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "WifiConnectActivity";
    private Button check_wifi,open_wifi,close_wifi,scan_wifi;
    private ListView mlistView;
    private List<ScanResult> mWifiList;
    protected String ssid;
    private LinkWifi mLinkWifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_connect);
        initViews();
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mwifiBroadcastReceiver,myIntentFilter);

        mLinkWifi =  LinkWifi.getInstance((WifiManager)getSystemService(Context.WIFI_SERVICE));

        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ssid=mWifiList.get(position).SSID;
                boolean tag =mLinkWifi.isConnected(mWifiList.get(position));
                Log.e(TAG , "判断WiFi是否连接" + tag);
                if (!tag){
                    WifiConfiguration wifiConfiguration = mLinkWifi.IsExsits(ssid);
                    if (wifiConfiguration == null){
                        AlertDialog.Builder alert=new AlertDialog.Builder(WifiConnectActivity.this);
                        alert.setTitle(ssid);
                        alert.setMessage("输入密码");
                        final EditText et_password=new EditText(WifiConnectActivity.this);
                        final SharedPreferences preferences=getSharedPreferences("wifi_password",Context.MODE_PRIVATE);
                        et_password.setText(preferences.getString(ssid, ""));
                        alert.setView(et_password);
                        //alert.setView(view1);
                        alert.setPositiveButton("连接", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String pw = et_password.getText().toString();
                                if(null == pw  || pw.length() < 8){
                                    Toast.makeText(WifiConnectActivity.this, "密码至少8位", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                SharedPreferences.Editor editor=preferences.edit();
                                editor.putString(ssid, pw);   //保存密码
                                editor.commit();
                                WifiConfiguration configuration = mLinkWifi.CreateWifiInfo(ssid , pw , 3);
                                mLinkWifi.setMaxPriority(configuration);
                                mLinkWifi.ConnectWifi(configuration.networkId);
                            }
                        });
                        alert.setNegativeButton("取消", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //
                                //mWifiAdmin.removeWifi(mWifiAdmin.getNetworkId());
                            }
                        });
                        alert.create();
                        alert.show();
                    }else {
                        mLinkWifi.setMaxPriority(wifiConfiguration);
                        mLinkWifi.ConnectToNetID(wifiConfiguration.networkId );
                    }
                }else {
                    Toast.makeText(WifiConnectActivity.this ,"已经连接11111" ,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /*
    * 控件初始化
    * */
    private void initViews() {
        check_wifi=(Button) findViewById(R.id.check_wifi);
        open_wifi=(Button) findViewById(R.id.open_wifi);
        close_wifi=(Button) findViewById(R.id.close_wifi);
        scan_wifi=(Button) findViewById(R.id.scan_wifi);
        mlistView=(ListView) findViewById(R.id.wifi_list);
        check_wifi.setOnClickListener(this);
        open_wifi.setOnClickListener(this);
        close_wifi.setOnClickListener(this);
        scan_wifi.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.check_wifi:
                //mWifiAdmin.checkState();
                break;
            case R.id.open_wifi:
                //mWifiAdmin.openWifi();
                break;
            case R.id.close_wifi:
                //mWifiAdmin.colseWifi();
                break;
            case R.id.scan_wifi:
                mLinkWifi.startScan();
                mWifiList = mLinkWifi.getWifiList();
                if(mWifiList!=null){
                    mlistView.setAdapter(new MyAdapter(this,mWifiList));
                    new Utility().setListViewHeightBasedOnChildren(mlistView);
                }
                break;
            default:
                break;
        }
    }

    // 监听wifi状态广播
    private BroadcastReceiver mwifiBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                ConnectivityManager localConnectivityManager = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo localNetworkInfo = (localConnectivityManager == null ? null
                        : localConnectivityManager.getActiveNetworkInfo());
                if (localNetworkInfo != null) {
                    if (localNetworkInfo.isConnected()) {
                        //已连接
                        Toast.makeText(WifiConnectActivity.this ,"已经连接" ,Toast.LENGTH_SHORT).show();
                    } else {
                        //断开连接
                    }
                } else {
                    //localNetworkInfo is null, NETWORK................DISCONNECT......
                }
            }
        }
    };


    /*设置listview的高度*/
    public class Utility {
        public void setListViewHeightBasedOnChildren(ListView listView) {
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null) {
                return;
            }
            int totalHeight = 0;
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
        }
    }


}
