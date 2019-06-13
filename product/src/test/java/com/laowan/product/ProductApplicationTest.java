package com.laowan.product;

import com.laowan.product.product.MsgProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
      MsgProducer   msgProducer;


      @Test
      public void sendMsgTest(){
          for (int i = 0;i < 10;i++){
              msgProducer.sendMsg("测试消息"+i);
          }
      }


}
