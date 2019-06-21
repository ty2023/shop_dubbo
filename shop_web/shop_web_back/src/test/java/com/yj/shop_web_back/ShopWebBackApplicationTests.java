package com.yj.shop_web_back;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopWebBackApplicationTests {

    @Test
    public void contextLoads() {
        String path = "http://192.168.135.130/group1/M00/00/02/wKiHgl0Jl_2AWYSnAATM0zXRHTI684.jpg";
        String substring = path.substring(23);
        System.out.println(substring);
    }

}
