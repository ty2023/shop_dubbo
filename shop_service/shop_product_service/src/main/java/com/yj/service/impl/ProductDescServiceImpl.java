package com.yj.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yj.base.BaseMapper;
import com.yj.base.BaseServiceImpl;
import com.yj.dao.ProductDescMapper;
import com.yj.entity.ProductDesc;
import com.yj.service.ProductDescService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author 29029
 * @Version 1.0
 * @Time 12:00
 */
@Service
public class ProductDescServiceImpl extends BaseServiceImpl<ProductDesc> implements ProductDescService {

    @Autowired
    private ProductDescMapper productDescMapper;

    @Override
    protected BaseMapper<ProductDesc> getBaseDao() {
        return productDescMapper;
    }

    @Override
    public PageInfo<ProductDesc> page(Integer pageIndex, Integer pageSize) {
        //1.设置分页参数
        PageHelper.startPage(pageIndex,pageSize);
        //2.获取数据
        List<ProductDesc> list = productDescMapper.getList();
        //3.构建一个分页对象
        PageInfo<ProductDesc> pageInfo = new PageInfo<ProductDesc>(list,10);
        return pageInfo;
    }
}
