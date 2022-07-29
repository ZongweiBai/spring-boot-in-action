package com.github.baymin.consul.endpoint;

import com.github.baymin.consul.config.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/properties")
@RestController
public class PropertiesEndpoint {

    @Autowired
    private ApplicationConfig config;

    @GetMapping
    public String getProperties() {
        return config.getAppVersion();
    }

}
