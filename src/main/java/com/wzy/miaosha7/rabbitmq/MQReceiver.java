package com.wzy.miaosha7.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


@Service
public class MQReceiver {

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
}
