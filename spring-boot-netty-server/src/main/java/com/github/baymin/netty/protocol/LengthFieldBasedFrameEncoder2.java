package com.github.baymin.netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

/**
 * 自定义编码器
 *
 * @author BaiZongwei
 * @date 2021/9/1 22:57
 */
public class LengthFieldBasedFrameEncoder2 extends MessageToByteEncoder {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        TransferMessage req = (TransferMessage) msg;
        out.writeByte(req.getType());
        out.writeInt(req.getLength());

        ByteArrayOutputStream byam = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(byam);
        oos.writeObject(req.getContent());
        byte[] bytes = byam.toByteArray();

        out.writeBytes(bytes);
    }

}
