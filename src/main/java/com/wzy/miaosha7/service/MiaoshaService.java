package com.wzy.miaosha7.service;

import com.wzy.miaosha7.domain.MiaoshaUser;
import com.wzy.miaosha7.domain.OrderInfo;
import com.wzy.miaosha7.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MiaoshaService {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goodsVo) {
        //减库存，下订单，写入秒杀订单
        goodsService.reduceStock(goodsVo);
        return orderService.createOrder(user,goodsVo);
    }
}
