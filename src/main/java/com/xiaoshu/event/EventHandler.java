package com.xiaoshu.event;

import com.xiaoshu.im.MessageInfo;
import io.netty.channel.ChannelHandlerContext;

/**
 * 功能说明：
 *
 * @ com.xiaoshu.event
 * <p>
 * Original @Author: deane.jia-贾亮亮,@2021/2/19@16:50
 * <p>
 * Copyright (C)2012-@2021 深圳优必选科技 All rights reserved.
 */
public interface EventHandler {
    /**
     * 返回处理的消息类型；
     *
     * @return
     */
    MessageInfo.Message.Type getMessageType();

    /**
     * 处理具体的 msg
     *
     * @param ctx
     * @param msg
     */
    void handlerEvent(ChannelHandlerContext ctx, MessageInfo.Message msg);
}
