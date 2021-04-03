package com.wm.zookeeper.dao;

import com.wm.zookeeper.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author wangm
 * @title: OrderDao
 * @projectName zookeeper-praent
 * @description: TODO
 * @date 2021/4/223:19
 */
@Mapper
public interface OrderDao {

    void insertOrder(@Param("order") Order order);

}
