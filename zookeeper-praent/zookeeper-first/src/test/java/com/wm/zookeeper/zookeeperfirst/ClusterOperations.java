package com.wm.zookeeper.zookeeperfirst;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;
import org.junit.Test;

import java.security.acl.Acl;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author wangm
 * @title: ClusterOperations
 * @projectName zookeeper-praent
 * @description: 使用Zookeeper客户端连接集群环境
 * @date 2021/4/123:12
 */
@Slf4j
public class ClusterOperations extends ClusterBase{

    @Test
    public void testReconnect() throws InterruptedException {
        try {
            while(true) {
                Stat stat = new Stat();
                byte[] data = getZookeeper().getData("/acltest", false,  stat);
                log.info("data:{}", new String(data));
                TimeUnit.SECONDS.sleep(5);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("开始重连");
            while(true) {
                log.info("zookeeper.status:{}",getZookeeper().getState().name());
                if(getZookeeper().getState().isConnected()){
                    break;
                }
                TimeUnit.SECONDS.sleep(3);
            }
        }
    }

    /**
     * 使用ACL权限创建文件
     * @throws InterruptedException
     */
    @Test
    public void testACLCreate() throws InterruptedException {
        try {
            String authStr = DigestAuthenticationProvider.generateDigest("wangming:111111");
            log.info("authStr:{}", authStr);
            ACL digest = new ACL(ZooDefs.Perms.READ | ZooDefs.Perms.WRITE, new Id("digest",authStr));
            ACL ipacl = new ACL(ZooDefs.Perms.READ | ZooDefs.Perms.WRITE, new Id("ip", "121.205.48.79"));
            ArrayList<ACL> acls = new ArrayList<ACL>();
            acls.add(digest); // digest授权方式
            acls.add(ipacl); // ip授权方式
            getZookeeper().create("/testCreateAclPath", "acltest".getBytes(), acls, CreateMode.PERSISTENT);
            log.info("创建成功");
            TimeUnit.SECONDS.sleep(5);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("开始重连");
            while(true) {
                log.info("zookeeper.status:{}",getZookeeper().getState().name());
                if(getZookeeper().getState().isConnected()){
                    break;
                }
                TimeUnit.SECONDS.sleep(3);
            }
        }
    }

    /**
     * 测试使用Ip授权方式访问文件
     */
    @Test
    public void testGetACLPath () {
        try {
            byte[] data = getZookeeper().getData("/testCreateAclPath", null, null);
            log.info("/testCreateAclPath 数据是：{}", new String(data));
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
