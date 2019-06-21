package com.yj.shop_product_service;

import com.yj.constant.RabbitMqConstant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 29029
 * @Version 1.0
 * @Time 16:13
 */
@Configuration
public class RabbitMqCon {

    /**
     *
     * 队列一：给搜索服务提供的队列
     * @return
     */
    @Bean
    public Queue getSearchQueue(){
        return new Queue(RabbitMqConstant.SEARCH_QUEUE,true,false,false);
    }

    /**
     * 队列二：用于删除索引库中的索引
     * @return
     */
    @Bean
    public Queue getSearchDelQueue(){
        return new Queue(RabbitMqConstant.SEARCH_DEL_QUEUE);
    }

    /**
     * 队列三：给页面静态化提供的队列
     * @return
     */
    @Bean
    public Queue getItemQueue(){
        return new Queue(RabbitMqConstant.ITEM_QUEUE);
    }

    /**
     * 队列四：用于商品下架
     * @return
     */
    @Bean
    public Queue getItemDelQueue(){
        return new Queue(RabbitMqConstant.ITEM_DEL_QUEUE);
    }

    /**
     * 交换机一：使用Fanout方法的交换机，会把内容传送给所有绑定的队列<广播方法>
     * @return
     */
    @Bean
    public FanoutExchange getExchange1(){
        return (FanoutExchange) ExchangeBuilder.fanoutExchange(RabbitMqConstant.PRODUCT_FANOUT_EXCHANGE).build();
    }

    /**
     * 交换机二：使用Fanout方法的交换机，会把内容传送给所有绑定的队列<广播方法>
     * @return
     */
    @Bean
    public FanoutExchange getExchange2(){
        return (FanoutExchange) ExchangeBuilder.fanoutExchange(RabbitMqConstant.PRODUCT_FANOUT_DEL_EXCHANGE).build();
    }

    /**
     * 队列一、队列三和交换机一进行绑定
     */
    @Bean
    public Binding bind1(Queue getSearchQueue, FanoutExchange getExchange1){
        return BindingBuilder.bind(getSearchQueue).to(getExchange1);
    }

    @Bean
    public Binding bind2(Queue getItemQueue, FanoutExchange getExchange1){
        return BindingBuilder.bind(getItemQueue).to(getExchange1);
    }

    /**
     * 队列二、队列四和交换机二进行绑定
     */
    @Bean
    public Binding bind3(Queue getSearchDelQueue, FanoutExchange getExchange2){
        return BindingBuilder.bind(getSearchDelQueue).to(getExchange2);
    }

    @Bean
    public Binding bind4(Queue getItemDelQueue, FanoutExchange getExchange2){
        return BindingBuilder.bind(getItemDelQueue).to(getExchange2);
    }

}
