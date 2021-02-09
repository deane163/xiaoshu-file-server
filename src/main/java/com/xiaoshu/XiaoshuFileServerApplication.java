package com.xiaoshu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 功能说明： 实现一个基于Netty框架的图传服务；
 * （1） 将图片传递给目标客户端；(实现一个图片透传功能)
 * （2） 保持心跳信息；
 * （3） 使用protobuf消息编解码器；
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

    public static void main(String[] args) {
        SpringApplication.run(XiaoshuFileServerApplication.class, args);
    }

}
