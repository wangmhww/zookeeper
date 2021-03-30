package com.wm.curator.zookeepercurator;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wangm
 * @title: CuratorBaseOperators
 * @projectName zookeeper-praent
 * @description: TODO
 * @date 2021/3/310:34
 */
@Slf4j
public class CuratorBaseOperators extends CuratorStandaloneBase{

    // 创建递归子节点
    @Test
    public void testCreateWithParent(){
        try {
            CuratorFramework curatorFramework = getCuratorFramework();

            String pathWithParent = "/node-parent/sub-node-1";

            String s = curatorFramework.create().creatingParentsIfNeeded().forPath(pathWithParent);
            log.info("curator create node:{} successfully", s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // protection模式，防止由于异常等原因，导致僵尸节点
    @Test
    public void testCreate() {
        try {
            CuratorFramework curatorFramework = getCuratorFramework();
            String path = curatorFramework.create().withProtection().withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .forPath("/curator-node", "some-data".getBytes());
            log.info("curator create node:{} successfully", path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCPreate() {
        try {
            CuratorFramework curatorFramework = getCuratorFramework();
            String path = curatorFramework.create().withMode(CreateMode.PERSISTENT)
                    .forPath("/curator-node", "some-data".getBytes());
            log.info("curator create node:{} successfully", path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetData () {
        try {
            CuratorFramework curatorFramework = getCuratorFramework();
            byte[] bytes = curatorFramework.getData().forPath("/curator-node");
            log.info("get data from node:{}, successfully" ,new String(bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testSetData(){
        try {
            CuratorFramework curatorFramework = getCuratorFramework();
            curatorFramework.setData().forPath("/curator-node", "change_node".getBytes());
            byte[] bytes = curatorFramework.getData().forPath("/curator-node");
            log.info("修改后的数据：{}", new String(bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDelete(){
        try {
            CuratorFramework curatorFramework = getCuratorFramework();
            curatorFramework.delete().guaranteed().deletingChildrenIfNeeded().forPath("/curator-parent");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testListChildren(){
        try {
            CuratorFramework curatorFramework = getCuratorFramework();
            List<String> strings = curatorFramework.getChildren().forPath("/discovery");
            strings.stream().forEach(item ->{
                log.info(item);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testThreadPool(){
        try {
            CuratorFramework curatorFramework = getCuratorFramework();
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            String zkNode = "/zk_node";
            curatorFramework.getData().inBackground((client, event) ->{
                log.info("background:{}",event);
            }, executorService).forPath(zkNode);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
