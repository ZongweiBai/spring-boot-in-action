package com.github.baymin.oauth2.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.Set;

/**
 * oauth_user_authority实体类，用户附属权限
 *
 * @author Zongwei
 * @date 2019/9/30 14:03
 */
@Data
public class UserAuthority {

    public static final String AUTHORITY_TYPE_ROLE = "ROLE";
    public static final String AUTHORITY_TYPE_API = "API";
    public static final String AUTHORITY_TYPE_TABLE = "TABLE";

    @JsonSerialize(using = ToStringSerializer.class)
    private Long authorityId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 权限类型
     * MANAGEMENT：管理权限
     * API：API权限
     * TABLE：表权限
     */
    private String authorityType;

    /**
     * 具体权限，在表中多个以,号分隔
     * 配置规则，项目名:API名，如oauth2:GET-DATASET-BY-LOCATION
     */
    private Set<String> authority;

}
