package com.github.baymin.disruptor;

import com.github.baymin.disruptor.producer.DisruptorMqService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author BaiZongwei
 * @date 2022/5/16 14:29
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DisruptorApplication.class)
public class ApplicationTests {

    @Autowired
    private DisruptorMqService disruptorMqService;

    /**
     * 项目内部使用Disruptor做消息队列
     *
     * @throws Exception
     */
    @Test
    public void sayHelloMqTest() throws Exception {
        disruptorMqService.publicEvent("消息到了，Hello world!");
        log.info("消息队列已发送完毕");
        //这里停止2000ms是为了确定是处理消息是异步的
        Thread.sleep(2000);
    }

}
