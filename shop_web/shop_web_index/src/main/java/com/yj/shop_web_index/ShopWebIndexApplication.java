package com.yj.shop_web_index;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication(scanBasePackages = "com.yj")
public class ShopWebIndexApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopWebIndexApplication.class, args);
    }

}
