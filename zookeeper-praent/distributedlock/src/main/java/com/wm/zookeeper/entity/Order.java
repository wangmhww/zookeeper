package com.wm.zookeeper.entity;

import lombok.*;

/**
 * @author wangm
 * @title: Order
 * @projectName zookeeper-praent
 * @description: TODO
 * @date 2021/4/223:14
 */
@Setter
@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Integer id;

    private Integer pid;

    private String userId;
}
