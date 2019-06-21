package com.yj.shop_web_item;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 29029
 */
@EnableDubbo
@SpringBootApplication(scanBasePackages = "com.yj")
public class ShopWebItemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopWebItemApplication.class, args);
    }

}
