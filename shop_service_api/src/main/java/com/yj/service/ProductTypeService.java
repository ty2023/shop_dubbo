package com.yj.service;

import com.yj.base.BaseService;
import com.yj.entity.ProductType;

import java.util.List;

/**
 * @author 29029
 * @Version 1.0
 * @Time 11:59
 */

public interface ProductTypeService extends BaseService<ProductType> {

    /**
     * 查询所有类别
     * @return
     */
    List<ProductType> findType();
}
