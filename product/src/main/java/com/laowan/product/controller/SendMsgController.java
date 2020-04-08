package com.laowan.product.controller;

import com.laowan.product.product.ReplyProducer;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: rabbitmq
 * @description: 发送消息测试类
 * @author: wanli
 * @create: 2019-07-02 17:11
 **/
//@RestController
public class SendMsgController {
    @Autowired
    ReplyProducer replyProducer;


    @GetMapping("/sendMsg")
    public String sendMsg(String msg) {
        int num = (int) (Math.random() * 10 + 1);
        msg = msg + num;
        Message message = replyProducer.sendAndReceive(msg + "，消息的尾号是：" + num);
        if (message != null) {
            return new String(message.getBody());
        } else {
            return "没有获得返回结果！";
        }
    }


}
