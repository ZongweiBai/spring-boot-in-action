package com.github.baymin.sse.timer;

import com.github.baymin.sse.config.ApplicationConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@EnableScheduling
public class TimerTask {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Scheduled(cron = "0 0/1 * * * *")
    public void redisKeyExpireSetting() {
        log.debug("=====>>>>>当前时间{}执行线程{}进行Redis操作", System.currentTimeMillis(), Thread.currentThread().getName());
        redisTemplate.expire(ApplicationConstant.ZSET_SSE_CONN, 5, TimeUnit.MINUTES);
    }

}
