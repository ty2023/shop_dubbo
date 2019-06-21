package com.yj.pojo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 29029
 * @version 1.0
 * @time 17:42
 */
@Configuration
public class CommonConfig {

    @Bean
    public ThreadPoolExecutor createPools (){
        //获得本机CPU核心数
        int cpu = Runtime.getRuntime().availableProcessors();
        //根据CPU设置线程最小数以及最大数
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                cpu,cpu*2,10, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(100));

        return pool;
    }

}
