package com.example.demosfw.ActiveMQ;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

/**
 * Created by 追梦1819 on 2019-06-26.
 */
@Service
public class Consumer {
    @JmsListener(destination = "${myqueue}")
    public void receive(String text) {
        System.out.println("consumer接收到的报文为:" + text);
    }
}