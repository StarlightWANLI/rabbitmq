package com.laowan.product.product;

import com.laowan.product.enums.ExchangeEnum;
import com.laowan.product.enums.QueueEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
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
public class ReplyProducer implements RabbitTemplate.ConfirmCallback{



    //由于rabbitTemplate的scope属性设置为ConfigurableBeanFactory.SCOPE_PROTOTYPE，所以不能自动注入
    private RabbitTemplate rabbitTemplate;

    /**
     * 构造方法注入rabbitTemplate
     */
    @Autowired
    public ReplyProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;

       // rabbitTemplate.setTaskExecutor();
        rabbitTemplate.setConfirmCallback(this); //rabbitTemplate如果为单例的话，那回调就是最后设置的内容
    }


    /**
     * 消息被成功消费的确认回调方法        消息成功发送到broker里面，收到反馈
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
/*        log.info(" 回调id:" + correlationData);
        if (ack) {
            log.info("消息发送成功");
        } else {
            log.info("消息发送失败:" + cause);
        }*/
    }

    /**
     * 发送字符串  到direct队列中    完全匹配
     * @param content
     */
    public Message sendAndReceive(String content) {
        //设置消息唯一id
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        //直接发送message对象
        MessageProperties  messageProperties = new MessageProperties();
        //过期时间10秒
        messageProperties.setExpiration("5000");
       // messageProperties.set
        content = Thread.currentThread()+"发送的消息是" + content;
        Message message = new Message(content.getBytes(),messageProperties);


        System.out.println("发送的消息是：" + content );
        StopWatch sw = new StopWatch();
        sw.start("发送消息任务");

        Message  message1 =  rabbitTemplate.sendAndReceive(ExchangeEnum.REPLY_EXCHANGE.getValue(), QueueEnum.TEST_REPLY.getRoutingKey(),message,correlationId);

        sw.stop();
        System.out.println(sw.prettyPrint());

        if (message1!=null) {
            System.out.println(Thread.currentThread() + "应答的消息是：" + new String(message1.getBody()));
        }

        return message1;


        //rabbitTemplate.send(ExchangeEnum.DIRECT_EXCHANGE.getValue(), QueueEnum.TEST_DIRECT.getRoutingKey(),message,correlationId);

        //发送的消息是Message对象就直接发送，不是的先转化为message对象
       // rabbitTemplate.convertAndSend(ExchangeEnum.DIRECT_EXCHANGE.getValue(), QueueEnum.TEST_DIRECT.getRoutingKey(), content, correlationId);

        // 发送消息到指定的交换器，指定的路由键，在消息转换完成后，通过 MessagePostProcessor 来添加属性
  /*      rabbitTemplate.convertAndSend("direct.exchange","key.1",user,mes -> {
            mes.getMessageProperties().setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
            return mes;
        });*/


    }


}
