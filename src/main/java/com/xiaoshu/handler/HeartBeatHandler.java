package com.xiaoshu.handler;

import com.xiaoshu.im.MessageInfo;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
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
        // 将数据转发到下一个Handler进行处理
        ctx.fireChannelRead(msg);
    }
}
