package com.laowan.product.config;

import com.laowan.product.enums.ExchangeEnum;
import com.laowan.product.enums.QueueEnum;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: rabbitmq
 * @description: 测试topic模式      主要是交换器和队列使用#和*的松耦合绑定
 *   其中“*”号匹配一个单词，“#”用来匹配多个单词
 * @author: wanli
 * @create: 2019-06-13 17:33
 **/
@Configuration
public class TestTopicQueueConfig {

    private static  final  String BINDING_KEY1 = "test.topic.*";

    private static  final  String BINDING_KEY2 = "test.topic.#";


    /**
     * 配置路由交换对象实例
     * @return
     */
    @Bean
    public TopicExchange topicExchange()
    {
        return new TopicExchange(ExchangeEnum.TOPIC_EXCHANGE.getValue());
    }

    /**
     *
     * 由于Config配置类中默认根据方法名生成bean，所以在项目中的所有配置类中，不要有相同的方法名称（不在一个配置类中，也不能相同）
     * Invalid bean definition with name 'queueOne' defined in class path,
     * 配置用户注册队列对象实例
     * 并设置持久化队列
     * @return
     */
    @Bean
    public Queue queueTopicOne()
    {
        return new Queue(QueueEnum.TEST_TOPIC_ONE.getName(),true);
    }

    @Bean
    public Queue queueTopicTwo()
    {
        return new Queue(QueueEnum.TEST_TOPIC_TWO.getName(),true);
    }

    @Bean
    public Queue queueTopicThree()
    {
        return new Queue(QueueEnum.TEST_TOPIC_THREE.getName(),true);
    }

    @Bean
    public Queue queueTopicFour()
    {
        return new Queue(QueueEnum.TEST_TOPIC_FOUR.getName(),true);
    }


    /**
     *
     * 队列1,2采用*号和交换器模糊绑定
     *
     * 将用户注册队列绑定到路由交换配置上并设置指定路由键进行转发
     * BindingBuilder  构建绑定时，才会建立路由，队列以及两者的绑定，同@RabbitListener(bindings = @QueueBinding()类似
     * @return
     */
    @Bean
    public Binding binding1()
    {
        return BindingBuilder.bind(queueTopicOne()).to(topicExchange()).with(BINDING_KEY1);
    }

    @Bean
    public Binding binding2()
    {
        return BindingBuilder.bind(queueTopicTwo()).to(topicExchange()).with(BINDING_KEY1);
    }


    /**
     * 将队列3,4   使用#号和交换器模糊匹配
     * @return
     */
    @Bean
    public Binding binding3()
    {
        return BindingBuilder.bind(queueTopicThree()).to(topicExchange()).with(BINDING_KEY2);
    }

    @Bean
    public Binding binding4()
    {
        return BindingBuilder.bind(queueTopicFour()).to(topicExchange()).with(BINDING_KEY2);
    }
}
