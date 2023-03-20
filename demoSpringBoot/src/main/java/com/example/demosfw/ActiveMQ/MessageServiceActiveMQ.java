package com.example.demosfw.ActiveMQ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

@Component
public class MessageServiceActiveMQ {
    @Autowired
    private JmsMessagingTemplate messagingTemplate;

    @Autowired
    private Queue queue;

    public void sendMessage(String id) {
        System.out.println("待发送短信的订单已纳入处理队列，id："+id);
        messagingTemplate.convertAndSend(queue,id);
    }

    public String doMessage() {
        String id = messagingTemplate.receiveAndConvert(queue,String.class);
        System.out.println("已完成短信发送业务，id："+id);
        return id;
    }
}
