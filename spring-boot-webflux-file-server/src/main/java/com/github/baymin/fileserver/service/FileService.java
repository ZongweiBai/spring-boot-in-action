package com.github.baymin.fileserver.service;

import com.github.baymin.fileserver.entity.FileInfo;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

/**
 * 文件业务操作接口
 *
 * @author Zongwei
 * @date 2020/4/13 23:16
 */
public interface FileService {

    Mono<FileInfo> getFileById(String fileId);

    Mono<FileInfo> uploadFile(FilePart filePart);

    Mono<Void> deleteById(String fileId);

    String downloadFile(String dfsBucket, String dfsFileName);
}
