package com.github.baymin.oauth2.restclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${spring.application.name}", path = "${server.servlet.context-path}")
public interface OAuth2Api {

    /**
     * 获取AccessToken
     *
     * @return OAuth2AccessToken
     */
    @PostMapping("/oauth/token")
    OAuth2AccessToken getAccessTokenByPassword(@RequestHeader("Authorization") String authorization,
                                               @RequestParam(value = "grant_type", defaultValue = "password") String grantType,
                                               @RequestParam("username") String username,
                                               @RequestParam("password") String password,
                                               @RequestParam(value = "scope", defaultValue = "all") String scope);

}
