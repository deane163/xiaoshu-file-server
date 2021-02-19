package com.xiaoshu.config;

import com.xiaoshu.initializer.ImChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 功能说明：封装启动类  ServerBootstrap
 *
 * @ com.xiaoshu.config
 * <p>
 * Original @Author: deane.jia-贾亮亮,@2021/2/19@14:50
 * <p>
 * Copyright (C)2012-@2021 小树盛凯科技 All rights reserved.
 */

@Component
@Slf4j
public class ServerInitializer {

    @Autowired
    public ImChannelInitializer immChannelInitializer;

    /**
     * @return
     */
    @Bean(value = "bossGroup", destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup bossGroup() {
        return new NioEventLoopGroup();
    }

    /**
     * @return
     */
    @Bean(value = "workerGroup", destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup workerGroup() {
        return new NioEventLoopGroup();
    }

    /**
     * 构建服务器端 ServerBootstrap
     *
     * @return
     */
    @Bean
    public ServerBootstrap serverBootstrap(@Autowired @Qualifier(value = "bossGroup") NioEventLoopGroup bossGroup,
                                           @Autowired @Qualifier(value = "workerGroup") NioEventLoopGroup workerGroup) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class);
        serverBootstrap.handler(new LoggingHandler(LogLevel.INFO))
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.SO_BACKLOG, 100);
        serverBootstrap.childHandler(immChannelInitializer);
        return serverBootstrap;
    }

}

