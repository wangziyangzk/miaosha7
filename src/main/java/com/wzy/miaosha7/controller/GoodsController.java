package com.wzy.miaosha7.controller;

import com.wzy.miaosha7.domain.MiaoshaUser;
import com.wzy.miaosha7.service.GoodsService;
import com.wzy.miaosha7.service.MiaoshaUserService;
import com.wzy.miaosha7.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private MiaoshaUserService miaoshaUserService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private RedisTemplate redisTemplate;

    
    @RequestMapping("to_list")
    public String list(Model model, MiaoshaUser miaoshaUser) {
        model.addAttribute("user",miaoshaUser);
        List<GoodsVo> goodslist = goodsService.listGoodsVo();
        model.addAttribute("goodsList",goodslist);
        return "goods_list";
    }

    @RequestMapping("to_detail/{goodsId}")
    public String miaoshaDetail(Model model, MiaoshaUser user, @PathVariable("goodsId")long goodsId) {
        model.addAttribute("user",user);

        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods",goods);

        //判断秒杀商品状态
        long startTime = goods.getStartDate().getTime();
        long endTime = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int miaoshaStatus = 0;
        int remainSeconds = 0;

        if(now < startTime) {
            miaoshaStatus = 0;
            remainSeconds = (int)((startTime - now)/1000);
        }else if(now > endTime) {
            //秒杀已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        }else {
            //秒杀进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }

        model.addAttribute("miaoshaStatus",miaoshaStatus);
        model.addAttribute("remainSeconds",remainSeconds);
        return "goods_detail";
    }
}



















