package com.wm.zookeeper.productcenter.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangm
 * @title: ProductController
 * @projectName zookeeper-praent
 * @description: TODO
 * @date 2021/4/323:52
 */
@RestController
public class ProductController {

    @Value("${server.port}")
    private String port;

    @Value("product-center")
    private String name;

    @GetMapping("/getInfo")
    public String getServerPortAndName () {
        return this.name + ":" + this.port;
    }
}
