package com.github.baymin.fileserver.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AlternativeJdkIdGenerator;
import org.springframework.util.IdGenerator;

@Configuration
public class ApplicationConfig {

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
//                .endpoint("192.168.111.128", 9004, false)
                .endpoint("192.168.3.168", 9004, false)
                .credentials("minio", "minio123")
                .build();
    }

    @Bean
    public IdGenerator idGenerator() {
        return new AlternativeJdkIdGenerator();
    }

}
