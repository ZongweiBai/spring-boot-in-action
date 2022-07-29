package com.github.baymin.oauth2.api;

import com.github.baymin.oauth2.entity.UserAuthority;
import com.github.baymin.oauth2.security.oauth2.UserAuthorityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户权限管理API
 *
 * @author Zongwei
 * @date 2019/10/8 9:26
 */
@Slf4j
@RestController
public class UserAuthorityApi {

    @Autowired
    private UserAuthorityService userAuthorityService;

    /**
     * 创建user_authority
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/user/authority")
    public UserAuthority createAuthority(@RequestBody UserAuthority userAuthority) {
        userAuthorityService.addUserAuthority(userAuthority);
        return userAuthority;
    }

    /**
     * 删除user_authority
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/user/authority/{userId}")
    public void deleteOneClient(@PathVariable String userId,
                                @RequestParam String authorityType) {
        userAuthorityService.removeUserAuthority(userId, authorityType);
    }

    /**
     * 更新oauth_client
     */
    @PutMapping(value = "/user/authority/{authorityId}")
    public UserAuthority updateClient(@PathVariable Long authorityId,
                                      @RequestBody UserAuthority userAuthority) {
        userAuthority.setAuthorityId(authorityId);
        userAuthorityService.updateUserAuthority(userAuthority);
        return userAuthority;
    }

    /**
     * 根据user_id查询
     */
    @GetMapping(value = "/user/authority/{userId}")
    public List<UserAuthority> fetchUserAuthority(@PathVariable String userId) {
        return userAuthorityService.queryUserAuthority(userId);
    }

}
