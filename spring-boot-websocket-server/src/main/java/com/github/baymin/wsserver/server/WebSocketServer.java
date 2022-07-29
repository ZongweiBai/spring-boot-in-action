package com.github.baymin.wsserver.server;

import com.github.baymin.wsserver.config.CustomSpringConfigurator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * websocket服务
 */
@Slf4j
@Component
@ServerEndpoint(value = "/boot-ws/{userId}", configurator = CustomSpringConfigurator.class)
public class WebSocketServer {

    @Autowired
    private WebsocketServerContext serverContext;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        try {
            int onLineCount = serverContext.clientRegister(userId, session);
            log.info("用户连接:" + userId + ",当前在线人数为:" + onLineCount);
            sendMessage(session, "连接成功");
        } catch (IOException e) {
            log.error("用户:{}建立websocket连接失败!", userId, e);
            try {
                sendMessage(session, "建立连接失败");
                session.close();
            } catch (IOException ioException) {
                log.error("IOException occurred!", ioException);
            }
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam("userId") String userId) {
        int onLineCount = serverContext.clientLogout(userId);
        log.info("用户退出:" + userId + ",当前在线人数为:" + onLineCount);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(@PathParam("userId") String userId, String message, Session session) {
        log.info("用户消息:" + userId + ",报文:" + message);
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(@PathParam("userId") String userId, Session session, Throwable error) {
        log.error("用户错误:" + userId, error);
        try {
            sendMessage(session, "发现不可预知的异常，关闭session");
            session.close();
        } catch (IOException e) {
            log.error("IOException occurred when try to close session!", e);
        }
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(Session session, String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }


    /**
     * 发送自定义消息
     */
    public static void sendInfo(String message, @PathParam("userId") String userId) throws IOException {
        log.info("发送消息到:" + userId + "，报文:" + message);
//        if (!StringUtils.isEmpty(userId) && webSocketMap.containsKey(userId)) {
//            webSocketMap.get(userId).sendMessage(message);
//        } else {
//            log.error("用户" + userId + ",不在线！");
//        }
    }

}
