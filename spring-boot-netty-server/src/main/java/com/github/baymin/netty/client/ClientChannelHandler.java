package com.github.baymin.netty.client;

import com.github.baymin.netty.protocol.AsyncListener;
import com.github.baymin.netty.protocol.TransferContent;
import com.github.baymin.netty.protocol.TransferMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * description:
 * author: yangzihe
 * date: 2018-12-28 14:06
 **/
@Slf4j
@Component
@ChannelHandler.Sharable
public class ClientChannelHandler extends SimpleChannelInboundHandler<Object> {

    private int counter;

    /**
     * 从服务器接收到的msg
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        TransferMessage buf = (TransferMessage) msg;
        AsyncListener asyncListener = NettyTcpClient.ASYNC_LISTENER_MAP.get(buf.getContent().getTransactionId());
        if (Objects.isNull(asyncListener)) {
            log.info("没有获取到asyncListener");
        } else {
            asyncListener.addAsyncListener(buf);
            NettyTcpClient.ASYNC_LISTENER_MAP.remove(buf.getContent().getTransactionId());
        }
        System.out.println(buf.getContent() + " count:" + ++counter + "----end----\n");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("client channelActive");
        ctx.write(new TransferMessage(TransferContent
                .builder()
                .transactionId(System.currentTimeMillis())
                .content("client连接成功")
                .build()));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("Client is close");
    }

}