package com.wzy.miaosha7.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wzy.miaosha7.domain.MiaoshaOrder;
import com.wzy.miaosha7.domain.MiaoshaUser;
import com.wzy.miaosha7.service.GoodsService;
import com.wzy.miaosha7.service.MiaoshaService;
import com.wzy.miaosha7.service.OrderService;
import com.wzy.miaosha7.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MQReceiver {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MiaoshaService miaoshaService;

    private static Logger log = LoggerFactory.getLogger(MQReceiver.class);

    @RabbitListener(queues = MQConfig.Quene)
    public void receive(String message) {
        log.info("receive + "+message);
    }

    @RabbitListener(queues = MQConfig.TOPIC_Quene1)
    public void receive2(String message) {
        log.info("receive + "+message);
    }

    @RabbitListener(queues = MQConfig.TOPIC_Quene2)
    public void receive3(String message) {
        log.info("receive + "+message);
    }

    @RabbitListener(queues = MQConfig.HEADER_QUENE)
    public void receive3(byte[] message) {
        log.info("receive + "+message.toString());
    }

    @RabbitListener(queues = MQConfig.MIAOSHA_QUENE)
    public void receiveMiaosha (String message) {
        log.info("receive message" + message);
        MiaoshaMessage mm = JSON.parseObject(message,MiaoshaMessage.class);
        long goodsId = mm.getGoodsId();
        MiaoshaUser user = mm.getUser();
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getGoodsStock();
        if(stock <= 0) {
            return;
        }
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(),goodsId);
        if(order != null) {
            return;
        }
        miaoshaService.miaosha(user,goods);
    }
}
