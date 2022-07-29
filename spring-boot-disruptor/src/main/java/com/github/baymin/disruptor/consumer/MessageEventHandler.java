package com.github.baymin.disruptor.consumer;

import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 定义消费者
 *
 * @author BaiZongwei
 * @date 2022/5/13 16:49
 */
@Slf4j
public class MessageEventHandler implements EventHandler<MessageEvent> {

    @Override
    public void onEvent(MessageEvent messageEvent, long sequence, boolean endOfBatch) throws Exception {
        try {
            //这里停止1000ms是为了确定消费消息是异步的
            Thread.sleep(1000);
            log.info("消费者处理消息开始");
            if (messageEvent != null) {
                log.info("消费者消费的信息是：{}", messageEvent);
            }
        } catch (Exception e) {
            log.info("消费者处理消息失败");
        }
        log.info("消费者处理消息结束");
    }
}
