package com.xiaoshu.event.handler;

import cn.hutool.core.util.StrUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import com.xiaoshu.component.ChannelRepository;
import com.xiaoshu.event.EventHandler;
import com.xiaoshu.im.MessageInfo;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 功能说明： 处理Authorization消息类型；
 *
 * @ com.xiaoshu.event
 * <p>
 * Original @Author: deane.jia-贾亮亮,@2021/2/19@17:32
 * <p>
 * Copyright (C)2012-@2021 深圳优必选科技 All rights reserved.
 */
@Component
@Slf4j
public class AuthorizationEventHandler implements EventHandler {

    @Autowired
    private ChannelRepository channelRepository;

    @Override
    public MessageInfo.Message.Type getMessageType() {
        return MessageInfo.Message.Type.AUTH;
    }

    @Override
    public void handlerEvent(ChannelHandlerContext ctx, MessageInfo.Message msg) {
        // TODO 认证客户端的合法性，合法则接受连接，不合法，则关闭连接；
        MessageInfo.Authorization authorization = null;
        try {
            authorization = msg.getMessageContent().getContent().unpack(MessageInfo.Authorization.class);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        String clientId = authorization.getCId();
        if (StrUtil.isNotEmpty(clientId)) {
            log.info("[添加Channel]clientId:{} 添加客户端Channel:{}", clientId, ctx.channel().remoteAddress());
            channelRepository.put(clientId, ctx.channel());
        }
    }
}
