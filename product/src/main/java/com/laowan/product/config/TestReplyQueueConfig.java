package com.laowan.product.config;

import com.laowan.product.enums.ExchangeEnum;
import com.laowan.product.enums.QueueEnum;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: rabbitmq
 * @description: 测试direct模式
 * @author: wanli
 * @create: 2019-06-13 17:33
 **/
@Configuration
public class TestReplyQueueConfig {

    /**
     * 配置路由交换对象实例
     *
     * @return
     */
    @Bean
    public DirectExchange replyExchange() {
        return new DirectExchange(ExchangeEnum.REPLY_EXCHANGE.getValue());
    }

    /**
     * 配置用户注册队列对象实例
     * 并设置持久化队列
     *
     * @return
     */
    @Bean
    public Queue replyQueue() {
        return new Queue(QueueEnum.TEST_REPLY.getName(), true);
    }

    /**
     * 将用户注册队列绑定到路由交换配置上并设置指定路由键进行转发
     * <p>
     * BindingBuilder  构建绑定时，才会建立路由，队列以及两者的绑定，同@RabbitListener(bindings = @QueueBinding()类似
     *
     * @return
     */
    @Bean
    public Binding replyBinding() {
        return BindingBuilder.bind(replyQueue()).to(replyExchange()).with(QueueEnum.TEST_REPLY.getRoutingKey());
    }
}
