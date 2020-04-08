package com.laowan.product.product;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
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
//@Component
@Slf4j
public class HashProducer{

    @Autowired
    ConnectionFactory connectionFactory;

    //由于rabbitTemplate的scope属性设置为ConfigurableBeanFactory.SCOPE_PROTOTYPE，所以不能自动注入
    @Autowired
    private RabbitTemplate rabbitTemplate;




    /**
     * 发送字符串  到direct队列中    完全匹配
     * @param content
     */
    public void sendMsg(String content) {
        //设置消息唯一id
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        //直接发送message对象
        MessageProperties messageProperties = new MessageProperties();
        //过期时间10秒
       // messageProperties.setExpiration("10000");
        Message message = new Message(content.getBytes(), messageProperties);
        rabbitTemplate.send("history",correlationId.getId(),message);

     //   for (int i = 0; i < 100000; i++) {
           // ch.basicPublish("e1", String.valueOf(i), bldr.build(), "".getBytes("UTF-8"));
            //只有route key变化才会路由到不同的队列中
            //rabbitTemplate.send("history",correlationId.getId(),message);
    //    }


   /*     Connection conn = connectionFactory.createConnection();
        Channel ch = conn.createChannel(true);*/

       // ch.basicPublish("e1", "123", bldr.build(), "".getBytes("UTF-8"));

        //rabbitTemplate.send(ExchangeEnum.DIRECT_EXCHANGE.getValue(), QueueEnum.TEST_DIRECT.getRoutingKey(), message, correlationId);

        //发送的消息是Message对象就直接发送，不是的先转化为message对象
        // rabbitTemplate.convertAndSend(ExchangeEnum.DIRECT_EXCHANGE.getValue(), QueueEnum.TEST_DIRECT.getRoutingKey(), content, correlationId);

        // 发送消息到指定的交换器，指定的路由键，在消息转换完成后，通过 MessagePostProcessor 来添加属性
  /*      rabbitTemplate.convertAndSend("direct.exchange","key.1",user,mes -> {
            mes.getMessageProperties().setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
            return mes;
        });*/


    }


}
