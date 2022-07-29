package com.github.baymin.oauth2.security.oauth2;

import com.github.baymin.oauth2.util.IPAddressUtil;
import org.slf4j.MDC;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.github.baymin.oauth2.constant.OAuth2Constant.REQUEST_IP_ADDR;
import static com.github.baymin.oauth2.constant.OAuth2Constant.REQUEST_URL_ADDR;

/**
 * 获取请求的IP等信息
 */
public class HttpRequestInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ipAddr = IPAddressUtil.getIPAddress(request);
        MDC.put(REQUEST_IP_ADDR, ipAddr);
        MDC.put(REQUEST_URL_ADDR, request.getRequestURI());
        return super.preHandle(request, response, handler);
    }
}
