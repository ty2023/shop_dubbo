package com.yj.shop_user_service;

import org.springframework.amqp.core.*;
import com.yj.constant.RabbitMqConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 29029
 * @version 1.0
 * @time 11:54
 */
@Configuration
public class RabbitMqUserConfig {

    /**
     *
     * 队列一：给邮件提供的队列
     * @return
     */
    @Bean
    public Queue getUserQueue(){
        return new Queue(RabbitMqConstant.USER_EMAIL,true,false,false);
    }

    /**
     * 交换机一：使用Fanout方法的交换机，会把内容传送给所有绑定的队列<广播方法>
     * @return
     */
    @Bean
    public FanoutExchange getExchange1(){
        return (FanoutExchange) ExchangeBuilder.fanoutExchange(RabbitMqConstant.USER_EXCHANGE).build();
    }


    @Bean
    public Binding bind1(Queue getUserQueue, FanoutExchange getExchange1){
        return BindingBuilder.bind(getUserQueue).to(getExchange1);
    }



}
