# XNet
XNet是一个网络变化的监听组件

## how to use
1. app/build.gradle
```
dependencies {
    ...

    // xnet
    implementation 'com.yazao:xnet:1.0.1'

}
```

2. 权限配置  
在清单文件中如下配置：
```
    <!-- 用于访问网络 -->
    <uses-permission android:name="android.permission.INTERNET"/>
    <!-- 获取运营商信息，用于支持提供运营商信息相关的接口 -->
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    <!-- 用于访问wifi网络信息，wifi信息会用于进行网络定位 -->
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
```

3. 广播注册
基于Android 7.0 广播一律采用动态注册方式  

在 Activity的 onCreate()方法中  
```
//NetWork Change Listener
if (mNetChangeObserver == null) {
    mNetChangeObserver = new NetChangeObserver() {

        @Override
        public void onNetConnected(NetUtil.NetType type) {
            switch (type) {
                case NONE:// none
                    break;
                case WIFI:// wifi
                    XToast.show("当前处于Wifi网络", Gravity.CENTER); // XToast ： implementation 'com.yazao:xtoast:1.0.2'
                    break;
                default:
                    //移动网络
                    XToast.show("当前处于移动网络", Gravity.CENTER);
                    break;
            }
        }

        @Override
        public void onNetDisConnect() {
             XToast.show("网络已断开，请检查网络", Gravity.CENTER);
        }
    };
}
NetChangeReceiver.registerObserver(mNetChangeObserver);

//动态注册广播
NetChangeReceiverUtil.getInstance().registerNetworkStateReceiver(this);
```

在 onDestroy()方法中：  
```
@Override
protected void onDestroy() {
    super.onDestroy();

    if (mNetChangeObserver != null) {
        NetChangeReceiver.unRegisterObserver(mNetChangeObserver);
    }
    NetChangeReceiverUtil.getInstance().unRegisterNetworkStateReceiver(this);
}
```

