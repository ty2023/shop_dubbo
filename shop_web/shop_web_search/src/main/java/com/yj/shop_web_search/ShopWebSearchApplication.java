package com.yj.shop_web_search;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.yj")
@EnableDubbo
public class ShopWebSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopWebSearchApplication.class, args);
    }

}
