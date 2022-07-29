package com.github.baymin.fileserver.exception;

/**
 * 分布式文件系统操作自定义异常
 */
public class DfsServerException extends RuntimeException {
    private static final long serialVersionUID = 4230806510214523993L;

    public DfsServerException(Exception e) {
        super(e);
    }

    public DfsServerException(String message, Exception e) {
        super(message, e);
    }
}
