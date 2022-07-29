package com.github.baymin.wsserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.restart.RestartEndpoint;
import org.springframework.stereotype.Service;

/**
 * @author BaiZongwei
 * @date 2021/4/20 22:12
 */
@Service
public class RestartService {

    @Autowired
    private RestartEndpoint restartEndpoint;

    public void restartApp() {
        restartEndpoint.restart();
    }

}
