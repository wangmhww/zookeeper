package com.wm.zookeeper;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wm.zookeeper.dao")
public class DistributedlockApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistributedlockApplication.class, args);
    }

}
