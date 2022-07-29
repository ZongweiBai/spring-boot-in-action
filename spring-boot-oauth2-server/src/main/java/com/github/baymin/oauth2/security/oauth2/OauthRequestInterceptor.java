package com.github.baymin.oauth2.security.oauth2;

import com.github.baymin.oauth2.entity.OauthClientAcl;
import com.github.baymin.oauth2.util.IPAddressUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.github.baymin.oauth2.constant.OAuth2Constant.REQUEST_IP_ADDR;
import static com.github.baymin.oauth2.constant.OAuth2Constant.REQUEST_URL_ADDR;

@Slf4j
public class OauthRequestInterceptor implements WebRequestInterceptor {

    @Autowired
    private OauthClientAclService oauthClientAclService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void preHandle(WebRequest webRequest) throws Exception {
        String requestUrl = (webRequest.getContextPath() + "/oauth/token").replace("//", "/");
        if (!MDC.get(REQUEST_URL_ADDR).contentEquals(requestUrl)) {
            return;
        }
        String clientId = webRequest.getUserPrincipal().getName();
        List<OauthClientAcl> oauthClientAclList = oauthClientAclService.queryClientAcl(clientId);
        if (CollectionUtils.isEmpty(oauthClientAclList)) {
            return;
        }

        String requestIpAddr = MDC.get(REQUEST_IP_ADDR);
        OauthClientAcl acl = oauthClientAclList.get(0);

        // 判断IP是否允许访问
        boolean hasPermission = true;
        if (!CollectionUtils.isEmpty(acl.getBlockList())) {
            for (String blockAddr : acl.getBlockList()) {
                if (IPAddressUtil.checkIPMatching(requestIpAddr, blockAddr)) {
                    hasPermission = false;
                    break;
                }
            }
        }
        if (!hasPermission && !CollectionUtils.isEmpty(acl.getAllowList())) {
            for (String allowAddr : acl.getAllowList()) {
                if (IPAddressUtil.checkIPMatching(requestIpAddr, allowAddr)) {
                    hasPermission = true;
                    break;
                }
            }
        }

        if (!hasPermission) {
            throw new AccessDeniedException("请求IP未允许访问");
        }

        // 判断是否达到最大访问次数限制
        if (Objects.isNull(acl.getDailyMaxAccess())) {
            return;
        }
        if (acl.getDailyMaxAccess() < 0) {
            throw new AccessDeniedException("已经达到今天的访问最大次数");
        }

        int currentAccessNum = 0;
        String redisAccessKey = clientId + ":" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String accessNum = redisTemplate.opsForValue().get(redisAccessKey);
        // 初次访问，设置过期时间，给初始值
        if (StringUtils.isEmpty(accessNum)) {
            redisTemplate.opsForValue().set(redisAccessKey, "0", 1, TimeUnit.DAYS);
        } else {
            currentAccessNum = Integer.parseInt(accessNum);
        }
        if (currentAccessNum > acl.getDailyMaxAccess()) {
            throw new AccessDeniedException("已经达到今天的访问最大次数");
        }
        redisTemplate.opsForValue().increment(redisAccessKey);
    }

    @Override
    public void postHandle(WebRequest webRequest, ModelMap modelMap) throws Exception {

    }

    @Override
    public void afterCompletion(WebRequest webRequest, Exception e) throws Exception {

    }
}
