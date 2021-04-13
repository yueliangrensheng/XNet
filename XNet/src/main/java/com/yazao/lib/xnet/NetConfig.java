package com.yazao.lib.xnet;

public class NetConfig {

    private static NetConfig ourInstance = new NetConfig();

    public static NetConfig getInstance() {
        return ourInstance;
    }

    private NetConfig() {
    }

    private Boolean isShowNetLog = false;//网络包下面 是否输出日志

    public Boolean getShowNetLog() {
        return isShowNetLog;
    }

    public void setShowNetLog(Boolean showNetLog) {
        isShowNetLog = showNetLog;
    }
}
