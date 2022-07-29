package com.github.baymin.oauth2.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.baymin.oauth2.entity.UserProfile;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 用户基本信息
 *
 * @author Zongwei
 * @date 2019/9/29 15:04
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
public class UserInfoVO {

    private String userId;

    private String userName;

    /**
     * 用户角色ID
     */
    private List<String> userRoleIds;

    private Map<String, Object> userExt;

    public UserInfoVO(UserProfile user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
    }

}
