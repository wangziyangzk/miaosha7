package com.wzy.miaosha7.rabbitmq;


import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MQConfig {

    public static final String Quene = "quene";
    public static final String TOPIC_Quene1 = "quene2";
    public static final String TOPIC_Quene2 = "quene3";
    public static final String HEADER_QUENE = "quene4";
    public static final String TOPIC_EXCHANGE = "topic_exchenge";
    public static final String ROUTING_KEY1 = "topic.key1";
    public static final String ROUTING_KEY2 = "topic.*";
    public static final String FANOUT_EXCHANGE = "fanout_exchange";
    public static final String Headers_EXCHANGE = "headers_exchange";

    public static final String MIAOSHA_QUENE = "miaosha_quene";

    @Bean
    public Queue miaoshaQuene() {
        return new Queue(MIAOSHA_QUENE,true);
    }
    /*
     **  Direct交换机模式
     */
    @Bean
    public Queue quene() {
        return new Queue(Quene,true); //是否做持久化
    }

    /*
     **  Topic交换机模式
     */
    @Bean
    public Queue topicQuene1() {
        return new Queue(TOPIC_Quene1,true);
    }

    @Bean
    public Queue topicQuene2() {
        return new Queue(TOPIC_Quene2,true);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public Binding topicBinding1() {
        return BindingBuilder.bind(topicQuene1()).to(topicExchange()).with(ROUTING_KEY1);
    }

    @Bean
    public Binding topicBinding2() {
        return BindingBuilder.bind(topicQuene2()).to(topicExchange()).with(ROUTING_KEY2);
    }

    /*
    ** Fanout模式 交换机Exchange
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    @Bean
    public Binding FanoutBinding() {
        return BindingBuilder.bind(topicQuene1()).to(fanoutExchange());
    }

    /*
    ** Header模式 通过匹配Key-Value
     */
    @Bean
    public HeadersExchange headersExchange() {
        return new HeadersExchange(Headers_EXCHANGE);
    }

    @Bean
    public Queue HeaderQuene() {
        return new Queue(HEADER_QUENE,true);
    }

    @Bean
    public Binding headerBinding() {
        Map<String,String> map  =new HashMap<String,String>();
        map.put("header1","value1");
        map.put("header2","value2");
        return BindingBuilder.bind(HeaderQuene()).to(headersExchange()).whereAll((HashMap)map).match();
    }
}
