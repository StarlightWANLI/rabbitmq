package com.laowan.product.config;

import com.laowan.product.enums.ExchangeEnum;
import com.laowan.product.enums.QueueEnum;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: rabbitmq
 * @description: 测试fanout模式      类似广播模式，所有绑定在交换器上的队列都会受到消息
 * @author: wanli
 * @create: 2019-06-13 17:33
 **/
@Configuration
public class TestFanoutQueueConfig {

    /**
     * 配置路由交换对象实例
     *
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(ExchangeEnum.FANOUT_EXCHANGE.getValue());
    }

    /**
     * 配置用户注册队列对象实例
     * 并设置持久化队列
     *
     * @return
     */
    @Bean
    public Queue queueFanoutOne() {
        return new Queue(QueueEnum.TEST_FANOUT_ONE.getName(), true);
    }

    @Bean
    public Queue queueFanoutTwo() {
        return new Queue(QueueEnum.TEST_FANOUT_TWO.getName(), true);
    }

    @Bean
    public Queue queueFanoutThree() {
        return new Queue(QueueEnum.TEST_FANOUT_THREE.getName(), true);
    }


    /**
     * Fanout 模式多个队列，都绑定到同一个FanoutExchange上
     *
     * @return
     */
    @Bean
    public Binding bindingOne() {
        return BindingBuilder.bind(queueFanoutOne()).to(fanoutExchange());
    }

    @Bean
    public Binding bindingTwo() {
        return BindingBuilder.bind(queueFanoutTwo()).to(fanoutExchange());
    }

    @Bean
    public Binding bindingThree() {
        return BindingBuilder.bind(queueFanoutThree()).to(fanoutExchange());
    }
}
