package com.wm.zookeeper.controller;

import com.wm.zookeeper.config.CuratorCfg;
import com.wm.zookeeper.service.OrderService;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangm
 * @title: OrderController
 * @projectName zookeeper-praent
 * @description: TODO
 * @date 2021/4/30:03
 */
@RestController
public class OrderController {

    @Autowired
    private CuratorFramework curatorFramework;

    @Autowired
    private OrderService orderService;

    @Value("${server.port}")
    private String port ;

    @PostMapping("/stock/deduct")
    public Object reduceStock(Integer id) {
        InterProcessMutex interProcessMutex = new InterProcessMutex(curatorFramework, "/product" + id);
        try {
            interProcessMutex.acquire();
            orderService.reduceStock(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                interProcessMutex.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "ok" + port;
    }
}
