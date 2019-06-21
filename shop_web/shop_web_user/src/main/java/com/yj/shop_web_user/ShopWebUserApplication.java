package com.yj.shop_web_user;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication(scanBasePackages = "com.yj")
public class ShopWebUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopWebUserApplication.class, args);
    }

}
