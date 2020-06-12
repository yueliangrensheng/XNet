
package com.yazao.lib.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;


public class NetChangeReceiver extends BroadcastReceiver {

    public final static String CUSTOM_ANDROID_NET_CHANGE_ACTION = "com.yazao.conn.CONNECTIVITY_CHANGE";
    public final static String ANDROID_NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    public final static String ANDROID_NET_WIFI_STATE_CHANGE_ACTION = "android.net.wifi.WIFI_STATE_CHANGED";
    public final static String ANDROID_NET_STATE_CHANGE_ACTION = "android.net.wifi.STATE_CHANGE";

    private static boolean isNetAvailable = false;
    private static NetUtil.NetType mNetType;
    private static ArrayList<NetChangeObserver> mNetChangeObservers = new ArrayList<NetChangeObserver>();
    private static BroadcastReceiver mBroadcastReceiver;

    public static BroadcastReceiver getReceiver() {
        if (mBroadcastReceiver == null) {
            mBroadcastReceiver = new NetChangeReceiver();
        }
        return mBroadcastReceiver;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        mBroadcastReceiver = NetChangeReceiver.this;
        if (intent.getAction().equalsIgnoreCase(ANDROID_NET_CHANGE_ACTION) || intent.getAction().equalsIgnoreCase(CUSTOM_ANDROID_NET_CHANGE_ACTION)
                || intent.getAction().equalsIgnoreCase(ANDROID_NET_WIFI_STATE_CHANGE_ACTION)
                || intent.getAction().equalsIgnoreCase(ANDROID_NET_STATE_CHANGE_ACTION)
        ) {
            if (!NetUtil.getInstance().isNetworkconnected(context)) {
                isNetAvailable = false;
            } else {
                isNetAvailable = true;
                mNetType = NetUtil.getInstance().getAPNType(context);
            }
            notifyObserver();
        }
    }

    public static boolean isNetworkAvailable() {
        return isNetAvailable;
    }

    public static NetUtil.NetType getAPNType() {
        return mNetType;
    }

    private void notifyObserver() {
        if (!mNetChangeObservers.isEmpty()) {
            int size = mNetChangeObservers.size();
            for (int i = 0; i < size; i++) {
                NetChangeObserver observer = mNetChangeObservers.get(i);
                if (observer != null) {
                    if (isNetworkAvailable()) {
                        observer.onNetConnected(mNetType);
                    } else {
                        observer.onNetDisConnect();
                    }
                }
            }
        }
    }

    public static void registerObserver(NetChangeObserver observer) {
        if (mNetChangeObservers == null) {
            mNetChangeObservers = new ArrayList<NetChangeObserver>();
        }
        mNetChangeObservers.add(observer);
    }

    public static void unRegisterObserver(NetChangeObserver observer) {
        if (mNetChangeObservers != null && mNetChangeObservers.size() > 0) {
            if (mNetChangeObservers.contains(observer)) {
                mNetChangeObservers.remove(observer);
            }
        }
    }
}