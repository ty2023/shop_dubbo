package com.yj.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yj.base.BaseMapper;
import com.yj.base.BaseServiceImpl;
import com.yj.dao.ProductTypeMapper;
import com.yj.entity.ProductType;
import com.yj.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author 29029
 * @Version 1.0
 * @Time 12:00
 */
@Service
public class ProductTypeServiceImpl extends BaseServiceImpl<ProductType> implements ProductTypeService {

    @Autowired
    private ProductTypeMapper productTypeMapper;


    @Override
    protected BaseMapper<ProductType> getBaseDao() {
        return productTypeMapper;
    }

    @Override
    public PageInfo<ProductType> page(Integer pageIndex, Integer pageSize) {
        //1.设置分页参数
        PageHelper.startPage(pageIndex,pageSize);
        //2.获取数据
        List<ProductType> list = productTypeMapper.getList();
        //3.构建一个分页对象
        PageInfo<ProductType> pageInfo = new PageInfo<ProductType>(list,10);
        return pageInfo;
    }

    @Override
    public List<ProductType> findType() {
        return productTypeMapper.getList();
    }
}
