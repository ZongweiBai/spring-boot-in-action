package com.github.baymin.springnative.client;

import com.github.baymin.springnative.protocol.LengthFieldBasedFrameDecoder2;
import com.github.baymin.springnative.protocol.LengthFieldBasedFrameEncoder2;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * description: 通道初始化，主要用于设置各种Handler
 * author:
 * date: 2018-11-28 14:55
 **/
@Component
public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    ClientChannelHandler clientChannelHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //IdleStateHandler心跳机制,如果超时触发Handle中userEventTrigger()方法
        pipeline.addLast("idleStateHandler",
                new IdleStateHandler(15, 0, 0, TimeUnit.MINUTES));
        /*//字符串编解码器
        pipeline.addLast(
                new StringDecoder(),
                new StringEncoder()
        );*/

        pipeline.addLast(new LengthFieldBasedFrameEncoder2());
        /**
         *
         * MsgReq 对象 ：
         * byte type 字段占一个字节
         * int length 字段 占4个字节
         * 剩下的是消息体
         * 那么 这里的 参数设置：
         *
         * int maxFrameLength：可以设置你认为的消息最大上限
         * int lengthFieldOffset：长度字段从哪个位置开始读：第0个字节是type，长度是从1开始的
         * int lengthFieldLength：长度字段所占的字节数，int类型占4个字节
         * int lengthAdjustment：是否需要调整消息头的长度，即读取消息头是否需要偏移一下，我们这里不需要
         * int initialBytesToStrip：消息体是否需要忽略一些字节数，比如忽略消息头的长度，我们这里消息头也算在对象里面了所以不忽略
         * boolean failFast：如果读取长度不够是否快速失败
         *
         */
        pipeline.addLast(new LengthFieldBasedFrameDecoder2(1024,
                1,
                4,
                0,
                0,
                true));

        //自定义Handler
        pipeline.addLast("clientChannelHandler", clientChannelHandler);
    }
}
