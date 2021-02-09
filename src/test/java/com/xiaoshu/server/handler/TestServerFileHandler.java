package com.xiaoshu.server.handler;

import com.xiaoshu.im.MessageInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;

/**
 * 功能说明：
 *
 * @ com.xiaoshu.server.handler
 * <p>
 * Original @Author: deane.jia-贾亮亮,@2021/2/9@14:03
 * <p>
 * Copyright (C)2012-@2021 小树盛凯科技 All rights reserved.
 */
public class TestServerFileHandler extends SimpleChannelInboundHandler<MessageInfo.Message> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ctx address:" + ctx.channel().remoteAddress());
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageInfo.Message msg) throws Exception {
        System.out.printf(" start deal with business on time :{}", System.currentTimeMillis());
        System.out.println("================================================================>");
        System.out.println("[receive message] gid" + msg.getGId());
        System.out.println("[receive message] type" + msg.getType().toString());
        System.out.println("================================================================>");
        ctx.writeAndFlush(msg);
        // 发送到下一个Handler进行处理；
        ctx.fireChannelRead(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        System.out.println("[远程客户端]关闭连接" + ctx.channel().remoteAddress());
    }
}
