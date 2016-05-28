package com.limb.customview.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * Created by limb on 2016/5/26.
 */
public class NetConnectedUtils {
    public boolean isConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(context.TELEPHONY_SERVICE);
        NetworkInfo nwInfo = cm.getActiveNetworkInfo();
        if(nwInfo==null || !cm.getBackgroundDataSetting()){
            return false;
        }
        //获取网络连接类型
        int netType = nwInfo.getType();

        int netSubtype = nwInfo.getSubtype();

        if(netType == ConnectivityManager.TYPE_WIFI &&
                netSubtype == TelephonyManager.NETWORK_TYPE_UMTS &&
                !tm.isNetworkRoaming()){
            return nwInfo.isConnected();
        }
        return false;
    }
}
