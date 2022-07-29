package com.github.baymin.disruptor.consumer;

import com.lmax.disruptor.EventFactory;

/**
 * @author BaiZongwei
 * @date 2022/5/13 16:48
 */
public class MessageEventFactory implements EventFactory<MessageEvent> {
    @Override
    public MessageEvent newInstance() {
        return new MessageEvent();
    }
}
