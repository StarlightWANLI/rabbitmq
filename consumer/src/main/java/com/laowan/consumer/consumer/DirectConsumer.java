package com.laowan.consumer.consumer;

import com.laowan.consumer.model.Order;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @program: rabbitmq
 * @description: 用户注册消费服务
 * @author: wanli
 * @create: 2019-06-13 18:01
 **/
@Component
@RabbitListener(queues = "test.direct.queue")
public class DirectConsumer {


    /**
     * 消费者接收消息并消费消息    使用@RabbitListener(bindings = @QueueBinding(）声明，就不用担心消费者先启动时，生产者没启动导致路由，队列不存在的问题了
     *
     * @param msg
     * @param headers
     * @param channel
     * @throws Exception
     */
/*    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "user.register.queue", durable = "true"),
            exchange = @Exchange(value = "user.register.topic.exchange", durable = "true", type = "direct"),
            key = "user.register.#"
    ))*/

    // @RabbitHandler
    public void onMessage(@Payload String msg,
                          @Headers Map<String, Object> headers,
                          Channel channel) throws Exception {
        System.out.println("--------------收到消息，开始消费------------");
        System.out.println("收到的消息是：" + msg);
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);

        Thread.sleep(1000);
        //抛出异常，出发重试机制
        //  throw  new RuntimeException("消息处理失败");
        // ACK
        // channel.basicAck(deliveryTag, false);
    }

    @RabbitHandler
    public void onMessage(String message) {
        System.out.println("获取到字符串：" + message);
    }

/*
    @RabbitHandler
    public void onMessage(byte[] message,
                          @Headers Map<String, Object> headers,
                          Channel channel) {
        System.out.println("获取到的是二进制消息：" + new String(message));
    }
*/


}
