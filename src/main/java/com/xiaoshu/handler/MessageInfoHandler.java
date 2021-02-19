package com.xiaoshu.handler;

import cn.hutool.core.io.FileUtil;
import com.xiaoshu.im.MessageInfo;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 功能说明： 具体的业务逻辑处理类实现；(实现具体的图片透传操作)
 *
 * @ com.xiaoshu.handler
 * <p>
 * Original @Author: deane.jia-贾亮亮,@2021/2/9@13:46
 * <p>
 * Copyright (C)2012-@2021 小树盛凯科技 All rights reserved.
 */
@Component("messageInfoHandler")
@ChannelHandler.Sharable
@Slf4j
public class MessageInfoHandler extends SimpleChannelInboundHandler<MessageInfo.Message> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    /**
     * 添加业务逻辑，实现图片的透传操作
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageInfo.Message msg) throws Exception {
        // TODO 添加实际的业务逻辑，实现图片的透传操作；
        log.info("[Handler Message] handler with message :{}", msg);
        if (msg.getType().equals(MessageInfo.Message.Type.FILE)) {
            MessageInfo.File file = msg.getMessageContent().getContent().unpack(MessageInfo.File.class);
            FileUtil.writeBytes(file.getData().toByteArray(), "D:/temp/image_" + System.currentTimeMillis() + ".jpg");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
