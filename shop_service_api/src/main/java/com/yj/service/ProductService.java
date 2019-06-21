package com.yj.service;

import com.yj.base.BaseService;
import com.yj.entity.Product;

import java.util.List;

/**
 * @author 29029
 * @Version 1.0
 * @Time 11:45
 */
public interface ProductService extends BaseService<Product> {

    /**
     * 批量删除
     * @param ids
     * @return
     */
    Integer batchDel(Long[] ids);

    /**
     * 不分页的查询所有
     * @return
     */
    List<Product> findAll();
}
