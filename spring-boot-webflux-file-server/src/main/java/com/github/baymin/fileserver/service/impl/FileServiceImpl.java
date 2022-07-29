package com.github.baymin.fileserver.service.impl;

import com.github.baymin.fileserver.entity.FileInfo;
import com.github.baymin.fileserver.repository.DfsRepository;
import com.github.baymin.fileserver.repository.FileInfoRepository;
import com.github.baymin.fileserver.service.FileService;
import io.minio.ObjectWriteResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.FileNotFoundException;
import java.util.Date;

/**
 * 文件业务操作接口实现类
 *
 * @author Zongwei
 * @date 2020/4/13 23:18
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private DfsRepository dfsRepository;

    @Autowired
    private FileInfoRepository fileInfoRepository;

    @Override
    public Mono<FileInfo> uploadFile(FilePart filePart) {
        return DataBufferUtils.join(filePart.content())
                .map(dataBuffer -> {
                    ObjectWriteResponse writeResponse = dfsRepository.uploadObject(filePart.filename(), dataBuffer.asInputStream());
                    FileInfo fileInfo = new FileInfo();
                    fileInfo.setOriginFileName(filePart.filename());
                    fileInfo.setDfsFileName(writeResponse.object());
                    fileInfo.setDfsBucket(writeResponse.bucket());
                    fileInfo.setCreatedAt(new Date());
                    return fileInfo;
                })
                .flatMap(fileInfo -> fileInfoRepository.insert(fileInfo))
                .onErrorStop();
    }

    @Override
    public Mono<FileInfo> getFileById(String fileId) {
        return fileInfoRepository.findById(fileId);
    }

    @Override
    public Mono<Void> deleteById(String fileId) {
        Mono<FileInfo> fileInfoMono = this.getFileById(fileId);
        Mono<FileInfo> fallback = Mono.error(new FileNotFoundException("No file was found with fileId: " + fileId));
        return fileInfoMono
                .switchIfEmpty(fallback)
                .flatMap(fileInfo -> {
                    dfsRepository.deleteObject(fileInfo.getDfsFileName());
                    return fileInfoRepository.deleteById(fileId);
                }).then();
    }

    @Override
    public String downloadFile(String dfsBucket, String dfsFileName) {
        return dfsRepository.downloadObject(dfsBucket, dfsFileName);
    }
}
