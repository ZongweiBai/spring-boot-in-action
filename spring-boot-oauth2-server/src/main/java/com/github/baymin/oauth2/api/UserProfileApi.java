package com.github.baymin.oauth2.api;

import com.github.baymin.oauth2.entity.UserProfile;
import com.github.baymin.oauth2.payload.UserInfoVO;
import com.github.baymin.oauth2.restclient.OAuth2Api;
import com.github.baymin.oauth2.restclient.OAuth2AuthnInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Base64;
import java.util.UUID;

/**
 * 用户相关API
 *
 * @author Zongwei
 * @date 2019/9/27 15:58
 */
@Slf4j
@RestController
public class UserProfileApi {

    @Autowired
    private OAuth2Api oAuth2Api;

    @PostMapping(value = "/user/login")
    public OAuth2AccessToken userLogin(@RequestBody OAuth2AuthnInfo authnInfo) {
        log.info("进行用户登录操作");
        String authorization = "Basic " + Base64.getEncoder().encodeToString("oauth_client:oauth_client_9527".getBytes());
        return oAuth2Api.getAccessTokenByPassword(authorization, "password", authnInfo.getUsername(), authnInfo.getPassword(), "all");
    }

    /**
     * 获取用户的基本信息
     *
     * @param user
     * @return
     */
    @GetMapping(value = "/user/profile")
    public UserInfoVO fetchUserProfile(Principal user) {
        String username = user.getName();
        UserProfile userProfile = new UserProfile();
        userProfile.setUserName(username);
        userProfile.setUserId(UUID.randomUUID().toString());
        userProfile.setPassword(UUID.randomUUID().toString());
        UserInfoVO userInfoVo = new UserInfoVO(userProfile);
        return userInfoVo;
    }

}
