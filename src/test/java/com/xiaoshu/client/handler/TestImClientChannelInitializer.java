package com.xiaoshu.client.handler;

import com.xiaoshu.im.MessageInfo;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * 功能说明： 客户端自己的业务实现逻辑；
 *
 * @ com.xiaoshu.client.handler
 * <p>
 * Original @Author: deane.jia-贾亮亮,@2021/2/9@14:17
 * <p>
 * Copyright (C)2012-@2021 小树盛凯科技 All rights reserved.
 */
public class TestImClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //检测空闲，必须放到这里，因为pipeline是分顺序加载的;
        pipeline.addLast("idleStateHandler", new IdleStateHandler(0, 0, 12, TimeUnit.SECONDS));
        //解码器，通过Google Protocol Buffers序列化框架动态的切割接收到的ByteBuf;
        pipeline.addLast(new ProtobufVarint32FrameDecoder());
        //将接收到的二进制文件解码成具体的实例，这边接收到的是服务端的ResponseBank对象实列;
        pipeline.addLast(new ProtobufDecoder(MessageInfo.Message.getDefaultInstance()));
        //Google Protocol Buffers编码器;
        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
        //Google Protocol Buffers编码器;
        pipeline.addLast(new ProtobufEncoder());
        // 添加自己的业务处理逻辑实现；
        pipeline.addLast(new TestClientFileHandler());
    }
}
