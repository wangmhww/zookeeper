package com.wm.zookeeper.dao;

import com.wm.zookeeper.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author wangm
 * @title: ProductMapper
 * @projectName zookeeper-praent
 * @description: TODO
 * @date 2021/4/223:08
 */
@Mapper
public interface ProductDao {
    Product getProduct(@Param("id") Integer id);

    int deductStock(@Param("id") Integer id);
}
