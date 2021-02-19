package com.xiaoshu.handler;

import cn.hutool.core.util.StrUtil;
import com.xiaoshu.component.ChannelRepository;
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
        if (msg.getType().equals(MessageInfo.Message.Type.AUTH)) {
            MessageInfo.Authorization authorization = msg.getMessageContent().getContent().unpack(MessageInfo.Authorization.class);
            String clientId = authorization.getCId();
            if (StrUtil.isNotEmpty(clientId)) {
                log.info("[添加Channel]clientId:{} 添加客户端Channel:{}", clientId, ctx.channel().remoteAddress());
                channelRepository.put(clientId, ctx.channel());
            }
        } else {
            ctx.fireChannelRead(msg);
        }
        ReferenceCountUtil.release(msg);
    }
}
