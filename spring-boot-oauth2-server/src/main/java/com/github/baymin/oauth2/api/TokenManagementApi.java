package com.github.baymin.oauth2.api;

import com.github.baymin.oauth2.constant.OAuth2Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Objects;

/**
 * token管理相关API
 *
 * @author Zongwei
 * @date 2019/9/27 16:35
 */
@Slf4j
@RestController
public class TokenManagementApi {

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private ConsumerTokenServices tokenServices;

    /**
     * 撤销token
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/oauth/token/revoke")
    public void revokeToken(@RequestParam("token_type") String tokenType,
                            @RequestParam("token") String token) {
        if (StringUtils.equalsIgnoreCase(tokenType, OAuth2Constant.TOKEN_TYPE_ACCESS)) {
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
            if (Objects.isNull(accessToken)) {
                return;
            }
            ((RedisTokenStore) tokenStore).removeRefreshToken(accessToken.getRefreshToken().getValue());
            tokenServices.revokeToken(token);
        } else if (StringUtils.equalsIgnoreCase(tokenType, OAuth2Constant.TOKEN_TYPE_REFRESH)) {
            ((RedisTokenStore) tokenStore).removeRefreshToken(token);
        }
    }

    /**
     * 根据clientId查询所有的Token
     *
     * @return list
     */
    @GetMapping(value = "/oauth/token/{clientId}")
    public Collection<OAuth2AccessToken> fetchTokenByClientId(@PathVariable("clientId") String clientId,
                                                              @RequestParam(value = "userName", required = false) String userName) {
        if (StringUtils.isBlank(userName)) {
            return tokenStore.findTokensByClientId(clientId);
        } else {
            return tokenStore.findTokensByClientIdAndUserName(clientId, userName);
        }
    }

}
