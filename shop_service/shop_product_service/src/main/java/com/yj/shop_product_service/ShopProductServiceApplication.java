package com.yj.shop_product_service;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 29029
 */
@MapperScan("com.yj.dao")
@DubboComponentScan("com.yj.service.impl")
@SpringBootApplication(scanBasePackages = "com.yj")
public class ShopProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopProductServiceApplication.class, args);
    }

}
