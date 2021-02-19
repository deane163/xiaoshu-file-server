package com.xiaoshu.client;

import cn.hutool.core.io.FileUtil;
import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import com.xiaoshu.client.handler.TestImClientChannelInitializer;
import com.xiaoshu.im.MessageInfo;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

/**
 * 功能说明：
 *
 * @ com.xiaoshu.client
 * <p>
 * Original @Author: deane.jia-贾亮亮,@2021/2/9@13:50
 * <p>
 * Copyright (C)2012-@2021 小树盛凯科技 All rights reserved.
 */
public class TcpClient {

    @Getter
    private static Channel clientChannel;

    public static void start(String address, int port) throws InterruptedException {
        doConnect(address, port);
    }

    /***
     * 进行连接操作， 并发送测试图片使用；
     * @param address
     * @param port
     */
    private static void doConnect(String address, int port) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup).channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new TestImClientChannelInitializer());
        // 创建连接
        try{
            ChannelFuture channelFuture = bootstrap.connect(address, port);
            clientChannel = channelFuture.channel();
            // 测试发送消息
            clientChannel.writeAndFlush(createMessage());
            channelFuture.addListener((ChannelFuture futureListener)->{
                EventLoop eventLoop = futureListener.channel().eventLoop();
                if(!futureListener.isSuccess()){
                    System.out.println("Fail to connect to Server, reconnect after 5 seconds!");
                    eventLoop.schedule(()-> doConnect(address, port), 5, TimeUnit.SECONDS );
                }
            });
            channelFuture.sync();
            clientChannel.closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    /**
     * 创建客户端连接，并发送消息到服务器端；
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        TcpClient.start("127.0.0.1", 28888);
    }

    /**
     * 创建protobuf类型Message
     * @return
     */
    private static MessageInfo.Message createMessage(){
        MessageInfo.Message.Builder builder = MessageInfo.Message.newBuilder();
        builder.setGId(System.currentTimeMillis() + "000000");
        builder.setType(MessageInfo.Message.Type.FILE);

        MessageInfo.MessageContent.Builder contentBuilder = MessageInfo.MessageContent.newBuilder();

        MessageInfo.File.Builder fileBuilder = MessageInfo.File.newBuilder();
        byte[] conBytes = FileUtil.readBytes("D:/temp/image.jpg");
        fileBuilder.setData(ByteString.copyFrom(conBytes));
        fileBuilder.setTo("6");
        // 设置内容信息
        contentBuilder.setContent(Any.pack(fileBuilder.build()));
        builder.setMessageContent(contentBuilder.build());
        MessageInfo.Message message = builder.build();
        return message;
    }

}
