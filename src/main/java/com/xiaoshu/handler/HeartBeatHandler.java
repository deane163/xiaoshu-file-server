package com.xiaoshu.handler;

import cn.hutool.core.util.ObjectUtil;
import com.xiaoshu.component.ChannelRepository;
import com.xiaoshu.event.EventHandler;
import com.xiaoshu.event.EventHandlerFactory;
import com.xiaoshu.im.MessageInfo;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 功能说明： 负责客户端的连接状态处理
 * （单位时间内，检查客户端是否有心跳信息，如果超过单位时间，没有心跳信息，则删除用户Channel）
 *
 * @ com.xiaoshu.handler
 * <p>A
 * Original @Author: deane.jia-贾亮亮,@2021/2/19@14:38
 * <p>
 * Copyright (C)2012-@2021 小树盛凯科技 All rights reserved.
 */
@ChannelHandler.Sharable
@Component
@Slf4j
public class HeartBeatHandler extends SimpleChannelInboundHandler<MessageInfo.Message> {

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private EventHandlerFactory eventHandlerFactory;

    /**
     * 空闲触发器 心跳基于空闲实现
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // TODO 判断连接客户端的在线状态信息； 如果保存连接状态，则把连接保存到 ChannelCache中；
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageInfo.Message msg) throws Exception {
        EventHandler eventHandler = eventHandlerFactory.getEventHandler(msg.getType());
        if (ObjectUtil.isNotEmpty(eventHandler)) {
            eventHandler.handlerEvent(ctx, msg);
        } else {
            // 如果没有处理该消息类型的Handler，则直接将消息发送给下一个Handler进行处理；
            ctx.fireChannelRead(msg);
        }
        ReferenceCountUtil.release(msg);
    }
}
