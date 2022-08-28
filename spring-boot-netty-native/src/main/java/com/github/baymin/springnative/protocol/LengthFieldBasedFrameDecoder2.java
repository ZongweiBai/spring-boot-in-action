package com.github.baymin.springnative.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

/**
 * 自定义解码器
 *
 * @author BaiZongwei
 * @date 2021/9/1 22:57
 */
public class LengthFieldBasedFrameDecoder2 extends LengthFieldBasedFrameDecoder {

    /**
     * @param maxFrameLength      帧的最大长度
     * @param lengthFieldOffset   length字段偏移的地址
     * @param lengthFieldLength   length字段所占的字节长
     * @param lengthAdjustment    修改帧数据长度字段中定义的值，可以为负数 因为有时候我们习惯把头部记入长度,若为负数,则说明要推后多少个字段
     * @param initialBytesToStrip 解析时候跳过多少个长度
     * @param failFast            为true，当frame长度超过maxFrameLength时立即报TooLongFrameException异常，为false，读取完整个帧再报异
     */
    public LengthFieldBasedFrameDecoder2(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength,
                                         int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
    }


    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        //在这里调用父类的方法
        in = (ByteBuf) super.decode(ctx, in);
        if (in == null) {
            return null;
        }
        //读取type字段
        byte type = in.readByte();
        //读取length字段
        int length = in.readInt();
        if (in.readableBytes() != length) {
            throw new RuntimeException("长度与标记不符");
        }
        //读取body
        byte[] bytes = new byte[in.readableBytes()];
        in.readBytes(bytes);

        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bais);

        TransferContent s = (TransferContent)ois.readObject();

        return TransferMessage.builder().length(length).type(type).content(s).build();
    }

}
