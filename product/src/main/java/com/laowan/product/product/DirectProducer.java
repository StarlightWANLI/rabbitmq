package com.laowan.product.product;

import com.laowan.product.enums.ExchangeEnum;
import com.laowan.product.enums.QueueEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @program: rabbitmq
 * @description: direct模式   完全匹配的生产者
 * @author: wanli
 * @create: 2019-06-13 16:47
 **/
@Component
@Slf4j
public class DirectProducer implements RabbitTemplate.ConfirmCallback{

    //由于rabbitTemplate的scope属性设置为ConfigurableBeanFactory.SCOPE_PROTOTYPE，所以不能自动注入
    private RabbitTemplate rabbitTemplate;

    /**
     * 构造方法注入rabbitTemplate
     */
    @Autowired
    public DirectProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
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
        log.info(" 回调id:" + correlationData);
        if (ack) {
            log.info("消息成功消费");
        } else {
            log.info("消息消费失败:" + cause);
        }
    }

    /**
     * 发送字符串  到direct队列中    完全匹配
     * @param content
     */
    public void sendMsg(String content) {
        //设置消息唯一id
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        //直接发送message对象
        MessageProperties  messageProperties = new MessageProperties();
        //过期时间10秒
        messageProperties.setExpiration("10000");
        Message message = new Message(content.getBytes(),messageProperties);

        rabbitTemplate.send(ExchangeEnum.DIRECT_EXCHANGE.getValue(), QueueEnum.TEST_DIRECT.getRoutingKey(),message,correlationId);

        //发送的消息是Message对象就直接发送，不是的先转化为message对象
       // rabbitTemplate.convertAndSend(ExchangeEnum.DIRECT_EXCHANGE.getValue(), QueueEnum.TEST_DIRECT.getRoutingKey(), content, correlationId);
    }


}
