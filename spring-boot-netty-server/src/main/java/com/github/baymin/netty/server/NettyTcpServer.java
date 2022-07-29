package com.github.baymin.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class NettyTcpServer {

    private static final Logger log = LoggerFactory.getLogger(NettyTcpServer.class);
    //boss事件轮询线程组
    //处理Accept连接事件的线程，这里线程数设置为1即可，netty处理链接事件默认为单线程，过度设置反而浪费cpu资源
    private EventLoopGroup boss = new NioEventLoopGroup(1);
    //worker事件轮询线程组
    //处理hadnler的工作线程，其实也就是处理IO读写 。线程数据默认为 CPU 核心数乘以2
    private EventLoopGroup worker = new NioEventLoopGroup();

    @Autowired
    ServerChannelInitializer serverChannelInitializer;

    @Value("${netty.tcp.client.port}")
    private Integer port;

    //与客户端建立连接后得到的通道对象
    private Channel channel;

    /**
     * 存储client的channel
     * key:ip，value:Channel
     */
    public static Map<String, Channel> map = new ConcurrentHashMap<String, Channel>();

    /**
     * 开启Netty tcp server服务
     *
     * @return
     */
    public ChannelFuture start() {
        //启动类
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boss, worker)//组配置，初始化ServerBootstrap的线程组
                .channel(NioServerSocketChannel.class)///构造channel通道工厂//bossGroup的通道，只是负责连接
                .childHandler(serverChannelInitializer)//设置通道处理者ChannelHandlerworkerGroup的处理器
                .option(ChannelOption.SO_BACKLOG, 1024)//socket参数，当服务器请求处理程全满时，用于临时存放已完成三次握手请求的队列的最大长度。如果未设置或所设置的值小于1，Java将使用默认值50。
                .childOption(ChannelOption.SO_KEEPALIVE, true);//启用心跳保活机制，tcp，默认2小时发一次心跳
        //Future：异步任务的生命周期，可用来获取任务结果
        ChannelFuture channelFuture1 = serverBootstrap.bind(port).syncUninterruptibly();//绑定端口，开启监听，同步等待
        if (channelFuture1 != null && channelFuture1.isSuccess()) {
            channel = channelFuture1.channel();//获取通道
            log.info("Netty tcp server start success, port = {}", port);
        } else {
            log.error("Netty tcp server start fail");
        }
        return channelFuture1;
    }

    /**
     * 停止Netty tcp server服务
     */
    @PreDestroy
    public void destroy() {
        if (channel != null) {
            channel.close();
        }
        try {
            Future<?> future = worker.shutdownGracefully().await();
            if (!future.isSuccess()) {
                log.error("netty tcp workerGroup shutdown fail, {}", future.cause());
            }
            Future<?> future1 = boss.shutdownGracefully().await();
            if (!future1.isSuccess()) {
                log.error("netty tcp bossGroup shutdown fail, {}", future1.cause());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("Netty tcp server shutdown success");
    }

}