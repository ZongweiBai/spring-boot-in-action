package com.github.baymin.gateway.filter.factory;

import com.github.baymin.gateway.filter.RequestTimeFilter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

/**
 * 把自定义的过滤器加入到过滤器工厂，并且注册到spring容器中
 *
 * @author Zongwei
 * @date 2020/4/17 20:59
 */
@Component
public class RequestTimeGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
    @Override
    public GatewayFilter apply(Object config) {
        return new RequestTimeFilter();
    }
}
