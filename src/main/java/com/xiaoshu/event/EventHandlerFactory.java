package com.xiaoshu.event;

import com.xiaoshu.im.MessageInfo;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 功能说明： 工厂方法设计模式实现；
 *
 * @ com.xiaoshu.event
 * <p>
 * Original @Author: deane.jia-贾亮亮,@2021/2/19@16:52
 * <p>
 * Copyright (C)2012-@2021 小树盛凯科技 All rights reserved.
 */
@Component
@Configuration
public class EventHandlerFactory implements ApplicationContextAware {

    public Map<MessageInfo.Message.Type, EventHandler> eventHandlerList = new ConcurrentHashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
        beans.forEach((key, value) -> {
            // 策略模式，将所有的策略类的Handler存入到Map中；
            eventHandlerList.put(value.getMessageType(), value);
        });
    }

    /**
     * @param type
     * @return
     */
    public EventHandler getEventHandler(MessageInfo.Message.Type type) {
        return eventHandlerList.get(type);
    }
}
