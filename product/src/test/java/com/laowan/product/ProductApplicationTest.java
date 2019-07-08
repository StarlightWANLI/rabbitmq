package com.laowan.product;

import com.laowan.product.product.DirectProducer;
import com.laowan.product.product.FanoutProducer;
import com.laowan.product.product.ReplyProducer;
import com.laowan.product.product.TopicProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @program: rabbitmq
 * @description: 测试类
 * @author: wanli
 * @create: 2019-06-13 16:43
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductApplicationTest {

      @Autowired
      DirectProducer directProducer;

    @Autowired
    TopicProducer topicProducer;

    @Autowired
    FanoutProducer fanoutProducer;

    @Autowired
    ReplyProducer replyProducer;

      @Test
      public void sendMsgDirectTest(){
          for (int i = 0;i < 100;i++){
              directProducer.sendMsg("测试direct消息"+i);
          }
      }


    /**
     * 消息发送到4个队列中  *  和  #号都能匹配上  one这个单词
     */
    @Test
    public void sendMsgTopicTest(){
        for (int i = 0;i < 100;i++){
            topicProducer.sendMsg("test.topic.one","测试topic消息"+i);
        }
    }


    /**
     * 消息发送到2个队列中，只有#号能匹配到one.many.team多个单词
     */
    @Test
    public void sendMsgTopic2Test(){
        for (int i = 0;i < 10;i++){
            topicProducer.sendMsg("test.topic.one.many.team","测试topic消息的#号匹配"+i);
        }
    }


    /**
     * 测试广播路由匹配规则，消息发送到交换器下的所有队列中
     */
    @Test
    public void sendMsgFanoutTest(){
        for (int i = 0;i < 10;i++){
            fanoutProducer.sendMsg("测试fanout消息"+i);
        }
    }



    /**
     * 测试reply应答模式   rpc
     */
    @Test
    public void sendOneReplyTest(){
        Message message = replyProducer.sendAndReceive("测试reply消息");
        if(message!=null){
            System.out.println(message.getBody().toString());
        }else{
            System.out.println("没有获得返回信息");
        }
    }

    /**
     * 测试reply应答模式   rpc    发送多条
     */
    @Test
    public void sendManyReplyTest(){
        for (int i = 0;i < 30;i++){
            Message message = replyProducer.sendAndReceive("测试reply消息"+i);
            if(message!=null){
                System.out.println(message.getBody().toString());
            }else{
                System.out.println("没有获得返回信息");
            }
        }

    }


    /**
     * 测试reply应答模式   rpc    多线程发送
     */
    @Test
    public void sendManyThreadReplyTest(){
        ExecutorService threadPool = Executors.newFixedThreadPool(100);
        for (int i = 0;i < 100;i++){
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    Message message = replyProducer.sendAndReceive("测试reply消息");
                    if(message!=null){
                        System.out.println(message.getBody().toString());
                    }else{
                        System.out.println("没有获得返回信息");
                    }
                }
            });
        }
    }
}
