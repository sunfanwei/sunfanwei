package com.example.demosfw.ActiveMQ;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

@Component
@EnableJms  //开启jms适配的注解
public class ConfigBean {

    @Value("${myqueue}")
    private String myQueue;

    //按照刚刚配置的东西创建一个队列
    @Bean
    public Queue queue(){
        return new ActiveMQQueue(myQueue);
    }

}
