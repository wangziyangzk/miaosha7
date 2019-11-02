package com.wzy.miaosha7.exception;

import com.wzy.miaosha7.result.CodeMsg;

public class GlobalException extends RuntimeException {
    private static final long serialVersionUID = 1;

    private CodeMsg codeMsg;

    public GlobalException(CodeMsg msg){
        super(msg.toString());
        this.codeMsg = msg;
    }

    public CodeMsg getMsg() {
        return codeMsg;
    }
}
