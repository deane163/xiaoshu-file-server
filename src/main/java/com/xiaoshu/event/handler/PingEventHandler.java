package com.xiaoshu.event.handler;

import com.xiaoshu.component.ChannelRepository;
import com.xiaoshu.event.EventHandler;
import com.xiaoshu.im.MessageInfo;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 功能说明： 处理PING消息类型；
 *
 * @ com.xiaoshu.event.handler
 * <p>
 * Original @Author: deane.jia-贾亮亮,@2021/2/19@17:33
 * <p>
 * Copyright (C)2012-@2021 小树盛凯科技 All rights reserved.
 */
@Component
public class PingEventHandler implements EventHandler {

    @Autowired
    private ChannelRepository channelRepository;

    @Override
    public MessageInfo.Message.Type getMessageType() {
        return MessageInfo.Message.Type.PING;
    }

    @Override
    public void handlerEvent(ChannelHandlerContext ctx, MessageInfo.Message msg) {
        // 针对Ping消息处理
        // TODO 处理PING信息，返回PONG消息到客户端; 首先判断客户端是否已经连接的合法用户；
        if (channelRepository.isChannelExists(ctx.channel())) {
            ctx.channel().writeAndFlush(createPongMessage());
        } else {
            // 非法用户，需要关闭Channel
            ctx.channel().close();
        }
    }

    /**
     * 构建PONG命令
     *
     * @return
     */
    private MessageInfo.Message createPongMessage() {
        MessageInfo.Message.Builder builder = MessageInfo.Message.newBuilder();
        builder.setType(MessageInfo.Message.Type.PONG);
        return builder.build();
    }
}
