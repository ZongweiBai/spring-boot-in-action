package com.github.baymin.netty;

import com.github.baymin.netty.client.NettyTcpClient;
import com.github.baymin.netty.protocol.TransferContent;
import com.github.baymin.netty.protocol.TransferMessage;
import com.github.baymin.netty.server.NettyTcpServer;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.charset.StandardCharsets;

@Slf4j
@SpringBootApplication
public class NettyDemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(NettyDemoApplication.class, args);
    }

    @Autowired
    NettyTcpServer nettyTcpServer;
    @Autowired
    NettyTcpClient nettyTcpClient;

    @Override
    public void run(String... args) throws Exception {
        //启动服务端
        ChannelFuture start = nettyTcpServer.start();

        //启动客户端，发送数据
        nettyTcpClient.connect();
        for (int i = 0; i < 10; i++) {
            byte[] req = ("我是一条测试消息，快来读我吧，啦啦啦" + i).getBytes();
            TransferMessage build = new TransferMessage(new TransferContent(System.currentTimeMillis(), new String(req, StandardCharsets.UTF_8)));

            nettyTcpClient.sendMsg(build, transferMessage -> {
                log.info("获取到的响应结果：{}", transferMessage);
            });
        }

        //服务端管道关闭的监听器并同步阻塞,直到channel关闭,线程才会往下执行,结束进程
        start.channel().closeFuture().syncUninterruptibly();
    }
}
