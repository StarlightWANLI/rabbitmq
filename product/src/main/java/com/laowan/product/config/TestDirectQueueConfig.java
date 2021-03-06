package com.laowan.product.config;

import com.laowan.product.enums.ExchangeEnum;
import com.laowan.product.enums.QueueEnum;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @program: rabbitmq
 * @description: 测试direct模式
 * @author: wanli
 * @create: 2019-06-13 17:33
 **/
@Configuration
public class TestDirectQueueConfig {

    /**
     * 配置路由交换对象实例
     *
     * @return
     */
    @Bean
    public DirectExchange directExchange() {
      //  DirectExchange directExchange = new DirectExchange(ExchangeEnum.DIRECT_EXCHANGE.getValue());

        return new DirectExchange(ExchangeEnum.DIRECT_EXCHANGE.getValue());
    }


    /**
     * 配置用户注册队列对象实例
     * 并设置持久化队列
     *
     * @return
     */
    @Bean
    public Queue queue() {
        return new Queue(QueueEnum.TEST_DIRECT.getName(), true);
    }

    /**
     * 将用户注册队列绑定到路由交换配置上并设置指定路由键进行转发
     * <p>
     * BindingBuilder  构建绑定时，才会建立路由，队列以及两者的绑定，同@RabbitListener(bindings = @QueueBinding()类似
     *
     * @return
     */
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(directExchange()).with(QueueEnum.TEST_DIRECT.getRoutingKey());
    }
}
