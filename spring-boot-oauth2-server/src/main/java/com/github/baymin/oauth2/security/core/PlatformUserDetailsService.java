package com.github.baymin.oauth2.security.core;

import com.github.baymin.oauth2.entity.UserProfile;
import com.github.baymin.oauth2.security.UserGrantedAuthority;
import com.github.baymin.oauth2.security.oauth2.UserAuthorityService;
import com.github.baymin.oauth2.util.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;

/**
 * 自定义的用户接口实现类
 *
 * @author Zongwei
 * @date 2019/9/27 9:53
 */
@Component
public class PlatformUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserProfile userProfile = new UserProfile();
        userProfile.setPassword(UUID.randomUUID().toString());
        userProfile.setUserId(UUID.randomUUID().toString());
        userProfile.setUserName(username);

        UserAuthorityService userAuthorityService = SpringContextUtil.getBean(UserAuthorityService.class);
        Set<UserGrantedAuthority> grantedAuthorities = userAuthorityService.getUserGrantedAuthorities(userProfile.getUserId());

        return User.builder().username(username).password(userProfile.getPassword())
                .accountExpired(false).accountLocked(false).disabled(false).credentialsExpired(false)
                .authorities(grantedAuthorities)
                .build();
    }
}
