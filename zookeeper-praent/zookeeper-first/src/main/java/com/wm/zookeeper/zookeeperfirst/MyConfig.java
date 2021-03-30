package com.wm.zookeeper.zookeeperfirst;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author wangm
 * @title: MyConfig
 * @projectName zookeeper-praent
 * @description: TODO
 * @date 2021/3/300:36
 */
@Data
@ToString
@NoArgsConstructor
public class MyConfig {
    private String key;

    private String name;

}
