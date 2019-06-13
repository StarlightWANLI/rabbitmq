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
    /**
     * 用户注册枚举
     */
    USER_REGISTER("user.register.queue","user.register")
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
