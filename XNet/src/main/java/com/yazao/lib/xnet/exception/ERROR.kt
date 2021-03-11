package com.yazao.lib.xnet.exception

enum class ERROR(private val code: Int, private val errMsg: String) {

    /**
     * 未知错误
     */
    UNKNOWN(1000, "未知错误"),

    /**
     * 解析错误
     */
    PARSE_ERROR(1001, "解析错误"),

    /**
     * 网络错误
     */
    NETWORK_ERROR(1002, "网络错误"),

    /**
     * 协议错误
     */
    HTTP_ERROR(1003, "协议错误"),

    /**
     * 证书错误
     */
    SSL_ERROR(1004, "证书错误"),

    /**
     * 连接超时
     */
    TIMEOUT_ERROR(1005, "连接超时"),
    /**
     * 未知HOST错误
     */
    UNKNOWN_HOST_ERROR(1006, "未知HOST错误");

    fun getMessage(): String {
        return errMsg
    }

    fun getCode(): Int {
        return code
    }
}