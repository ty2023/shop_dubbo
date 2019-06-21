package com.yj.listener;


import com.yj.constant.RabbitMqConstant;
import com.yj.entity.Product;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @author 29029
 * @Version 1.0
 * @Time 17:53
 */
@Component
public class RabbitMqListener {

    @Autowired
    private SolrClient solrClient;

    /**
     * 通过消息中间件RabbitMQ添加到索引库
     * @param product
     */
    @RabbitListener(queues = RabbitMqConstant.SEARCH_QUEUE)
    public void productSearch(Product product){
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", product.getId() + "");
        document.addField("product_name", product.getProName());
        document.addField("product_sale_point", product.getProSalePoint());
        document.addField("product_images", product.getProImages());
        document.addField("product_price", product.getProPrice());
        document.addField("product_sale_price",product.getProSalePrice());
        try {
            solrClient.add(document);
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = RabbitMqConstant.SEARCH_DEL_QUEUE)
    public void productDelSearch(List<Product> list){
        try {
            for (Product product : list) {
                solrClient.deleteById(product.getId()+"");
            }
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
