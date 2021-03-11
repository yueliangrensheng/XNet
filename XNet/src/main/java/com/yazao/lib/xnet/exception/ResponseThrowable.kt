package com.yazao.lib.xnet.exception

open class ResponseThrowable : Exception {

    var code: Int

    var errorMsg: String

    constructor(code: Int, msg: String, e: Throwable? = null) : super(e) {
        this.code = code
        this.errorMsg = msg
    }

    constructor(error: ERROR, e: Throwable? = null) : super(e) {
        this.code = error.getCode()
        this.errorMsg = error.getMessage()
    }

}
