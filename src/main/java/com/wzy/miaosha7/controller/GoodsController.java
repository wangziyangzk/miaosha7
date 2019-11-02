package com.wzy.miaosha7.controller;

import com.wzy.miaosha7.domain.MiaoshaUser;
import com.wzy.miaosha7.service.MiaoshaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private MiaoshaUserService miaoshaUserService;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("to_list")
    public String list(Model model, MiaoshaUser miaoshaUser) {
        model.addAttribute("user",miaoshaUser);
        return "goods_list";
    }
}
