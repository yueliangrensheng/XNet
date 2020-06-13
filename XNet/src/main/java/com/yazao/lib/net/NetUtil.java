package com.yazao.lib.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import com.yazao.lib.xlog.Log;

import java.util.Locale;

/**
 * Author:  MoonLife
 * Time: 2015/12/15 14:32
 * Descripton:
 */
public class NetUtil {
    private static NetUtil ourInstance = new NetUtil();

    public static NetUtil getInstance() {
        return ourInstance;
    }

    private NetUtil() {
    }

    /**
     * 判断Network是否开启(包括移动网络和Wifi)
     *
     * @param context
     * @return
     */
    public boolean isNetworkEnabled(Context context) {
        return (isNetEnabled(context) || isWIFIEnabled(context));
    }

    /**
     * 判断Network是否连接成功(包括移动网络和Wifi)
     *
     * @param context
     * @return
     */
    public boolean isNetworkconnected(Context context) {
        return (isWifiConnected(context) || isNetContected(context));
    }

    /**
     * 判断移动网络是否开启
     *
     * @param context
     * @return
     */
    public boolean isNetEnabled(Context context) {
        boolean enable = false;
        if (context == null) {
            return enable;
        }
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            if (telephonyManager.getNetworkType() != TelephonyManager.NETWORK_TYPE_UNKNOWN) {
                enable = true;
                Log.i("移动网络已开启");
            }
        }
        return enable;
    }

    /**
     * 判断Wifi是否开启
     *
     * @param context
     * @return
     */
    public boolean isWIFIEnabled(Context context) {
        boolean enable = false;
        if (context == null) {
            return enable;
        }
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null && wifiManager.isWifiEnabled()) {
            enable = true;
            Log.i("WIFI已开启");
        }
        return enable;
    }

    /**
     * 判断移动网络连接是否成功
     * 需要权限： android.permission.ACCESS_NETWORK_STATE
     *
     * @param context
     * @return
     */
    public boolean isNetContected(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobileNetworkInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetworkInfo != null && mobileNetworkInfo.isConnected()) {

            Log.i("移动网络已连接");
            return true;
        }
        Log.i("移动网络未连接");
        return false;
    }

    /**
     * 判断Wifi是否连接成功
     *
     * @param context
     * @return
     */
    public boolean isWifiConnected(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetworkInfo != null && wifiNetworkInfo.isConnected()) {

            Log.i("Wifi网络已连接");
            return true;
        }
        Log.i("Wifi网络未连接");
        return false;
    }

    public NetType getAPNType(Context context) {
        if (context == null) {
            return null;
        }
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return NetType.NONE;
        }
        int nType = networkInfo.getType();

        if (nType == ConnectivityManager.TYPE_MOBILE) {
            if (networkInfo.getExtraInfo().toLowerCase(Locale.getDefault()).equals("cmnet")) {
                return NetType.CMNET;
            } else {
                return NetType.CMWAP;
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            return NetType.WIFI;
        }
        return NetType.NONE;
    }

    public enum NetType {
        WIFI("wifi"),//wifi方式入网
        CMNET("cmnet"),//net方式入网
        CMWAP("cmwap"),//wap方式入网
        NONE("");//none

        String value = "";

        NetType(String value) {
            this.value = value;
        }
    }
}
