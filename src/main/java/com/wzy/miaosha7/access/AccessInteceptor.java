package com.wzy.miaosha7.access;

import com.alibaba.fastjson.JSON;
import com.wzy.miaosha7.domain.MiaoshaUser;
import com.wzy.miaosha7.result.CodeMsg;
import com.wzy.miaosha7.result.Result;
import com.wzy.miaosha7.service.MiaoshaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class AccessInteceptor extends HandlerInterceptorAdapter {

    @Autowired
    private MiaoshaUserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod) {
            MiaoshaUser user = (MiaoshaUser) request.getSession().getAttribute("user");
            if(user != null) {
                UserContext.setUser(user);
                HandlerMethod hm = (HandlerMethod)handler;
                AccessLimit al = hm.getMethodAnnotation(AccessLimit.class);
                if(al == null) {
                    return true;
                }
                int seconds = al.seconds();
                int maxCount = al.maxCount();
                boolean needLogin = al.needLogin();
                //需要登录的界面进行拦截
                if(needLogin) {
                    if(user == null) {
                        render(response, CodeMsg.SESSION_ERROR);
                    }
                }
                Integer count = (Integer) redisTemplate.opsForValue().get(user.getId());
                if(count == null) {
                    redisTemplate.opsForValue().set(user.getId(),1);
                }else if(count < 5){
                    Integer count2 = (Integer) redisTemplate.opsForValue().get(user.getId());
                    count2++;
                    redisTemplate.opsForValue().set(user.getId(),count2);
                }else if(count >=5) {
                    render(response,CodeMsg.ACCESS_LIMIT_REACHED);
                    return false;
                }
            }
        }
        return true;
    }

    private void render(HttpServletResponse response, CodeMsg codeMsg) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        String str = JSON.toJSONString(Result.error(codeMsg));
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();
    }


}
