package com.xiaoshu.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 功能说明：
 *
 * @ com.xiaoshu.server
 * <p>
 * Original @Author: deane.jia-贾亮亮,@2021/2/19@14:59
 * <p>
 * Copyright (C)2012-@2021 小树盛凯科技 All rights reserved.
 */
@Component
@Slf4j
public class TcpServer {

    @Autowired
    private ServerBootstrap serverBootstrap;

    /**
     * 启动NettyServer服务
     *
     * @param port
     * @throws InterruptedException
     */
    public void start(int port) throws InterruptedException {
        ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
        log.info("[Server start] TCP Server start , listen is :" + channelFuture.channel().remoteAddress());
        channelFuture.channel().closeFuture().sync();
    }
}
