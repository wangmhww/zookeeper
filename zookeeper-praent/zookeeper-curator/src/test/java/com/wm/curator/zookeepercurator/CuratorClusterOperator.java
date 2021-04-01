package com.wm.curator.zookeepercurator;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author wangm
 * @title: CuratorClusterOperator
 * @projectName zookeeper-praent
 * @description: TODO
 * @date 2021/4/123:43
 */
@Slf4j
public class CuratorClusterOperator  extends  CuratorClusterBase{

    @Test
    public void testCluster() throws Exception {
        CuratorFramework curatorFramework = getCuratorFramework();
        String path = "/test";
        byte[] data = curatorFramework.getData().forPath(path);
        log.info("data:{}", new String(data));
        while(true) {
            byte[] bytes = curatorFramework.getData().forPath(path);
            log.info("data:{}", new String(bytes));
            TimeUnit.SECONDS.sleep(5);
        }
    }

    @Test
    public void testCreate(){
        try {
            CuratorFramework curatorFramework = getCuratorFramework();
            String forPath = curatorFramework.create().forPath("/test");
            log.info("forPath:{}", forPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
