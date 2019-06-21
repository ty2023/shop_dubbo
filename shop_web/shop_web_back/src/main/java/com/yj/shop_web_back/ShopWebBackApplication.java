package com.yj.shop_web_back;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author 29029
 */
@EnableDubbo
@SpringBootApplication(scanBasePackages = "com.yj")
@Import(FdfsClientConfig.class)
public class ShopWebBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopWebBackApplication.class, args);
    }

}
