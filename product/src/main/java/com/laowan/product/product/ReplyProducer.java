package com.laowan.product.product;

import com.laowan.product.config.TestReplyQueueConfig;
import com.laowan.product.enums.ExchangeEnum;
import com.laowan.product.enums.QueueEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;
import org.springframework.util.StopWatch;

import java.util.UUID;
import java.util.concurrent.Executor;

/**
 * @program: rabbitmq
 * @description: reply模式   RPC应答模式
 * @author: wanli
 * @create: 2019-06-13 16:47
 **/
@Component
@Slf4j
public class ReplyProducer  {


    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Autowired
    Queue replyResponseQueue;


    public Message sendAndReceive(String content) {
        //设置消息唯一id
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        //直接发送message对象
        MessageProperties messageProperties = new MessageProperties();
        //过期时间10秒
        messageProperties.setExpiration("10000");

         messageProperties.setReplyTo(replyResponseQueue.getName());
        // messageProperties.set
      //  content = Thread.currentThread() + "发送的消息是" + content;
        log.info("发送的消息为：" + content);

        messageProperties.setConsumerQueue(replyResponseQueue.getName());

        messageProperties.setCorrelationId(correlationId.getId());

        Message message = new Message(content.getBytes(), messageProperties);

        rabbitTemplate.setUseTemporaryReplyQueues(false);
        rabbitTemplate.setReplyAddress(replyResponseQueue.getName());
        rabbitTemplate.expectedQueueNames();
        rabbitTemplate.setUserCorrelationId(true);


        //消息体中指定了replyTo属性就不能使用sendAndReceive
        //Send-and-receive methods can only be used if the Message does not already have a replyTo property
        Message response = rabbitTemplate.sendAndReceive(ExchangeEnum.REPLY_EXCHANGE.getValue(), QueueEnum.TEST_REPLY.getRoutingKey(), message, correlationId);

        if (response != null) {
            log.info("返回的消息为：" + new String(response.getBody()));
        }
        return response;

    }
}
