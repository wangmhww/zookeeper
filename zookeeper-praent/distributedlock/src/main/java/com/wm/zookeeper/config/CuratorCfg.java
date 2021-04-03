package com.wm.zookeeper.config;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangm
 * @title: CuratorCfg
 * @projectName zookeeper-praent
 * @description: TODO
 * @date 2021/4/30:58
 */
@Configuration
public class CuratorCfg {
    private final static String CLUSTER_CONNECT_STR = "47.110.71.90:2181,47.110.71.90:2182,47.110.71.90:2183,47.110.71.90:2184";

    private final static Integer CLUSTER_SESSION_TIMEOUT = 100*1000;
    @Bean(initMethod = "start")
    public CuratorFramework curatorFramework(){
        RetryPolicy policy = new ExponentialBackoffRetry(1000, 5);
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(CLUSTER_CONNECT_STR, policy);
        return curatorFramework;
    }
}
