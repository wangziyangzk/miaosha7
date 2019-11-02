package com.wzy.miaosha7.controller;

import com.wzy.miaosha7.domain.MiaoshaUser;
import com.wzy.miaosha7.result.Result;
import com.wzy.miaosha7.service.MiaoshaUserService;
import com.wzy.miaosha7.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {

    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private MiaoshaUserService miaoshaUserService;

    @RequestMapping("to_login")
    public String toLogin() {
        return "login";
    }

//    @RequestMapping("/do_login")
//    @ResponseBody
//    public Result<Boolean> doLogin(HttpServletResponse response, LoginVo loginVo) {
//        log.info(loginVo.toString());
//        miaoshaUserService.login(response,loginVo);
//        return Result.success(true);
//    }

    @RequestMapping(value = "/getid",method = RequestMethod.GET)
    @ResponseBody
    public Result<MiaoshaUser> getUserId(@RequestParam long id) {
        MiaoshaUser user = miaoshaUserService.getById(id);
        if(user != null) {
            return Result.success(user);
        }else {
            throw new RuntimeException("没有用户存在");
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> doLogin(HttpServletResponse response, @Valid LoginVo loginVo) {
        log.info(loginVo.toString());
        miaoshaUserService.login(response,loginVo);
        return Result.success(true);
    }
}
