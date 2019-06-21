package com.yj.shop_search_service;

import com.yj.entity.Product;
import com.yj.pojo.PageResultBean;
import com.yj.pojo.ResultBean;
import com.yj.service.SearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopSearchServiceApplicationTests {

    @Autowired
    private SolrClient solrClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private SearchService searchService;

    @Test
    public void contextLoads() {
        PageResultBean<Product> pageResultBean=new PageResultBean<>();
        PageResultBean<Product> pageResultBean1 = searchService.queryByKeyWord(pageResultBean);
        List<Product> products = pageResultBean1.getList();
        if (products == null){
            System.out.println("这是空的");
        }
        for (Product product : products) {
            System.err.println(product.getId()+"->"+product.getProName());
        }
    }

    @Test
    public void rabbitTest(){
        ResultBean resultBean = searchService.synAllData();
        System.out.println(resultBean.getData()+"---"+resultBean.getStatusCode());
    }


}


