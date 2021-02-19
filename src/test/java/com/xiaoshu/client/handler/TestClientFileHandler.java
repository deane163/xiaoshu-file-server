package com.xiaoshu.client.handler;

import cn.hutool.core.io.FileUtil;
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
        if (msg.getType().equals(MessageInfo.Message.Type.FILE)) {
            MessageInfo.File file = msg.getMessageContent().getContent().unpack(MessageInfo.File.class);
            String destFileName = "D:/temp/image_" + System.currentTimeMillis() + ".jpg";
            FileUtil.writeBytes(file.getData().toByteArray(), destFileName);
            System.out.println("将图片保存到本地磁盘中");
        }
    }
}
