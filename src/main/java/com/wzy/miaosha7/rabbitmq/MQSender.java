package com.wzy.miaosha7.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.wzy.miaosha7.rabbitmq.MQConfig.*;

@Service
public class MQSender {

    private static Logger log = LoggerFactory.getLogger(MQSender.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    /*
    **  Direct交换机模式
     */
    public void send(Object message) {
        String strMessage = message.toString();
        amqpTemplate.convertAndSend(Quene,strMessage);
    }

    /*
    ** Topic交换机模式
     */
    public void sendTopic(Object message) {
        String msg = message.toString();
        log.info("sendTopicMsg" + msg);
        amqpTemplate.convertAndSend(TOPIC_EXCHANGE,ROUTING_KEY1,msg+"1");
        amqpTemplate.convertAndSend(TOPIC_EXCHANGE,ROUTING_KEY1,msg+"2");
    }

    /*
    ** Fanout交换机模式
     */
    public void sendFanout(Object message) {
        String msg = message.toString();
        amqpTemplate.convertAndSend(FANOUT_EXCHANGE,"",msg+ "1");
    }

    /*
    ** Header模式
     */
    public void sendHeader(Object message) {
        String msg = message.toString();
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("header1","value1");
        messageProperties.setHeader("header2","value2");
        Message obj = new Message(msg.getBytes(),messageProperties);
        amqpTemplate.convertAndSend(Headers_EXCHANGE, HEADER_QUENE,obj);
    }
}
