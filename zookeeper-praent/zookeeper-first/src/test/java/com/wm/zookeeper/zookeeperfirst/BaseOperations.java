package com.wm.zookeeper.zookeeperfirst;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

/**
 * @author wangm
 * @title: BaseOperations
 * @projectName zookeeper-praent
 * @description: TODO
 * @date 2021/3/3023:42
 */
@Slf4j
public class BaseOperations extends StandaloneBase {

    private String first_node = "/first_node";

    @Test
    public void testCreate(){
        try {
            ZooKeeper zooKeeper = getZookeeper();

            zooKeeper.create(first_node, "first".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            log.info("create:{}");
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSetDate(){
        try {
            ZooKeeper zooKeeper = getZookeeper();
            Stat stat = new Stat();

            byte[] data = zooKeeper.getData(first_node, null, stat);
            log.info("获取到的数据：{} 版本号：{}", new String(data), stat.getAversion());
            int version = stat.getVersion();
            zooKeeper.setData(first_node, "third".getBytes(), version);
            Stat stat1 = new Stat();
            byte[] data1 = zooKeeper.getData(first_node, null, stat1);
            log.info("修改后的数据：{},版本号是：{}",new String(data1), stat1.getVersion());

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDelete(){
        try {
            ZooKeeper zookeeper = getZookeeper();
            // -1代表所有版本，直接删除
            // 任意大于-1的代表可以指定数据版本删除
            zookeeper.delete("/config", -1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void asyncTest(){
        getZookeeper().getData(first_node, false, (rc, path, ctx, data, stat) -> {
            Thread thread = Thread.currentThread();
            log.info("Thread name:{} rc:{},path:{}, ctx:{}, data:{}, stat: {}",thread.getName(), rc, path, ctx, data, stat);
        }, "test");

        log.info("over");
    }
}

