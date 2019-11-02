package com.wzy.miaosha7.controller;

import com.wzy.miaosha7.domain.User;
import com.wzy.miaosha7.result.Result;
import com.wzy.miaosha7.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Autowired
    private UserService userService;

    @RequestMapping("/demo")
    public String demo(){
        return "ceshi";
    }

    @RequestMapping("/success")
    @ResponseBody
    public Result<String> demo1() {
        return Result.success("成功的调用");
    }

    @RequestMapping("/user")
    @ResponseBody
    public Result<User> get() {
        User user = userService.getById(2);
        return Result.success(user);
    }

    @RequestMapping("/tx")
    public Result<Boolean> dbtx() {
        userService.tx();
        return Result.success(true);
    }
}
