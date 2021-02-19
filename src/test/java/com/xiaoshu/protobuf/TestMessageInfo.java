package com.xiaoshu.protobuf;

import cn.hutool.core.io.FileUtil;
import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.xiaoshu.im.MessageInfo;
import org.junit.jupiter.api.Test;

/**
 * 功能说明： 测试代码类，测试基础功能实现；
 *
 * @ com.xiaoshu.protobuf
 * <p>
 * Original @Author: deane.jia-贾亮亮,@2021/2/9@11:25
 * <p>
 * Copyright (C)2012-@2021 小树盛凯科技 All rights reserved.
 */

public class TestMessageInfo {

    @Test
    public void testMessage1() throws InvalidProtocolBufferException {
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
        System.out.println(message);

        // =======================  解析消息
        System.out.println("==============================================================>");
        MessageInfo.Message srcMessage = MessageInfo.Message.parseFrom(message.toByteArray());
        System.out.println(srcMessage.toString());
    }
}
