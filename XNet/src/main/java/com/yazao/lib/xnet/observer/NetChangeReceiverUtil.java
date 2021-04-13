package com.yazao.lib.xnet.observer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Build;

import com.yazao.lib.xlog.Log;
import com.yazao.lib.xnet.NetConfig;


/**
 * Author:  MoonLife
 * Time: 2015/12/15 15:27
 * Descripton: 注册、反注册、发送 广播
 */
public class NetChangeReceiverUtil {

    private static BroadcastReceiver netChangeObserverReceiver;

    private static NetChangeReceiverUtil instance;

    private NetChangeReceiverUtil() {
    }

    public static NetChangeReceiverUtil getInstance() {
        if (instance == null) {
            synchronized (NetChangeReceiverUtil.class) {
                if (instance == null) {
                    instance = new NetChangeReceiverUtil();
                }
                if (netChangeObserverReceiver == null) {
                    netChangeObserverReceiver = NetChangeReceiver.getReceiver();
                }
            }
        }
        return instance;
    }

    /**
     * 方法描述：手动注册广播
     * @author zhaishaoping
     * @time 2019-06-28 11:28
     * @return
     */
    public void registerNetworkStateReceiver(Context mContext) {
        if (netChangeObserverReceiver == null) {
            netChangeObserverReceiver = NetChangeReceiver.getReceiver();
        }

        IntentFilter filter = new IntentFilter();
        filter.addAction(NetChangeReceiver.CUSTOM_ANDROID_NET_CHANGE_ACTION);
        filter.addAction(NetChangeReceiver.ANDROID_NET_CHANGE_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);

        //Android 7.0以上需要动态注册在页面加载后注册广播监听网络
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        }

        mContext.getApplicationContext().registerReceiver(netChangeObserverReceiver, filter);
        if (NetConfig.getInstance().getShowNetLog()){
            Log.i("registerNetworkStateReceiver");
        }
    }

    /**
     * 方法描述：手动取消注册广播
     * @author zhaishaoping
     * @time 2019-06-28 11:28
     * @return
     */
    public void unRegisterNetworkStateReceiver(Context mContext) {
        if (netChangeObserverReceiver != null) {
            try {
                mContext.getApplicationContext().unregisterReceiver(netChangeObserverReceiver);
                netChangeObserverReceiver = null;
                if (NetConfig.getInstance().getShowNetLog()){
                    Log.i("unRegisterNetworkStateReceiver");
                }
            } catch (Exception e) {
                if (NetConfig.getInstance().getShowNetLog()){
                    Log.d(e.getMessage());
                }
            }
        }

    }

    /**
     * 方法描述：发送广播
     * @author zhaishaoping
     * @time 2019-06-28 11:28
     * @return
     */
    public void sendNetChangeBroadcastReceiver(Context mContext) {
        Intent intent = new Intent();
        intent.setAction(NetChangeReceiver.CUSTOM_ANDROID_NET_CHANGE_ACTION);
        mContext.sendBroadcast(intent);
        if (NetConfig.getInstance().getShowNetLog()){
            Log.i("sendNetChangeBroadcastReceiver");
        }
    }
}
