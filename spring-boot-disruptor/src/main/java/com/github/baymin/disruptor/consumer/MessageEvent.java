package com.github.baymin.disruptor.consumer;

import lombok.Data;

/**
 * 定义消息体，用于信息传输
 *
 * @author BaiZongwei
 * @date 2022/5/13 16:48
 */
@Data
public class MessageEvent {

    private String message;

}
