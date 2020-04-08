package com.laowan.product.config;

import com.laowan.product.enums.ExchangeEnum;
import com.laowan.product.enums.QueueEnum;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: rabbitmq
 * @description: 测试hash模式
 * @author: wanli
 * @create: 2019-06-13 17:33
 **/
//@Configuration
public class TestHashQueueConfig {

    @Bean
    public HashExchange hashExchange() {
        return new HashExchange(ExchangeEnum.HASH_EXCHANGE.getValue());
    }

    @Bean
    public Queue queue() {
        return new Queue(QueueEnum.TEST_TOPIC_FOUR.getName(), true);
    }

    /**
     * 将用户注册队列绑定到路由交换配置上并设置指定路由键进行转发
     * <p>
     * BindingBuilder  构建绑定时，才会建立路由，队列以及两者的绑定，同@RabbitListener(bindings = @QueueBinding()类似
     *
     * @return
     */
/*    @Bean
    public Binding binding() {

        Queue queue = new Queue("hash1", true);
       return BindingBuilder.bind(queue).to(hashExchange()).;
    }*/
/*    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(hashExchange()).with(QueueEnum.TEST_DIRECT.getRoutingKey());
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(hashExchange()).with(QueueEnum.TEST_DIRECT.getRoutingKey());
    }*/
}
