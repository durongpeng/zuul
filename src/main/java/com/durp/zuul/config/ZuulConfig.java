package com.durp.zuul.config;

import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.zuul.fallback.ZuulBlockFallbackManager;
import com.alibaba.csp.sentinel.adapter.gateway.zuul.filters.SentinelZuulErrorFilter;
import com.alibaba.csp.sentinel.adapter.gateway.zuul.filters.SentinelZuulPostFilter;
import com.alibaba.csp.sentinel.adapter.gateway.zuul.filters.SentinelZuulPreFilter;
import com.netflix.zuul.ZuulFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class ZuulConfig {

    @Bean
    public ZuulFilter sentinelZuulPreFilter() {
        return new SentinelZuulPreFilter();
    }

    @Bean
    public ZuulFilter sentinelZuulPostFilter() {
        return new SentinelZuulPostFilter();
    }

    @Bean
    public ZuulFilter sentinelZuulErrorFilter() {
        return new SentinelZuulErrorFilter();
    }

    @PostConstruct
    public void doInit() {
        // 注册 FallbackProvider
        ZuulBlockFallbackManager.registerProvider(new MyBlockFallbackProvider());
        initGatewayRules();
    }

    /**
     * 配置限流规则
     */
    private void initGatewayRules() {
        Set<GatewayFlowRule> rules = new HashSet<>();
        GatewayFlowRule gatewayFlowRule = new GatewayFlowRule();
        // 资源名称，可以是网关中的route名称或者用户自定义的API名称
        gatewayFlowRule.setResource("serviceA");
        // 规则是针对 API Gateway 的 route（RESOURCE_MODE_ROUTE_ID）
        // 还是用户在 Sentinel 中定义的 API 分组（RESOURCE_MODE_CUSTOM_API_NAME），默认是 route
        gatewayFlowRule.setResourceMode(SentinelGatewayConstants.RESOURCE_MODE_ROUTE_ID);

        rules.add(gatewayFlowRule);

        GatewayRuleManager.loadRules(rules);
    }
}
