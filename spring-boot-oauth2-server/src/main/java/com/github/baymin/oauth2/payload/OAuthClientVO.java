package com.github.baymin.oauth2.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * 表oauth_client_details的vo类
 *
 * @author Zongwei
 * @date 2019/9/30 9:33
 */
@Data
public class OAuthClientVO {

    @NotNull
    private String clientId;

    private Set<String> resourceIds;

    @NotNull
    private String clientSecret;

    @NotEmpty
    private Set<String> scopes;

    @NotEmpty
    private Set<String> grantTypes;

    private Set<String> redirectUris;

    @NotEmpty
    private Set<String> authorities;

    @NotNull
    private Long accessTokenValidity;

    @NotNull
    private Long refreshTokenValidity;

    private String additionalInformation;

    private String autoApprove;

}
