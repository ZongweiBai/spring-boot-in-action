package com.github.baymin.disruptor;

import com.github.baymin.disruptor.consumer.MessageEvent;
import com.github.baymin.disruptor.consumer.MessageEventFactory;
import com.github.baymin.disruptor.consumer.MessageEventHandler;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author BaiZongwei
 * @date 2022/5/16 14:14
 */
@Configuration
public class MQManager {

    @Bean("messageEventRingBuffer")
    public RingBuffer<MessageEvent> messageEventRingBuffer() {
        //指定事件工厂
        MessageEventFactory factory = new MessageEventFactory();

        //指定ringbuffer字节大小，必须为2的N次方（能将求模运算转为位运算提高效率），否则将影响效率
        int bufferSize = 1024 * 256;

        //单线程模式，获取额外的性能
        Disruptor<MessageEvent> disruptor = new Disruptor<>(factory, bufferSize, DaemonThreadFactory.INSTANCE,
                ProducerType.SINGLE, new BlockingWaitStrategy());

        //设置事件业务处理器---消费者
        disruptor.handleEventsWith(new MessageEventHandler());

        // 启动disruptor线程
        disruptor.start();

        //获取ringbuffer环，用于接取生产者生产的事件
        RingBuffer<MessageEvent> ringBuffer = disruptor.getRingBuffer();

        return ringBuffer;
    }

}
