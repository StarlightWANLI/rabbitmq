package com.laowan.product;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

/**
 * @program: rabbitmq
 * @description:
 * @author: wanli
 * @create: 2020-03-30 15:02
 **/
public class HashTest {

    private static String CONSISTENT_HASH_EXCHANGE_TYPE = "x-consistent-hash";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");
        factory.setHost("wanli");
        factory.setPort(5672);
        Connection conn = factory.newConnection();
        Channel ch = conn.createChannel();

        for (String q : Arrays.asList("q1", "q2", "q3", "q4")) {
            ch.queueDeclare(q, true, false, false, null);
            ch.queuePurge(q);//清空队列
        }

        ch.exchangeDeclare("e1", CONSISTENT_HASH_EXCHANGE_TYPE, true, false, null);

        for (String q : Arrays.asList("q1", "q2")) {
            ch.queueBind(q, "e1", "1");
        }

        for (String q : Arrays.asList("q3", "q4")) {
            ch.queueBind(q, "e1", "1");
        }

        ch.confirmSelect();

        AMQP.BasicProperties.Builder bldr = new AMQP.BasicProperties.Builder();
        for (int i = 0; i < 100000; i++) {
            ch.basicPublish("e1", String.valueOf(i), bldr.build(), "".getBytes("UTF-8"));
        }

        ch.waitForConfirmsOrDie(10000);

        System.out.println("Done publishing!");
        System.out.println("Evaluating results...");
        // wait for one stats emission interval so that queue counters
        // are up-to-date in the management UI
        Thread.sleep(5);

        System.out.println("Done.");
        conn.close();
    }
}
