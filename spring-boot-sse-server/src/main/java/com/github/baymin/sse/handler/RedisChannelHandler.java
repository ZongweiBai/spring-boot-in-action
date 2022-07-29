package com.github.baymin.sse.handler;

import com.github.baymin.sse.payload.MessageInfo;
import com.github.baymin.sse.payload.NotifyInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Redis pub/sub模式的订阅者
 */
@Slf4j
public class RedisChannelHandler implements MessageListener {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserBaseHandler userBaseHandler;

    @Autowired
    private SseEmitterHandler sseEmitterHandler;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel(), StandardCharsets.UTF_8);
        try {
            NotifyInfo notifyInfo = (NotifyInfo) redisTemplate.getValueSerializer().deserialize(message.getBody());
            assert notifyInfo != null;
            List<String> userIds = userBaseHandler.fetchUserIds(notifyInfo);
            String messageInfo = objectMapper.writeValueAsString(new MessageInfo(notifyInfo));
            sseEmitterHandler.batchSendMessage(userIds, messageInfo);
        } catch (Exception e) {
            log.error("序列化消息载体失败", e);
        }
    }
}
