package com.wm.zookeeper.zookeeperfirst;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author wangm
 * @title: ConfigCenter
 * @projectName zookeeper-praent
 * @description: TODO
 * @date 2021/3/300:17
 */
@Slf4j
public class ConfigCenter {
    private final static String CONNECT_STR = "47.110.71.90:2181";

    private final static Integer SESSION_TIMEOUT = 30*1000;

    private static ZooKeeper zooKeeper = null;

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {

        zooKeeper = new ZooKeeper(CONNECT_STR, SESSION_TIMEOUT, watchedEvent -> {
            if(watchedEvent.getType() == Watcher.Event.EventType.None
                    && watchedEvent.getState() == Watcher.Event.KeeperState.SyncConnected){
                log.info("连接已建立");
//                System.out.println("");
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();

        MyConfig myConfig = new MyConfig();
        myConfig.setKey("key");
        myConfig.setName("wangming");
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes = objectMapper.writeValueAsBytes(myConfig);

        zooKeeper.create("/myConfig", bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        Watcher watcher = new Watcher() {
            @SneakyThrows
            @Override
            public void process(WatchedEvent event) {
                if (event.getType() == Event.EventType.NodeDataChanged
                 && event.getPath().equals("/myConfig")) {
                    System.out.println("数据发生变化");

                    byte [] newData = zooKeeper.getData("/myConfig", this, null);
                    System.out.println("数据发生变化");
                    MyConfig myConfig1 = objectMapper.readValue(new String(newData), MyConfig.class);

                    System.out.println("新数据"+ myConfig1);
                }
            }
        };
        
        byte[] oldData = zooKeeper.getData("/myConfig", watcher, null);
        MyConfig myConfig1 = objectMapper.readValue(new String(oldData), MyConfig.class);
        System.out.println("原始数据"+ myConfig1);

        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }
}
