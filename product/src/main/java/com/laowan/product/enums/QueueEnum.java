package com.laowan.product.enums;

import lombok.Getter;

/**
 * @program: rabbitmq
 * @description: 队列枚举
 * @author: wanli
 * @create: 2019-06-13 17:37
 **/
@Getter
public enum QueueEnum {
/*    *//**
     * 用户注册枚举   direct模式测试
     *//*
    USER_REGISTER("user.register.queue","user.register"),*/

    /**
     * 应答模式
     */
    TEST_REPLY("test.reply.queue","test.reply"),

    /**
     * direct模式测试
     */
    TEST_DIRECT("test.direct.queue","test.direct"),

    /**
     * topic模式
     */
    TEST_TOPIC_ONE("test.topic.queue1","test.topic.one"),

    /**
     * topic模式
     */
    TEST_TOPIC_TWO("test.topic.queue2","test.topic.two"),

    /**
     * topic模式
     */
    TEST_TOPIC_THREE("test.topic.queue3","test.topic.three"),

    /**
     * topic模式
     */
    TEST_TOPIC_FOUR("test.topic.queue4","test.topic.four.many.match"),



    /**
     *fanout模式
     */
    TEST_FANOUT_ONE("test.fanout.one.queue",""),

    /**
     *fanout模式
     */
    TEST_FANOUT_TWO("test.fanout.two.queue",""),

    /**
     *fanout模式
     */
    TEST_FANOUT_THREE("test.fanout.three.queue","")




    ;
    /**
     * 队列名称
     */
    private String name;
    /**
     * 队列路由键
     */
    private String routingKey;

    QueueEnum(String name, String routingKey) {
        this.name = name;
        this.routingKey = routingKey;
    }
}
