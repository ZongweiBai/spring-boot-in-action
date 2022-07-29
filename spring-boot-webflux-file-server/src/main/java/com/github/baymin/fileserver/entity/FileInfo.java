package com.github.baymin.fileserver.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * 文件信息
 *
 * @author Zongwei
 * @date 2020/4/13 23:20
 */
@Data
@Document(collection = "file_info")
public class FileInfo {

    @Id
    private String id;

    @Field(name = "origin_file_name")
    private String originFileName;

    @Field(name = "dfs_file_name")
    private String dfsFileName;

    @Field(name = "dfs_bucket")
    private String dfsBucket;

    @Field(name = "created_at")
    private Date createdAt;

}
