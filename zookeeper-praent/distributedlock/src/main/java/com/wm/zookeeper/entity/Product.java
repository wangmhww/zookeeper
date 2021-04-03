package com.wm.zookeeper.entity;

import lombok.*;

/**
 * @author wangm
 * @title: Product
 * @projectName zookeeper-praent
 * @description: TODO
 * @date 2021/4/223:13
 */
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private Integer id;

    private String productName;

    private Integer stock;

    private Integer version;
}
