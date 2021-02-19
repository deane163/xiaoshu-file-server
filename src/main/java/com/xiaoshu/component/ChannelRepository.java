package com.xiaoshu.component;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 功能说明： 此类为工具类，根据客户端Id，把数据通道放入到对应的 value，相当于做一个绑定关系；
 *
 * @ com.xiaoshu.component
 * <p>
 * Original @Author: deane.jia-贾亮亮,@2021/2/19@15:41
 * <p>
 * Copyright (C)2012-@2021 小树盛凯科技 All rights reserved.
 */
@Component(value = "channelRepository")
@Slf4j
public class ChannelRepository {

    private final static Map<String, Channel> channelCache = new ConcurrentHashMap<>();

    /**
     * @param key
     * @param value
     */
    public void put(String key, Channel value) {
        channelCache.put(key, value);
    }

    /**
     * @param key
     * @return
     */
    public Channel get(String key) {
        return channelCache.get(key);
    }

    /**
     * 踢出客户端的连接Channel
     *
     * @param key
     */
    public void remove(String key) {
        channelCache.remove(key);
    }

    /**
     * 在线客户端数量信息
     */
    public int size() {
        return channelCache.size();
    }


    /**
     * 判断Channel是否已经存在
     * @param channel
     * @return
     */
    public boolean isChannelExists(Channel channel) {
        AtomicBoolean flag = new AtomicBoolean(false);
        channelCache.forEach((key, value) -> {
            if (value.equals(channel)) {
                flag.set(true);
                return;
            }
        });
        return flag.get();
    }
}
