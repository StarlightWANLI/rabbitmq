package com.laowan.product.config;

import org.springframework.amqp.core.AbstractExchange;
import org.springframework.amqp.core.DirectExchange;

import java.util.Map;

/**
 * @program: rabbitmq
 * @description: hash分发交换器
 * @author: wanli
 * @create: 2020-03-30 14:00
 **/
public class HashExchange extends AbstractExchange {
    public static final HashExchange DEFAULT = new HashExchange("");

    public HashExchange(String name) {
        super(name);
    }

    public HashExchange(String name, boolean durable, boolean autoDelete) {
        super(name, durable, autoDelete);
    }

    public HashExchange(String name, boolean durable, boolean autoDelete, Map<String, Object> arguments) {
        super(name, durable, autoDelete, arguments);
    }

    @Override
    public final String getType() {
        return "x-consistent-hash";
    }
}
