package com.laowan.product.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.listener.exception.ListenerExecutionFailedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.util.ErrorHandler;
import sun.plugin2.message.Message;

/**
 * @program: rabbitmq
 * @description: rabbitmq配置   一般情况，是不要自己去定义的，这里是由于要为每个RabbitTemplate模板类配置不同的回调确认机制
 * @author: wanli
 * @create: 2019-06-13 17:33
 **/
@Configuration
@Slf4j
public class RabbitmqConfig {
    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.virtual-host}")
    private String vhost;

    @Value("${spring.rabbitmq.template.reply-timeout}")
    Long replyTimeOut;
    @Value("${spring.rabbitmq.template.receive-timeout}")
    Long receiveTimeOut;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(this.host,this.port);

        connectionFactory.setUsername(this.username);
        connectionFactory.setPassword(this.password);
        connectionFactory.setVirtualHost(this.vhost);
        connectionFactory.setPublisherConfirms(true);

       // connectionFactory.setChannelCacheSize(100);

        return connectionFactory;
    }


    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setReceiveTimeout(receiveTimeOut);
        rabbitTemplate.setReplyTimeout(replyTimeOut);

        rabbitTemplate.setReplyErrorHandler(getErrorHandler() );
        return rabbitTemplate;
    }

    @Bean
    public ErrorHandler getErrorHandler() {
       // ErrorHandler errorHandler = new ConditionalRejectingErrorHandler();
        ErrorHandler errorHandler = new ErrorHandler() {
            @Override
            public void handleError(Throwable throwable) {
                try{
                    //比如出现异常的消息统一保存，留案
                    String  msg = new String(((ListenerExecutionFailedException) throwable).getFailedMessage().getBody());
                    log.error("消费失败,消息内容是：" + msg);
                }catch (Exception e){

                }


              //  log.error("异常原因：" +  throwable.getMessage());
            }
        };
        return  errorHandler;
    }
}
