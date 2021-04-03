package com.wm.zookeeper.service.impl;

import com.wm.zookeeper.dao.OrderDao;
import com.wm.zookeeper.dao.ProductDao;
import com.wm.zookeeper.entity.Order;
import com.wm.zookeeper.entity.Product;
import com.wm.zookeeper.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @author wangm
 * @title: OrderServiceImpl
 * @projectName zookeeper-praent
 * @description: TODO
 * @date 2021/4/223:25
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Override
    @Transactional
    public void reduceStock(Integer id) {
        try {
            Product product = productDao.getProduct(id);
            Thread.sleep(500);

            if(product.getStock() <= 0) {
                throw new RuntimeException("out of stock");
            }

            log.info("product.getStock: {}", product.getStock());
            int i = productDao.deductStock(id);

            if (i == 1) {
               Order order = new Order();
               order.setUserId(UUID.randomUUID().toString());
               order.setPid(id);
               orderDao.insertOrder(order);
            } else {
                throw new RuntimeException("dcduct stock fail retry.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
