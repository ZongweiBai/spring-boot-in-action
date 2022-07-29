package com.github.baymin.fileserver.repository;

import com.github.baymin.fileserver.entity.FileInfo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * 数据库访问层
 *
 * @author Zongwei
 * @date 2020/4/13 23:28
 */
@Repository
public interface FileInfoRepository extends ReactiveMongoRepository<FileInfo, String> {
}
