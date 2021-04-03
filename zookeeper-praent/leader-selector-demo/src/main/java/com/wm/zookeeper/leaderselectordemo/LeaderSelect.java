package com.wm.zookeeper.leaderselectordemo;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author wangm
 * @title: LeaderSelect
 * @projectName zookeeper-praent
 * @description: TODO
 * @date 2021/4/322:38
 */
public class LeaderSelect {
    private static String CONNECT_STR = "47.110.71.90:2181,47.110.71.90:2182,47.110.71.90:2183,47.110.71.90:2184";

    private static Integer SESSION_TIMES = 60 * 1000;

    private static CuratorFramework curatorFramework;

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {
        RetryPolicy policy = new ExponentialBackoffRetry(5000 ,10);
        curatorFramework = CuratorFrameworkFactory.newClient(CONNECT_STR,policy);
        curatorFramework.start();
        String appName = System.getProperty("appName");
        LeaderSelectorListener listener = new LeaderSelectorListenerAdapter() {
            @Override
            public void takeLeadership(CuratorFramework client) throws Exception {
                System.out.println("i`m leader now ,i`m," + appName);

                TimeUnit.SECONDS.sleep(15);
            }
        };
        LeaderSelector selector = new LeaderSelector(curatorFramework, "/cachePreHeat_leader", listener);
        selector.autoRequeue();
        selector.start();
        countDownLatch.await();
    }
}
