package com.yj.service;

import com.yj.entity.Product;
import com.yj.pojo.PageResultBean;
import com.yj.pojo.ResultBean;

/**
 * @author 29029
 * @Version 1.0
 * @Time 16:31
 */

public interface SearchService {

    /**
     * 查询并分页
     * @return
     */
    PageResultBean<Product> queryByKeyWord(PageResultBean<Product> page);

    /**
     * 同步数据库的所有数据
     * @return
     */
    ResultBean synAllData();
}
