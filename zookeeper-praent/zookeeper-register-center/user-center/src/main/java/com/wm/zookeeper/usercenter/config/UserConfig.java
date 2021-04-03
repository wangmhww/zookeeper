package com.wm.zookeeper.usercenter.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author wangm
 * @title: UserConfig
 * @projectName zookeeper-praent
 * @description: TODO
 * @date 2021/4/40:03
 */
@Configuration
public class UserConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }
}
