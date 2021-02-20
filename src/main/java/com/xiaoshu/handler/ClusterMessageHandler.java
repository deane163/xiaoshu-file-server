package com.xiaoshu.handler;

import com.xiaoshu.im.MessageInfo;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 功能说明：具体的业务逻辑处理类实现；(主要是集群功能，如果接收端没有连接到本节点，需要将图片文件转发到其它节点上)
 *
 * @ com.xiaoshu.handler
 * <p>
 * Original @Author: deane.jia-贾亮亮,@2021/2/20@9:59
 * <p>
 * Copyright (C)2012-@2021 小树盛凯科技 All rights reserved.
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class ClusterMessageHandler extends SimpleChannelInboundHandler<MessageInfo.Message> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageInfo.Message msg) throws Exception {
        // TODO 客户端在线，但是客户端没有连接到当前节点时，将数据转发到其它节点上；
        log.info("[数据转发操作]将数据转发到另外一个节点上");
    }
}
