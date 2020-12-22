package com.durp.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;



@Component
@Slf4j
public class AccessZuulFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("access filter running...");
        // TODO: 2020/12/22   验证token

        // TODO: 2020/12/22   鉴权

        // TODO: 2020/12/22   封装权限信息到Spring Security Context
//        SecurityContextHolder.getContext().setAuthentication(authentication);
        return null;
    }
}
