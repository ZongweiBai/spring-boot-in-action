package com.github.baymin.sse.handler;

import com.github.baymin.sse.payload.MessageInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.baymin.sse.config.ApplicationConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * SseEmitter服务Handler
 *
 * @author Zongwei
 */
@Slf4j
@Component
public class SseEmitterHandler implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ZSetOperations<String, Object> zSetOperations;

    @Autowired
    private UserBaseHandler userBaseHandler;

    /**
     * 当前连接数
     */
    private static final AtomicInteger SSE_CONN_COUNT = new AtomicInteger(0);

    /**
     * 使用map对象，便于根据userId来获取对应的SseEmitter，或者放redis里面
     */
    private static final Map<String, SseEmitter> SSE_EMITTER_MAP = new ConcurrentHashMap<>(1250);

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        log.info("系统启动完成，开始监听心跳");
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            while (true) {
                Set<Object> uidChannels = zSetOperations.rangeByScore(ApplicationConstant.ZSET_SSE_CONN, 0, System.currentTimeMillis());
                if (CollectionUtils.isEmpty(uidChannels)) {
                    try {
                        TimeUnit.SECONDS.sleep(1L);
                    } catch (InterruptedException e) {
                        log.warn("Error occurred when thread sleep");
                    }
                } else {
                    for (Object uidChannel : uidChannels) {
                        healthCheck(uidChannel.toString());
                    }
                }
            }
        });
    }

    /**
     * 创建用户连接并返回 SseEmitter
     *
     * @param userId 用户ID
     * @return SseEmitter
     */
    public SseEmitter connect(String userId) {
        // 设置超时时间，0表示不过期。默认30秒，超过时间未完成会抛出异常：AsyncRequestTimeoutException
        SseEmitter sseEmitter = new SseEmitter(0L);

        // 注册回调
        sseEmitter.onCompletion(completionCallBack(userId));
        sseEmitter.onError(errorCallBack(userId));
        sseEmitter.onTimeout(timeoutCallBack(userId));

        // 保存连接
        SSE_EMITTER_MAP.put(userId, sseEmitter);
        int count = SSE_CONN_COUNT.incrementAndGet();
        log.info("用户：{}创建新的sse连接，当前连接数：{}", userId, count);

        zSetOperations.add(ApplicationConstant.ZSET_SSE_CONN, userId, System.currentTimeMillis());
        return sseEmitter;
    }

    /**
     * 结束用户的连接
     *
     * @param userId 用户id
     */
    public void close(String userId) {
        if (SSE_EMITTER_MAP.containsKey(userId)) {
            SSE_EMITTER_MAP.get(userId).complete();
        } else {
            removeUser(userId, true);
        }
    }

    /**
     * 给指定用户发送信息
     */
    public void sendMessage(String userId, String message) {
        if (SSE_EMITTER_MAP.containsKey(userId)) {
            try {
                // sseEmitterMap.get(userId).send(message, MediaType.APPLICATION_JSON);
                SSE_EMITTER_MAP.get(userId).send(message);
            } catch (IOException e) {
                log.error("用户[{}]推送异常:{}", userId, e.getMessage());
                removeUser(userId, true);
            }
        }
    }

    /**
     * 指定ID群发消息
     */
    public void batchSendMessage(List<String> ids, String message) {
        ids.forEach(userId -> sendMessage(userId, message));
    }

    /**
     * 群发所有人
     */
    public void batchSendMessage(String message) {
        SSE_EMITTER_MAP.forEach((k, v) -> {
            try {
                v.send(message, MediaType.APPLICATION_JSON);
            } catch (IOException e) {
                log.error("用户[{}]推送异常:{}", k, e.getMessage());
                removeUser(k, true);
            }
        });
    }

    /**
     * 获取当前连接信息
     */
    public List<String> getIds() {
        return new ArrayList<>(SSE_EMITTER_MAP.keySet());
    }

    /**
     * 获取当前连接数量
     */
    public int getUserCount() {
        return SSE_CONN_COUNT.get();
    }

    /**
     * 心跳检测
     *
     * @param userId 用户ID
     */
    private void healthCheck(String userId) {
        SseEmitter sseEmitter = SSE_EMITTER_MAP.getOrDefault(userId, null);
        if (Objects.nonNull(sseEmitter)) {
            try {
                String message = objectMapper.writeValueAsString(new MessageInfo("ping", null));
                sseEmitter.send(message);
                // 设置心跳为45秒一次
                zSetOperations.incrementScore(ApplicationConstant.ZSET_SSE_CONN, userId, 45 * 1000L);
            } catch (Exception e) {
                log.warn("用户：{}心跳检测失败，移除连接", userId);
                removeUser(userId, true);
            }
        } else {
            removeUser(userId, false);
        }
    }

    private Runnable completionCallBack(String userId) {
        return () -> {
            log.info("结束连接：{}", userId);
            removeUser(userId, false);
        };
    }

    private Runnable timeoutCallBack(String userId) {
        return () -> {
            log.info("连接超时：{}", userId);
            removeUser(userId, false);
        };
    }

    private Consumer<Throwable> errorCallBack(String userId) {
        return throwable -> {
            log.info("连接异常：{}", userId);
            removeUser(userId, false);
        };
    }

    /**
     * 移除用户连接
     *
     * @param userId    用户ID
     * @param closeChannel 是否主动触发断开连接
     */
    private void removeUser(String userId, Boolean closeChannel) {
        try {
            if (closeChannel) {
                SSE_EMITTER_MAP.get(userId).complete();
            }
        } catch (Exception e) {
            // do nothing
        } finally {
            int count = SSE_CONN_COUNT.get();
            if (SSE_EMITTER_MAP.containsKey(userId)) {
                count = SSE_CONN_COUNT.decrementAndGet();
            }
            log.info("用户：{}移除sse连接，当前连接数：{}", userId, count);
            zSetOperations.remove(ApplicationConstant.ZSET_SSE_CONN, userId);
            SSE_EMITTER_MAP.remove(userId);
            userBaseHandler.removeByUserId(userId);
        }
    }

}
