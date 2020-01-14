package com.laowan.product.enums;

import lombok.Getter;

/**
 * @program: rabbitmq
 * @description: 交换器常量
 * @author: wanli
 * @create: 2019-06-13 17:36
 **/
@Getter
public enum ExchangeEnum {
    DIRECT_EXCHANGE("test.direct.exchange"),

    TOPIC_EXCHANGE("test.topic.exchange"),

    FANOUT_EXCHANGE("test.fanout.exchange"),

    REPLY_EXCHANGE("test.reply.exchange");


    private String value;

    ExchangeEnum(String value) {
        this.value = value;
    }

}
