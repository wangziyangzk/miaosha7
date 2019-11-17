package com.wzy.miaosha7.controller;

import com.wzy.miaosha7.domain.MiaoshaOrder;
import com.wzy.miaosha7.domain.MiaoshaUser;
import com.wzy.miaosha7.domain.OrderInfo;
import com.wzy.miaosha7.result.CodeMsg;
import com.wzy.miaosha7.service.GoodsService;
import com.wzy.miaosha7.service.MiaoshaService;
import com.wzy.miaosha7.service.MiaoshaUserService;
import com.wzy.miaosha7.service.OrderService;
import com.wzy.miaosha7.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MiaoshaController {

    @Autowired
    private MiaoshaUserService miaoshaUserService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private MiaoshaService miaoshaService;

    @Autowired
    private OrderService orderService;

    @RequestMapping("/do_miaosha")
    public String list(Model model, MiaoshaUser user,@RequestParam("goodsId")long goodsId) {
        model.addAttribute("user",user);
        if(user == null) {
            return "login";
        }

        //判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getGoodsStock();
        if(stock <= 0) {
            model.addAttribute("errMsg", CodeMsg.MIAO_SHA_OVER.getMsg());
            return "miaosha_fail";
        }
        //判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(),goodsId);
        if(order != null) {
            model.addAttribute("errMsg",CodeMsg.REPEATE_MIAOSHA.getMsg());
            return "miaosha_fail";
        }
        //减库存，下订单
        OrderInfo orderInfo = miaoshaService.miaosha(user,goods);
        model.addAttribute("orderInfo",orderInfo);
        model.addAttribute("goods",goods);
        return "order_detail";
    }
}
