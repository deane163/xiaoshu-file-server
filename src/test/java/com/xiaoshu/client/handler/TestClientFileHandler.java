package com.xiaoshu.client.handler;

import com.xiaoshu.im.MessageInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 功能说明：
 *
 * @ com.xiaoshu.client.handler
 * <p>
 * Original @Author: deane.jia-贾亮亮,@2021/2/9@14:18
 * <p>
 * Copyright (C)2012-@2021 小树盛凯科技 All rights reserved.
 */
public class TestClientFileHandler extends SimpleChannelInboundHandler<MessageInfo.Message> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageInfo.Message msg) throws Exception {
        System.out.println("================================================================>");
        System.out.println("[客户端]接收到数据信息:" + msg.getGId());
        System.out.println("[客户端]接收到数据信息:" + msg.getType().toString());
        ctx.fireChannelRead(msg);
    }
}
