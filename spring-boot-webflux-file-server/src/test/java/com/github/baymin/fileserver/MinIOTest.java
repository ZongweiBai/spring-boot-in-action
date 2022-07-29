package com.github.baymin.fileserver;

import io.minio.*;
import io.minio.errors.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class MinIOTest {

    private static MinioClient minioClient;

    @BeforeAll
    static void beforeAll() {
        minioClient = MinioClient.builder()
                .endpoint("192.168.111.128", 9004, false)
                .credentials("minio", "minio123")
                .build();
    }

    @Test
    @DisplayName("测试Bucket是否存在")
    public void testBucketExists() throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket("my-bucketname").build());
        if (found) {
            System.out.println("my-bucketname exists");
        } else {
            System.out.println("my-bucketname does not exist");
        }
    }

    @Order(1)
    @Test
    @DisplayName(("创建Bucket"))
    public void testCreateBucket() throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
        minioClient.makeBucket(MakeBucketArgs.builder().bucket("test-bucketname").build());
    }

    @Order(2)
    @Test
    @DisplayName(("上传文件到Bucket"))
    public void testUploadFileToBucket() throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
        ObjectWriteResponse response = minioClient.uploadObject(UploadObjectArgs.builder()
                .bucket("test-bucketname")
                .filename("D:/Desktop/big_data.png")
                .object("big_data.png")
                .build());
        System.out.println(response.versionId());
        System.out.println(response.etag());
    }

    @Order(3)
    @Test
    @DisplayName(("删除文件"))
    public void testDeleteFile() throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
        minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket("test-bucketname")
                .object("big_data.png")
                .build());
    }

}
