package com.xiaoshu;

import com.xiaoshu.server.TcpServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 功能说明： 实现一个基于Netty框架的图传服务；
 * （1） 将图片传递给目标客户端；(实现一个图片透传功能)
 * （2） 保持心跳信息；
 * （3） 使用protobuf消息编解码器；
 * <p>
 * 参考： https://gitee.com/zjz0812/heart-netty
 *
 * @ com.xiaoshu
 * <p>
 * Original @Author: deane.jia-贾亮亮,@2021/2/9@11:08
 * <p>
 * Copyright (C)2012-@2021 小树盛凯科技 All rights reserved.
 */
@SpringBootApplication
@Slf4j
public class XiaoshuFileServerApplication {

    /**
     * 启动Spring服务，并启动Netty Server服务，接收客户端的文件发送
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(XiaoshuFileServerApplication.class, args);
        TcpServer tcpServer = context.getBean(TcpServer.class);
        tcpServer.start(28888);
    }

}
