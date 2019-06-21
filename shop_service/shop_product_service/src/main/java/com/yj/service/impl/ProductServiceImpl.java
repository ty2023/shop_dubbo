package com.yj.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yj.base.BaseMapper;
import com.yj.base.BaseServiceImpl;
import com.yj.constant.RabbitMqConstant;
import com.yj.dao.ProductMapper;
import com.yj.entity.Product;
import com.yj.service.ProductService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 29029
 * @Version 1.0
 * @Time 11:35
 */
@Service
public class ProductServiceImpl extends BaseServiceImpl<Product> implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Override
    protected BaseMapper<Product> getBaseDao() {
        return productMapper;
    }

    @Override
    public PageInfo<Product> page(Integer pageIndex, Integer pageSize) {
        //1.设置分页参数
        PageHelper.startPage(pageIndex,pageSize);
        //2.获取数据
        List<Product> list = productMapper.getList();
        //3.构建一个分页对象
        PageInfo<Product> pageInfo = new PageInfo<Product>(list,10);
        return pageInfo;
    }

    @Override
    public Integer batchDel(Long[] ids) {
        List<Product> list = new ArrayList<>();
        for (Long id : ids) {
            Product product = productMapper.getById(id);
            list.add(product);
        }
        rabbitTemplate.convertAndSend(RabbitMqConstant.PRODUCT_FANOUT_DEL_EXCHANGE, "", list);
        return productMapper.batchDel(ids);
    }

    @Override
    public List<Product> findAll() {
        return productMapper.getList();
    }

    @Override
    public Integer doDelete(Long id) {
        Product product = productMapper.getById(id);
        Integer integer = productMapper.doDelete(id);
        List<Product> list = new ArrayList<>();
        list.add(product);
        rabbitTemplate.convertAndSend(RabbitMqConstant.PRODUCT_FANOUT_DEL_EXCHANGE, "", list);
        return integer;
    }

    @Override
    public Long doInsert(Product product) {
        Long aLong = productMapper.doInsert(product);
        product.setId(aLong);
        //给RabbitMq路由器添加数据
        rabbitTemplate.convertAndSend(RabbitMqConstant.PRODUCT_FANOUT_EXCHANGE, "", product);
        return aLong;
    }

    @Override
    public Integer doUpdate(Product product){
        Integer integer = productMapper.doUpdate(product);
        rabbitTemplate.convertAndSend(RabbitMqConstant.PRODUCT_FANOUT_EXCHANGE, "", product);
        return integer;
    }
}
