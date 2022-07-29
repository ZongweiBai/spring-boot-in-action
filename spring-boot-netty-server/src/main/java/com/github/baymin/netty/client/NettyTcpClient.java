package com.github.baymin.netty.client;

import com.github.baymin.netty.protocol.AsyncListener;
import com.github.baymin.netty.protocol.TransferMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * description:
 * author: yangzihe
 * date: 2018-12-28 13:59
 **/
@Component
public class NettyTcpClient {

    private static final Logger log = LoggerFactory.getLogger(NettyTcpClient.class);

    /**
     * 存储异步响应结果
     */
    public static final Map<Long, AsyncListener> ASYNC_LISTENER_MAP = new ConcurrentHashMap<>();

    @Value(("${netty.tcp.server.host}"))
    String HOST;
    @Value("${netty.tcp.server.port}")
    int PORT;

    @Autowired
    ClientChannelInitializer clientChannelInitializer;

    //与服务端建立连接后得到的通道对象
    private Channel channel;

    /**
     * 初始化 `Bootstrap` 客户端引导程序
     *
     * @return
     */
    private final Bootstrap getBootstrap() {
        Bootstrap b = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();
        b.group(group)
                .channel(NioSocketChannel.class)//通道连接者
                .handler(clientChannelInitializer)//通道处理者
                .option(ChannelOption.SO_KEEPALIVE, true);//心跳报活
        return b;
    }

    /**
     * 建立连接，获取连接通道对象
     *
     * @return
     */
    public void connect() {
        ChannelFuture channelFuture = getBootstrap().connect(HOST, PORT).syncUninterruptibly();
        if (channelFuture != null && channelFuture.isSuccess()) {
            channel = channelFuture.channel();
            log.info("connect tcp server host = {}, port = {} success", HOST, PORT);
        } else {
            log.error("connect tcp server host = {}, port = {} fail", HOST, PORT);
        }
    }

    /**
     * 向服务器发送消息
     *
     * @param msg
     * @param asyncListener
     * @throws Exception
     */
    public void sendMsg(Object msg, AsyncListener asyncListener) throws Exception {
        if (channel != null) {
            ASYNC_LISTENER_MAP.put(((TransferMessage) msg).getContent().getTransactionId(), asyncListener);
            channel.writeAndFlush(msg).sync();
        } else {
            log.warn("消息发送失败,连接尚未建立!");
        }
    }

}


