package com.github.baymin.sse.config;

/**
 * 系统应用常量
 */
public class ApplicationConstant {

    /**
     * 每次启动生成唯一的appId
     */
    public static final String APP_ID = String.valueOf(System.currentTimeMillis());

    /**
     * 存放sse注册的用户信息，key:userId  score:注册的时间戳
     */
    public static final String ZSET_SSE_CONN = "sse:conn:uid:" + APP_ID;

    /**
     * 订阅和发布消息的key
     */
    public static final String PUB_SUB_SSE_CHANNEL = "sse:pub-sub:channel";

}
