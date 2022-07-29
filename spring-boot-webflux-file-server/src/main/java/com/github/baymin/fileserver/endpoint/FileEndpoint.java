package com.github.baymin.fileserver.endpoint;

import com.github.baymin.fileserver.entity.FileInfo;
import com.github.baymin.fileserver.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ZeroCopyHttpOutputMessage;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 文件上传下载接口
 *
 * @author Zongwei
 * @date 2020/4/13 22:17
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/file")
public class FileEndpoint {

    @Autowired
    private FileService fileService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<FileInfo> uploadFile(@RequestPart("file") FilePart filePart) {
        log.info("upload file:{}", filePart.filename());
        return fileService.uploadFile(filePart);
    }

    @GetMapping("download/{fileId}")
    public Mono<Void> downloadFile(@PathVariable String fileId, ServerHttpResponse response) {
        Mono<FileInfo> fileInfoMono = fileService.getFileById(fileId);
        Mono<FileInfo> fallback = Mono.error(new FileNotFoundException("No file was found with fileId: " + fileId));
        return fileInfoMono
                .switchIfEmpty(fallback)
                .flatMap(fileInfo -> {
                    var fileName = new String(fileInfo.getOriginFileName().getBytes(Charset.defaultCharset()), StandardCharsets.ISO_8859_1);
                    var tmpFilePath = fileService.downloadFile(fileInfo.getDfsBucket(), fileInfo.getDfsFileName());
                    ZeroCopyHttpOutputMessage zeroCopyResponse = (ZeroCopyHttpOutputMessage) response;
                    response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
                    response.getHeaders().setContentType(MediaType.APPLICATION_OCTET_STREAM);

                    var file = new File(tmpFilePath);
                    return zeroCopyResponse.writeWith(file, 0, file.length());
                });
    }

    @DeleteMapping("{fileId}")
    public Mono<Void> deleteFile(@PathVariable String fileId, ServerHttpResponse response) {
        return fileService.deleteById(fileId);
    }

}
