package com.laowan.product;

import com.laowan.product.product.RPCClient;
import com.laowan.product.product.ReplyProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeoutException;

@SpringBootApplication
@Slf4j
@RestController
@EnableRabbit
public class ProductApplication {
    @Autowired
    ReplyProducer replyProducer;

    @Autowired
    RPCClient rpcClient;

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
        log.info("【【【【【消息队列-消息提供者启动成功.】】】】】");
    }

    @GetMapping("/reply")
    public String reply(String message) throws Exception {
        Message message1 = replyProducer.sendAndReceive(message);
        if(message1!=null){
            return new String(message1.getBody());
        }else{
            throw new Exception("请求失败");
        }
    }

    @GetMapping("/call")
    public String call(String message) throws Exception {
        return rpcClient.call(message);
    }
}
