package com.github.baymin.oauth2.config;

import com.github.baymin.oauth2.constant.OAuth2Constant;
import com.github.baymin.oauth2.security.oauth2.PlatformTokenEnhancer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * JWT TOKEN配置信息
 *
 * @author Zongwei
 * @date 2019/9/27 9:50
 */
@Configuration
public class JwtTokenConfig {

    /**
     * 使用jwtTokenStore存储token
     * @return
     */
//    @Bean
//    public TokenStore jwtTokenStore() throws Exception {
//        return new JwtTokenStore(jwtAccessTokenConverter());
//    }

    /**
     * 用于生成jwt
     *
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() throws Exception {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        //生成签名的key
        accessTokenConverter.setSigningKey(OAuth2Constant.JWT_SIGN_KEY);
        accessTokenConverter.setVerifierKey(OAuth2Constant.JWT_SIGN_KEY);
        accessTokenConverter.afterPropertiesSet();
        return accessTokenConverter;
    }

    /**
     * 用于扩展JWT
     *
     * @return
     */
    @Bean
//    @ConditionalOnMissingBean(name = "jwtTokenEnhancer")
    public TokenEnhancer platformTokenEnhancer() {
        return new PlatformTokenEnhancer();
    }

}
