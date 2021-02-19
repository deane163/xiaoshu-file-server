package com.xiaoshu.initializer;

import com.xiaoshu.handler.HeartBeatHandler;
import com.xiaoshu.handler.MessageInfoHandler;
import com.xiaoshu.im.MessageInfo;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 功能说明： 添加编解码器和业务处理逻辑；
 *
 * @ com.xiaoshu.initializer
 * <p>
 * Original @Author: deane.jia-贾亮亮,@2021/2/9@13:42
 * <p>
 * Copyright (C)2012-@2021 小树盛凯科技 All rights reserved.
 */
@Component
@Configuration
public class ImChannelInitializer extends ChannelInitializer<SocketChannel> {

    /**
     * 心跳检查的业务处理类实现
     */
    @Autowired
    private HeartBeatHandler heartBeatHandler;

    /**
     * 文件转发的业务处理类实现
     */
    @Autowired
    private MessageInfoHandler messageInfoHandler;

    /**
     * 初始化Channel，添加相应的业务逻辑 handler (构成 pipeline)
     *
     * @param ch
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //检测空闲，必须放到这里，因为pipeline是分顺序加载的
        pipeline.addLast("idleStateHandler", new IdleStateHandler(0, 0, 15, TimeUnit.SECONDS));
        //解码器，通过Google Protocol Buffers序列化框架动态的切割接收到的ByteBuf
        pipeline.addLast(new ProtobufVarint32FrameDecoder());
        //将接收到的二进制文件解码成具体的实例，这边接收到的是服务端的ResponseBank对象实列
        pipeline.addLast(new ProtobufDecoder(MessageInfo.Message.getDefaultInstance()));
        //Google Protocol Buffers编码器
        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
        //Google Protocol Buffers编码器
        pipeline.addLast(new ProtobufEncoder());
        // 添加心跳检测的Handler处理类
        pipeline.addLast(heartBeatHandler);
        // 添加文件转发的业务处理类
        pipeline.addLast(messageInfoHandler);
    }
}
