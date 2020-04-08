package com.laowan.consumer.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
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
//@Async
@Component
@RabbitListener(queues = "test.reply.queue")
@Slf4j
public class ReplyConsumer {


    @RabbitHandler
    public String onMessage(byte[] message,
                            @Headers Map<String, Object> headers,
                            Channel channel) {
        StopWatch stopWatch = new StopWatch("调用计时");
        stopWatch.start("rpc调用消费者耗时");
        String msg = new String(message);
        log.info("接收到的消息为：" + msg);
         //将结果返回到客户端Queue
        String responseMessage = sayHello(msg) ;
        try {
            Thread.sleep(3 );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("返回的消息为：" + responseMessage);
        stopWatch.stop();
        log.info(stopWatch.getLastTaskName()+stopWatch.getTotalTimeMillis()+"ms");
        return responseMessage;
    }

    public  String sayHello(String name){
        return "hello " + name ;
    }

}
