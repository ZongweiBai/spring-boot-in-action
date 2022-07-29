package com.github.baymin.oauth2.security.oauth2;

import com.github.baymin.oauth2.util.SpringContextUtil;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

/**
 * JWT Token增强类
 *
 * @author Zongwei
 * @date 2019/9/27 9:51
 */
public class PlatformTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        JdbcClientDetailsService clientDetailsService = SpringContextUtil.getBean(JdbcClientDetailsService.class);
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(authentication.getOAuth2Request().getClientId());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(clientDetails.getAdditionalInformation());
        return accessToken;
    }

}
