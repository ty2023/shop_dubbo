package com.yj.listener;

import com.yj.constant.RabbitMqConstant;
import com.yj.entity.Product;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 29029
 * @Version 1.0
 * @Time 9:52
 */
@Component
public class RabbitMqListener {

    @Autowired
    private Configuration configuration;

    /**
     * 用户商品添加
     * @param product
     */
    @RabbitListener(queues = RabbitMqConstant.ITEM_QUEUE)
    public void productMsgHander(Product product){
       CreatePage("item.ftl",product);
    }

    /**
     * 用于商品下架
     * @param list
     */
    @RabbitListener(queues = RabbitMqConstant.ITEM_DEL_QUEUE)
    public void productDelHander(List<Product> list){
        for (Product product : list) {
            CreatePage("item2.ftl",product);
        }
    }

    public void CreatePage(String model,Product product){
        //静态页面输出的路径 - 输出的静态页面必须能够让外界访问
        String path = RabbitMqListener.class.getResource("/static/html/").getPath() + product.getId() + ".html";
        try (
                Writer out = new FileWriter(path)
        ){
            //获得商品详情页模板
            Template template = configuration.getTemplate(model);
            //获得商品的对应数据 - 调用该商品服务查询商品详细信息
            Map map = new HashMap();
            map.put("product",product);
            //生成静态页
            template.process(map, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
