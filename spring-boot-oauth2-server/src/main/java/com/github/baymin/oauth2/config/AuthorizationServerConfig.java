package com.github.baymin.oauth2.config;

import com.github.baymin.oauth2.security.core.PlatformUserDetailsService;
import com.github.baymin.oauth2.security.oauth2.HttpRequestInterceptor;
import com.github.baymin.oauth2.security.oauth2.OauthClientAclService;
import com.github.baymin.oauth2.security.oauth2.OauthRequestInterceptor;
import com.github.baymin.oauth2.security.oauth2.UserAuthorityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * ????????????Config
 *
 * @author Zongwei
 * @date 2019/9/26 16:32
 */
@Slf4j
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PlatformUserDetailsService userDetailsService;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private TokenEnhancer platformTokenEnhancer;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private DataSource oauth2DataSource;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        /*
         * authorized_grant_types????????????
         * refresh_token - RefreshTokenGranter
         * authorization_code - AuthorizationCodeTokenGranter
         * implicit - ImplicitTokenGranter
         * password - ResourceOwnerPasswordTokenGranter
         * client_credentials - ClientCredentialsTokenGranter
         */
        clients.withClientDetails(jdbcClientDetailsService());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                //??????userService ?????????????????????????????????????????????????????????????????????
                .userDetailsService(userDetailsService)
                // ???client?????????????????????
                .addInterceptor(new HttpRequestInterceptor())
                .addInterceptor(oauthRequestInterceptor())
                .exceptionTranslator(loggingExceptionTranslator());

        // ??????token????????????
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> enhancerList = new ArrayList();
        enhancerList.add(platformTokenEnhancer);
        // TODO ????????????jwt??????Token
//        enhancerList.add(jwtAccessTokenConverter);
        tokenEnhancerChain.setTokenEnhancers(enhancerList);
        //jwt
        endpoints.tokenEnhancer(tokenEnhancerChain)
                // TODO ????????????jwt??????Token
//                .accessTokenConverter(jwtAccessTokenConverter)
                // ????????????Redis
                .tokenStore(redisTokenStore());
    }

    /**
     * springSecurity ??????????????????
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                // ??????/oauth/token_key???????????????????????????
                .tokenKeyAccess("permitAll()")
                // ??????/oauth/check_token??????????????????????????????
                .checkTokenAccess("isAuthenticated()");
    }

    @Bean
    public TokenStore redisTokenStore() throws Exception {
        return new RedisTokenStore(redisConnectionFactory);
    }

    @Bean
    public JdbcClientDetailsService jdbcClientDetailsService() {
        return new JdbcClientDetailsService(oauth2DataSource);
    }

    @Bean
    public UserAuthorityService userAuthorityService() {
        return new UserAuthorityService(oauth2DataSource);
    }

    @Bean
    public OauthClientAclService oauthClientAclService() {
        return new OauthClientAclService(oauth2DataSource);
    }

    @Bean
    public OauthRequestInterceptor oauthRequestInterceptor() {
        return new OauthRequestInterceptor();
    }

    /**
     * ???????????????????????????
     */
    @Bean
    public WebResponseExceptionTranslator loggingExceptionTranslator() {
        return new DefaultWebResponseExceptionTranslator() {
            @Override
            public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
                // This is the line that prints the stack trace to the log. You can customise this to format the trace etc if you like
                log.error("??????????????????", e);

                // Carry on handling the exception
                ResponseEntity<OAuth2Exception> responseEntity = super.translate(e);
                HttpHeaders headers = new HttpHeaders();
                headers.setAll(responseEntity.getHeaders().toSingleValueMap());
                OAuth2Exception excBody = responseEntity.getBody();
                return new ResponseEntity<>(excBody, headers, responseEntity.getStatusCode());
            }
        };
    }
}
