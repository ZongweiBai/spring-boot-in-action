package com.github.baymin.fileserver.repository;

import io.minio.ObjectWriteResponse;

import java.io.InputStream;

/**
 * 分布式文件访问
 */
public interface DfsRepository {

    /**
     * 上传到文件服务器
     *
     * @param filePath 文件路径
     * @return ObjectWriteResponse
     */
    ObjectWriteResponse uploadObject(String filePath);

    /**
     * 上传流到文件服务器
     *
     * @param fileName    文件名
     * @param inputStream 文件流
     * @return ObjectWriteResponse
     */
    ObjectWriteResponse uploadObject(String fileName, InputStream inputStream);

    /**
     * 删除文件
     *
     * @param fileName dfs下的文件名
     */
    void deleteObject(String fileName);

    /**
     * 下载文件，返回绝对路径
     */
    String downloadObject(String bucket, String fileName);
}
