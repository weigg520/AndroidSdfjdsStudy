package com.weizhengzhou.androidsdfjdsstudy.wifi_demo;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 75213 on 2018/1/23.
 */

public class WifiAdmin {
    private static final String TAG  = WifiAdmin.class.getSimpleName();
    private WifiManager mWifiManager;
    private WifiInfo mWifiInfo;
    private List<ScanResult> mResults;
    private List<WifiConfiguration> mWifiConfigurations;
    private WifiLock mWifiLock;
    private Context mContext;

    public WifiAdmin(Context context){
        mWifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        mWifiInfo = mWifiManager.getConnectionInfo();
        mContext = context;
    }

    /**
     * 开启Wifi
     */
    public void openWifi(){
        if (!mWifiManager.isWifiEnabled()){
            mWifiManager.setWifiEnabled(true);
        }else if (mWifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLING){
            Toast.makeText(mContext,"亲，Wifi正在开启，不用再开了", Toast.LENGTH_SHORT).show();
        }else if (mWifiManager.getWifiState() == WifiManager.WIFI_STATE_DISABLED){
            Toast.makeText(mContext,"亲，Wifi已经打开，不用再开了", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(mContext,"亲，Wifi已经打开，不用再开了", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 关闭wifi
     */
    public void colseWifi(){
        if (mWifiManager.isWifiEnabled()){
            mWifiManager.setWifiEnabled(false);
        }else if (mWifiManager.getWifiState() == WifiManager.WIFI_STATE_DISABLING){
            Toast.makeText(mContext,"亲，Wifi正在关闭，不用再关了", Toast.LENGTH_SHORT).show();
        }else if (mWifiManager.getWifiState() == WifiManager.WIFI_STATE_DISABLED ){
            Toast.makeText(mContext,"已经关闭", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(mContext,"更新关闭", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 查询状态
     */
    public void checkState(){
        if (mWifiManager.getWifiState() == WifiManager.WIFI_STATE_DISABLING){
            Toast.makeText(mContext , "Wifi正在关闭" , Toast.LENGTH_SHORT).show();
        }else if (mWifiManager.getWifiState() == WifiManager.WIFI_STATE_DISABLED){
            Toast.makeText(mContext , "Wifi已经关闭" , Toast.LENGTH_SHORT).show();
        }else if (mWifiManager.getWifiState() == WifiManager.WIFI_STATE_DISABLING){
            Toast.makeText(mContext , "Wifi正在开启" , Toast.LENGTH_SHORT).show();
        }else if (mWifiManager.getWifiState() == WifiManager.WIFI_STATE_DISABLED){
            Toast.makeText(mContext , "Wifi已经开启" , Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(mContext , "没有检测到状态" , Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 创建WifiLock
     */
    public void createWifiLock(){
        mWifiLock = mWifiManager.createWifiLock(TAG);
    }

    /**
     * 解锁WifiLock
     */
    public void releaseWifiLock(){
        if (mWifiLock.isHeld()){
            mWifiLock.acquire();
        }
    }

    /**
     * 锁定WifiLock
     */
    public void acuireWifiLock(){
        mWifiLock.acquire();
    }

    /**
     * 得到配置好的网络
     * @return
     */
    public List<WifiConfiguration> getWifiConfigurations(){
        return mWifiConfigurations;
    }

    /**
     * 指定配置好的网络进行链接
     */
    public void connectConfiguration(int index){
        if (index > mWifiConfigurations.size()){
            return;
        }
        mWifiManager.enableNetwork(mWifiConfigurations.get(index).networkId , true);
    }

    public void startScan() {
        mWifiManager.startScan();
        //得到扫描结果
        List<ScanResult> results = mWifiManager.getScanResults();
        // 得到配置好的网络连接
        mWifiConfigurations = mWifiManager.getConfiguredNetworks();
        if (results == null) {
            if(mWifiManager.getWifiState()==3){
                Toast.makeText(mContext,"当前区域没有无线网络",Toast.LENGTH_SHORT).show();
            }else if(mWifiManager.getWifiState()==2){
                Toast.makeText(mContext,"wifi正在开启，请稍后扫描", Toast.LENGTH_SHORT).show();
            }else{Toast.makeText(mContext,"WiFi没有开启", Toast.LENGTH_SHORT).show();
            }
        } else {
            mResults = new ArrayList();
            for(ScanResult result : results){
                if (result.SSID == null || result.SSID.length() == 0 || result.capabilities.contains("[IBSS]")) {
                    continue;
                }
                boolean found = false;
                for(ScanResult item: mResults){
                    if(item.SSID.equals(result.SSID)&&item.capabilities.equals(result.capabilities)){
                        found = true;break;
                    }
                }
                if(!found){
                    mResults.add(result);
                }
            }
        }
    }

    // 得到网络列表
    public List<ScanResult> getWifiList() {
        return mResults;
    }

    // 查看扫描结果
    public StringBuilder lookUpScan() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < mResults.size(); i++) {
            stringBuilder
                    .append("Index_" + new Integer(i + 1).toString() + ":");
            // 将ScanResult信息转换成一个字符串包
            // 其中把包括：BSSID、SSID、capabilities、frequency、level
            stringBuilder.append((mResults.get(i)).toString());
            stringBuilder.append("/n");
        }
        return stringBuilder;
    }

    // 得到MAC地址
    public String getMacAddress() {
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.getMacAddress();
    }

    // 得到接入点的BSSID
    public String getBSSID() {
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.getBSSID();
    }

    // 得到IP地址
    public int getIPAddress() {
        return (mWifiInfo == null) ? 0 : mWifiInfo.getIpAddress();
    }

    // 得到连接的ID
    public int getNetworkId() {
        return (mWifiInfo == null) ? 0 : mWifiInfo.getNetworkId();
    }

    // 得到WifiInfo的所有信息包
    public String getWifiInfo() {
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.toString();
    }

    // 添加一个网络并连接
    public void addNetwork(WifiConfiguration wcg) {

        int wcgID = mWifiManager.addNetwork(wcg);
        boolean b =  mWifiManager.enableNetwork(wcgID, true);
        System.out.println("a--" + wcgID);
        System.out.println("b--" + b);
    }

    // 断开指定ID的网络
    public void disconnectWifi(int netId) {
        mWifiManager.disableNetwork(netId);
        mWifiManager.disconnect();
    }
    public void removeWifi(int netId) {
        disconnectWifi(netId);
        mWifiManager.removeNetwork(netId);
    }

//然后是一个实际应用方法，只验证过没有密码的情况：
    public WifiConfiguration createWifiInfo(String SSID, String Password, int Type)
    {
        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        config.SSID = "\"" + SSID + "\"";

        if (Type == 1) // WIFICIPHER_NOPASS
        {
            config.wepKeys[0] = "";
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        }
        if (Type == 2) // WIFICIPHER_WEP
        {
            config.hiddenSSID = true;
            config.wepKeys[0] = "\"" + Password + "\"";
            config.allowedAuthAlgorithms
                    .set(WifiConfiguration.AuthAlgorithm.SHARED);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            config.allowedGroupCiphers
                    .set(WifiConfiguration.GroupCipher.WEP104);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        }
        if (Type == 3) // WIFICIPHER_WPA
        {
            config.preSharedKey = "\"" + Password + "\"";
            config.hiddenSSID = true;
            config.allowedAuthAlgorithms
                    .set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            config.allowedPairwiseCiphers
                    .set(WifiConfiguration.PairwiseCipher.TKIP);
            // config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedPairwiseCiphers
                    .set(WifiConfiguration.PairwiseCipher.CCMP);
            config.status = WifiConfiguration.Status.ENABLED;
        }
        return config;
    }

    private WifiConfiguration IsExsits(String SSID)
    {
        List<WifiConfiguration> existingConfigs = mWifiManager.getConfiguredNetworks();
        for (WifiConfiguration existingConfig : existingConfigs)
        {
            if (existingConfig.SSID.equals("\""+SSID+"\""))
            {
                return existingConfig;
            }
        }
        return null;
    }

}
