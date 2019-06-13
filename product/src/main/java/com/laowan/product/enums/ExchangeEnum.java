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
    /**
     * 用户注册交换配置枚举
     */
    USER_REGISTER("user.register.topic.exchange"),


    DIRECT_EXCHANGE("test.direct.exchange"),


    FANOUT_EXCHANGE("test.fanout.exchange");

    private String value;

    ExchangeEnum(String value) {
        this.value = value;
    }

}
