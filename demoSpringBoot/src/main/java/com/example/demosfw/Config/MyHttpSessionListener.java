package com.example.demosfw.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class MyHttpSessionListener implements HttpSessionListener {

    public static int online = 0;

    private final Logger logger = LoggerFactory.getLogger(MyHttpSessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        logger.info(se.getSession().getId()+"创建了");
        online ++;
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("销毁session");

    }



}