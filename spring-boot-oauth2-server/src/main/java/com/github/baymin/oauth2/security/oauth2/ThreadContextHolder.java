package com.github.baymin.oauth2.security.oauth2;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * ThreadLocal自定义管理类
 *
 * @author Zongwei
 */
@Slf4j
public class ThreadContextHolder {

    /**
     * ThreadLocal中用户权限key的前缀
     */
    public static final String USER_AUTHORITY_PREFIX = "USER_AUTHORITY_";

    private static final ThreadLocal<Map<String, Object>> THREAD_LOCAL = new ThreadLocal<>();

    public static void clear() {
        THREAD_LOCAL.remove();
    }

    public static void remove(String key) {
        Map<String, Object> dataMap = THREAD_LOCAL.get();
        if (Objects.nonNull(dataMap)) {
            dataMap.remove(key);
        }
        THREAD_LOCAL.set(dataMap);
    }

    public static Object get(String key) {
        Map<String, Object> dataMap = THREAD_LOCAL.get();
        if (Objects.nonNull(dataMap)) {
            return dataMap.get(key);
        } else {
            return null;
        }
    }

    public static void set(String key, Object value) {
        Map<String, Object> dataMap = THREAD_LOCAL.get();
        if (Objects.isNull(dataMap)) {
            dataMap = new HashMap<>();
        }
        dataMap.put(key, value);
        THREAD_LOCAL.set(dataMap);
    }

}
