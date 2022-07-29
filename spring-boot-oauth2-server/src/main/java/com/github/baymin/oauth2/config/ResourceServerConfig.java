package com.github.baymin.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * 资源服务config
 *
 * @author Zongwei
 * @date 2019/9/27 15:49
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private AuthenticationSuccessHandler appLoginInSuccessHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                // 登录成功处理器，暂时没有使用到该处理器
                .successHandler(appLoginInSuccessHandler)
                .and()
                .authorizeRequests()
                // actuator和登录接口不需要校验
                .antMatchers("/actuator", "/actuator/*", "/user/login").permitAll()
                .anyRequest().authenticated().and()
                .csrf().disable();
    }

}
