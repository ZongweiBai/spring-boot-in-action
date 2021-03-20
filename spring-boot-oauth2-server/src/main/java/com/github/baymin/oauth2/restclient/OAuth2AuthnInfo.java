package com.github.baymin.oauth2.restclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * OAuth2获取鉴权的参数
 */
@Data
public class OAuth2AuthnInfo {

    @JsonProperty("grant_type")
    private String grantType;

    private String password;

    private String username;

    private String scope = "all";

}
