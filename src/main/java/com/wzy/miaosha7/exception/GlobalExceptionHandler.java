package com.wzy.miaosha7.exception;

import com.wzy.miaosha7.result.CodeMsg;
import com.wzy.miaosha7.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
** 全局异常处理器
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest request,Exception e) {
        e.printStackTrace();
        //判断异常种类
        if(e instanceof GlobalException) {
            GlobalException ex = (GlobalException) e;
            return Result.error(ex.getMsg());
        }
        return Result.error(CodeMsg.SERVER_ERROR);
    }
}
