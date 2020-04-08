package com.laowan.product;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @program: rabbitmq
 * @description: 生产者测试
 * @author: wanli
 * @create: 2020-04-02 14:10
 **/
public class ProductTest {
    private static final String EXCHANGE_NAME = "history";


    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");
        factory.setHost("wanli");
        factory.setPort(5672);

        Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.confirmSelect();

        AMQP.BasicProperties.Builder bldr = new AMQP.BasicProperties.Builder();
        for (int i = 0; i < 10000; i++) {
            //第一个参数是交换器名称，第二个参数是routeing key ，注意这里的routeing key一定要是随机的，不然消息都会发送到同一个队列中
            channel.basicPublish(EXCHANGE_NAME, String.valueOf(i), bldr.build(), "hello".getBytes("UTF-8"));
        }
        channel.waitForConfirmsOrDie(10000);

        TimeUnit.SECONDS.sleep(5);

        channel.close();
        connection.close();
    }
}
