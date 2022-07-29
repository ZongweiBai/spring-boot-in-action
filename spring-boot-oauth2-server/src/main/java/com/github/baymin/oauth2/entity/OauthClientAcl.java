package com.github.baymin.oauth2.entity;

import lombok.Data;

import java.util.Set;

/**
 * oauth_client_acl实体类，oauth 客户端扩展属性
 */
@Data
public class OauthClientAcl {

    /**
     * client_id
     */
    private String clientId;

    /**
     * 每日最大访问量
     */
    private Integer dailyMaxAccess;

    /**
     * 访问白名单
     */
    private Set<String> allowList;

    /**
     * 访问黑名单
     */
    private Set<String> blockList;

}
