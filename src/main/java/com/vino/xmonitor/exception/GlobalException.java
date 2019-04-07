package com.vino.xmonitor.exception;


import com.vino.xmonitor.result.CodeMsg;

/**
 *
 * @author jiangyunxiong
 * @date 2018/5/22
 * <p>
 * 自定义全局异常类
 */
public class GlobalException extends RuntimeException {
    private CodeMsg codeMsg;

    public GlobalException(CodeMsg codeMsg) {
        super(codeMsg.toString());
        this.codeMsg = codeMsg;
    }

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }
}

