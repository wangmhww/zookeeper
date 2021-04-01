package com.wm.zookeeper.zookeeperfirst;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;
import org.junit.After;
import org.junit.Before;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author wangm
 * @title: StandaloneBase
 * @projectName zookeeper-praent
 * @description: Zookeeper配置封装类
 * @date 2021/3/3023:32
 */
@Slf4j
public class StandaloneBase {
    private final static String CONNECT_STR = "47.110.71.90:2181";

    private final static Integer SESSION_TIMEOUT = 30*1000;

    private static ZooKeeper zooKeeper = null;

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    private Watcher watcher = new Watcher() {
        @Override
        public void process(WatchedEvent event) {
            if (event.getType() == Event.EventType.None
             && event.getState() == Event.KeeperState.SyncConnected) {
                countDownLatch.countDown();
                log.info("连接已建立");
            }
        }
    };

    @Before
    public void init(){
        try {
            log.info("start to connect to zookeeper server: {}", getConnectStr());
            zooKeeper = new ZooKeeper(getConnectStr(), getTimeOut(), watcher);
            // 添加授权 访问不授权的则不需要添加
            zooKeeper.addAuthInfo("digest", "wangm:111111".getBytes());
            log.info("连接中...");
            countDownLatch.await();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public ZooKeeper getZookeeper(){
        return zooKeeper;
    }

    public String getConnectStr(){
        return CONNECT_STR;
    }

    public int getTimeOut(){
        return SESSION_TIMEOUT;
    }

    @After
    public void test(){
        try {
            TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
