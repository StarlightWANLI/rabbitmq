package com.laowan.consumer.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.util.Map;
import java.util.Random;

/**
 * @program: rabbitmq
 * @description: 应答这
 * @author: wanli
 * @create: 2019-06-13 18:01
 **/
@Async
@Component
@RabbitListener(queues = "test.reply.queue")
public class ReplyConsumer {

    /**
     * 交换机、队列不存在的话，以下注解可以自动创建交换机和队列
     *
     * @param order
     * @param headers
     * @param channel
     * @throws Exception
     */

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
        // return
    }


    @RabbitHandler
    public void onMessage(String message) {
        System.out.println("获取到字符串：" + message);
    }

    @RabbitHandler
    public String onMessage(byte[] message,
                            @Headers Map<String, Object> headers,
                            Channel channel) {
        String msg = new String(message);
        System.out.println("获取到的是二进制消息：" + msg);
        StopWatch sw = new StopWatch();
        sw.start("消费消息计时器");
/*        try {
            //手动应答    放在方法前后没有区别，还是会等待ack
            channel.basicAck(0L, true);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        try {
            System.out.println("出现卡顿，等待ack");
            Thread.sleep(3 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sw.stop();
        System.out.println(sw.prettyPrint());
        return "成功消费的消息是：" + new String(message);
    }

}
