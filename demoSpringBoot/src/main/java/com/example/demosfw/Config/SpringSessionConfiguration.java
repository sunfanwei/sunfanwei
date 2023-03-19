package com.example.demosfw.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.session.events.SessionCreatedEvent;
import org.springframework.session.events.SessionDeletedEvent;
import org.springframework.session.events.SessionExpiredEvent;


/**
 * session整合配置类
 *
 */
@Configuration
public class SpringSessionConfiguration {
    private final Logger logger = LoggerFactory.getLogger(SpringSessionConfiguration.class);
    public static int online = 0;

    /**
     * Redis内session过期事件监听
     */
    @EventListener
    public void onSessionExpired(SessionExpiredEvent expiredEvent) {
        online --;
        String sessionId = expiredEvent.getSessionId();
        logger.info(expiredEvent.getSessionId()+"失效了");
    }


    /**
     * Redis内session删除事件监听
     */
    @EventListener
    public void onSessionDeleted(SessionDeletedEvent deletedEvent) {
        online --;
        String sessionId = deletedEvent.getSessionId();
        logger.info(deletedEvent.getSessionId()+"删除了");

    }

    /**
     * Redis内session保存事件监听
     */
    @EventListener
    public void onSessionCreated(SessionCreatedEvent createdEvent) {
        online ++;
        logger.info(createdEvent.getSessionId()+"创建了");
        String sessionId = createdEvent.getSessionId();
    }
}