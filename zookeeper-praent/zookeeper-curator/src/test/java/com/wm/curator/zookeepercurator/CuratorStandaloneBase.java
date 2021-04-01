package com.wm.curator.zookeepercurator;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;

/**
 * @author wangm
 * @title: CuratorStandaloneBase
 * @projectName zookeeper-praent
 * @description: TODO
 * @date 2021/3/310:19
 */
@Slf4j
public class CuratorStandaloneBase {
    private final static String CONNECT_STR = "47.110.71.90:2181";

    private final static int sessionTimeoutMs = 60*1000;

    private final static int connectTimeoutMs = 5000;

    private static CuratorFramework curatorFramework = null;

    @Before
    public void init(){
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(5000, 30);
        curatorFramework = CuratorFrameworkFactory.builder().connectString(getConnectStr())
                .retryPolicy(retryPolicy)
                .sessionTimeoutMs(getSessionTimeoutMs())
                .connectionTimeoutMs(getConnectTimeoutMs())
                .canBeReadOnly(true)
                .build();
        curatorFramework.getConnectionStateListenable().addListener((client, newState) -> {
            if (newState == ConnectionState.CONNECTED){
                log.info("连接已建立");
            }
        });
        log.info("连接建立中....");
        curatorFramework.start();
    }

    public void createIfNeed(String path){
        try {
            Stat stat  = curatorFramework.checkExists().forPath(path);
            if (stat == null) {
                String s = curatorFramework.create().forPath(path);
                log.info("path:{} , 已创建", s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static CuratorFramework getCuratorFramework() {
        return curatorFramework;
    }

    public String getConnectStr() {
        return CONNECT_STR;
    }

    public int getSessionTimeoutMs() {
        return sessionTimeoutMs;
    }

    public int getConnectTimeoutMs() {
        return connectTimeoutMs;
    }
}
