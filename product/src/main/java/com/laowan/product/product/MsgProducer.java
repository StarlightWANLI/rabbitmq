package com.laowan.product.product;

import com.laowan.product.config.RabbitConfig;
import com.laowan.product.enums.ExchangeEnum;
import com.laowan.product.enums.QueueEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @program: rabbitmq
 * @description: 生产者
 * @author: wanli
 * @create: 2019-06-13 16:47
 **/
@Component
@Slf4j
public class MsgProducer implements RabbitTemplate.ConfirmCallback{

    //由于rabbitTemplate的scope属性设置为ConfigurableBeanFactory.SCOPE_PROTOTYPE，所以不能自动注入
    @Autowired
    private RabbitTemplate rabbitTemplate;



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
     * 构造方法注入rabbitTemplate
     */
/*    @Autowired
    public MsgProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setConfirmCallback(this); //rabbitTemplate如果为单例的话，那回调就是最后设置的内容
    }*/




    public void sendMsg(String content) {
        //设置消息唯一id
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        //把消息放入ROUTINGKEY_A对应的队列当中去，对应的是队列A
        rabbitTemplate.convertAndSend(ExchangeEnum.USER_REGISTER.getValue(), QueueEnum.USER_REGISTER.getRoutingKey(), content, correlationId);
    }

}