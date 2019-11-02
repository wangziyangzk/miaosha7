package com.wzy.miaosha7.service;

import com.wzy.miaosha7.dao.MiaoshaUserDao;
import com.wzy.miaosha7.domain.MiaoshaUser;
import com.wzy.miaosha7.util.MD5Util;
import com.wzy.miaosha7.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Service
public class MiaoshaUserService {

    public static final String COOKIE_NAME_TOKEN = "token";

    public static final int MAX_AGE = 1000*60*60;

    @Autowired
    private MiaoshaUserDao miaoshaUserDao;

    public MiaoshaUser getById(long id) {
        return miaoshaUserDao.selectUser(id);
    }

    public boolean login(HttpServletResponse response, LoginVo loginVo) {
        if(loginVo == null) {
            throw new RuntimeException("登录用户为空");
        }
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

        MiaoshaUser user = miaoshaUserDao.selectUser(Long.parseLong(mobile));
        if(user == null) {
            throw new RuntimeException("手机号码不存在");
        }

        //判断密码
        String dbPass = user.getPassword();
        String saltdb = user.getSalt();
        String calPass = MD5Util.inputPassToDBPass(password,saltdb);
        if(dbPass != calPass) {
            throw new RuntimeException("密码错误");
        }

        String token = UUID.randomUUID().toString();
        addCookie(response,token,user);
        return true;
    }

    private void addCookie(HttpServletResponse response, String token, MiaoshaUser user) {
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN,token);
        cookie.setMaxAge(MAX_AGE);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
