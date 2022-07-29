package com.github.baymin.wsserver.server;

import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * server的上下文，用于处理在线连接数，在线session相关
 */
@Component
public class WebsocketServerContext {

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static final AtomicInteger ONLINE_COUNT = new AtomicInteger(0);

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static final ConcurrentHashMap<String, Session> WEBSOCKET_SESSION_MAP = new ConcurrentHashMap<>(4000);

    /**
     * 建立连接
     *
     * @return online count
     */
    public int clientRegister(String userId, Session session) throws IOException {
        if (WEBSOCKET_SESSION_MAP.containsKey(userId)) {
            // 关闭旧的客户端
            WEBSOCKET_SESSION_MAP.get(userId).close();
            WEBSOCKET_SESSION_MAP.remove(userId);
            return ONLINE_COUNT.get();
        } else {
            WEBSOCKET_SESSION_MAP.put(userId, session);
            return ONLINE_COUNT.incrementAndGet();
        }
    }

    /**
     * 断开连接
     */
    public int clientLogout(String userId) {
        if (WEBSOCKET_SESSION_MAP.containsKey(userId)) {
            WEBSOCKET_SESSION_MAP.remove(userId);
            return ONLINE_COUNT.decrementAndGet();
        }
        return ONLINE_COUNT.get();
    }
}
