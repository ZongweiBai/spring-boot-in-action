package com.github.baymin.fileserver.repository.impl;

import com.github.baymin.fileserver.exception.DfsServerException;
import com.github.baymin.fileserver.repository.DfsRepository;
import io.minio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.IdGenerator;
import org.springframework.util.StopWatch;

import java.io.File;
import java.io.InputStream;

@Repository
public class MinIORepository implements DfsRepository {

    private static final String TEMP_FILE_PATH = "D:/tmp/";

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private IdGenerator idGenerator;

    @Override
    public ObjectWriteResponse uploadObject(String filePath) {
        String dfsFileName = generateMinIOObject(filePath);
        try {
            return minioClient.uploadObject(UploadObjectArgs.builder()
                    .bucket("test-bucketname")
                    .filename(filePath)
                    .object(dfsFileName)
                    .build());
        } catch (Exception e) {
            throw new DfsServerException("上传文件失败", e);
        }
    }

    @Override
    public ObjectWriteResponse uploadObject(String fileName, InputStream inputStream) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String dfsFileName = generateMinIOObject(fileName);
        try {
            ObjectWriteResponse response = minioClient.putObject(PutObjectArgs.builder()
                    .bucket("test-bucketname")
                    .stream(inputStream, -1, 10485760)
                    .object(dfsFileName)
                    .build());
            stopWatch.stop();
            System.out.println("耗时：" + stopWatch.getTotalTimeMillis() + "毫秒");
            return response;
        } catch (Exception e) {
            throw new DfsServerException("上传文件流失败", e);
        }
    }

    @Override
    public void deleteObject(String fileName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket("test-bucketname")
                    .object(fileName)
                    .build());
        } catch (Exception e) {
            throw new DfsServerException("删除文件失败", e);
        }
    }

    @Override
    public String downloadObject(String bucket, String fileName) {
        try {
            File directory = new File(TEMP_FILE_PATH);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            String localFilePath = TEMP_FILE_PATH + fileName;
            minioClient.downloadObject(DownloadObjectArgs.builder()
                    .bucket(bucket)
                    .object(fileName)
                    .filename(TEMP_FILE_PATH + fileName)
                    .build());
            return localFilePath;
        } catch (Exception e) {
            throw new DfsServerException("下载文件失败", e);
        }
    }

    /**
     * 根据文件名生成minIO中的文件名
     *
     * @param fileName 原始文件名
     * @return String
     */
    private String generateMinIOObject(String fileName) {
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        return idGenerator.generateId().toString() + suffix;
    }

}
