package com.github.baymin.disruptor.producer;

import com.github.baymin.disruptor.consumer.MessageEvent;
import com.lmax.disruptor.RingBuffer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author BaiZongwei
 * @date 2022/5/16 14:21
 */
@Slf4j
@Component
@Service
public class DisruptorMqService {

    @Autowired
    private RingBuffer<MessageEvent> messageEventRingBuffer;

    public void publicEvent(String message) {
        log.info("record the message: {}", message);
        // 获取下一个Event槽的下标
        long sequence = messageEventRingBuffer.next();
        try {
            // 给Event填充数据
            MessageEvent event = messageEventRingBuffer.get(sequence);
            event.setMessage(message);
            log.info("往消息队列中添加消息：{}", event);
        } catch (Exception e) {
            log.error("failed to add event to messageEventRingBuffer for : e = {},{}", e, e.getMessage());
        } finally {
            // 发布Event，激活观察者去消费，将sequence传递给改消费者
            // 注意最后的publish方法必须放在finally中以确保必须得到调用；如果某个请求的sequence未被提交将会堵塞后续的发布操作或者其他的producer
            messageEventRingBuffer.publish(sequence);
        }
    }

}
