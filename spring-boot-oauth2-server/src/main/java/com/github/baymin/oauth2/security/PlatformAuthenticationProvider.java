package com.github.baymin.oauth2.security;

import com.github.baymin.oauth2.entity.UserProfile;
import com.github.baymin.oauth2.security.oauth2.ThreadContextHolder;
import com.github.baymin.oauth2.security.oauth2.UserAuthorityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static com.github.baymin.oauth2.security.oauth2.ThreadContextHolder.USER_AUTHORITY_PREFIX;

/**
 * 自定义spring security认证处理类
 *
 * @author Zongwei
 */
@Slf4j
public class PlatformAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserAuthorityService userAuthorityService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取登录用户名/userId
        String userName = authentication.getName();
        // 获取登录用户密码
        String password = (String) authentication.getCredentials();

        // 调用hessian接口校验用户名和密码
        UserProfile user = new UserProfile(UUID.randomUUID().toString(), userName, password);

//        if (Objects.isNull(user)) {
//            throw new BadCredentialsException("用户名不存在或用户名密码不匹配");
//        }

        Set<UserGrantedAuthority> grantedAuthorities = userAuthorityService.getUserGrantedAuthorities(user.getUserId());
        ThreadContextHolder.set(USER_AUTHORITY_PREFIX + userName, grantedAuthorities);

        UserDetails userInfo = User.builder().username(userName).password(password)
                .accountExpired(false).accountLocked(false).disabled(false).credentialsExpired(false)
                .authorities(grantedAuthorities)
                .build();

        Collection<? extends GrantedAuthority> authorities = userInfo.getAuthorities();
        // 构建返回的用户登录成功的token
        return new UsernamePasswordAuthenticationToken(userInfo, password, authorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        // 这里直接改成return true;表示是支持这个执行
        return true;
    }
}
