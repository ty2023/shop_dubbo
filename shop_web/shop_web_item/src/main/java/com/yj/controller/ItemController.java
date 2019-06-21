package com.yj.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.yj.entity.Product;
import com.yj.listener.RabbitMqListener;
import com.yj.service.ProductService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 29029
 * @version 1.0
 * @time 15:54
 */

@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private Configuration configuration;

    @Autowired
    private ThreadPoolExecutor threadPool;

    @Reference
    private ProductService productService;


    @CrossOrigin
    @RequestMapping("/allProductItem")
    @ResponseBody
    public String allProductItem() throws ExecutionException, InterruptedException {
        List<Product> list = productService.findAll();
        List<Future<Long>> futureList = new ArrayList<>();
        List<Long> errors = new ArrayList<>();
        for (Product product : list) {
            futureList.add(threadPool.submit(new CreateHtmlTask(product)));
        }
        for (Future<Long> future : futureList) {
            Long id = future.get();
            if (id != 0){
                errors.add(id);
            }
        }
        if (errors.size() == 0){
            return goBack("true","生成静态页面成功");
        }
       return goBack("false","有静态页面失败，请查看日志");
    }

    private class CreateHtmlTask implements Callable<Long> {

        private Product product;

        public CreateHtmlTask(Product product){
            this.product = product;
        }

        @Override
        public Long call() throws Exception {
            //静态页面输出的路径 - 输出的静态页面必须能够让外界访问
            String path = RabbitMqListener.class.getResource("/static/html/").getPath() + product.getId() + ".html";
            try (
                    Writer out = new FileWriter(path)
            ){
                //获得商品详情页模板
                Template template = configuration.getTemplate("item.ftl");
                //获得商品的对应数据 - 调用该商品服务查询商品详细信息
                Map map = new HashMap();
                map.put("product",product);
                //生成静态页
                template.process(map, out);
            } catch (Exception e) {
                e.printStackTrace();
                return product.getId();
            }
            return 0L;
        }
    }


    /**
     * 用于返回数据各种提示语句
     * @param flag
     * @param msg
     * @return
     */
    public String goBack(String flag, String msg) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("fl", flag);
        map.put("msg", msg);
        return new Gson().toJson(map);
    }

}

