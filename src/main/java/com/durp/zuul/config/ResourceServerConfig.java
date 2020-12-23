package com.durp.zuul.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

// 获取code
// http://localhost:9000/oauth/authorize?client_id=client&response_type=code&scope=all&redirect_uri=http://localhost:9000/r/r1
// 获取token
// http://localhost:9000/oauth/token?client_id=client&client_secret=secret&grant_type=authorization_code&code=h74kWB&redirect_uri=http://localhost:9000/r/r1
// 检查token
// http://localhost:9000/oauth/check_token?token=9032d158-d056-4ded-8072-c440f44a5a79
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    public static final String RESOURCE_ID = "res1";

    @Bean
    public RemoteTokenServices tokenService() {
        RemoteTokenServices tokenService = new RemoteTokenServices();
        tokenService.setClientId("client");
        tokenService.setClientSecret("secret");
        tokenService.setCheckTokenEndpointUrl("http://localhost:9000/oauth/check_token");
        return tokenService;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenServices(tokenService());
        resources.resourceId(RESOURCE_ID);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/**").access("#oauth2.hasScope('all')")
                .and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;
    }


}
