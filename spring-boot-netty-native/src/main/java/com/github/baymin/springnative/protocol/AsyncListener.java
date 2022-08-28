package com.github.baymin.springnative.protocol;

/**
 * 异步监听
 * @author BaiZongwei
 * @date 2021/9/2 22:58
 */
@FunctionalInterface
public interface AsyncListener {

    void addAsyncListener(TransferMessage transferMessage);

}
