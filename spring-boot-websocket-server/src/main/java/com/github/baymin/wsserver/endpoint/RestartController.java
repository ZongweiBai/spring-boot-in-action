package com.github.baymin.wsserver.endpoint;

import com.github.baymin.wsserver.service.RestartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author BaiZongwei
 * @date 2021/4/20 22:19
 */
@RestController
@RequestMapping("/")
public class RestartController {

    @Autowired
    private RestartService restartService;

    @GetMapping("/restart")
    public void restart() {
        restartService.restartApp();
    }
}
