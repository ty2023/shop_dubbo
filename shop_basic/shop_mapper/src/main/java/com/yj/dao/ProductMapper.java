package com.yj.dao;

import com.yj.base.BaseMapper;
import com.yj.entity.Product;
import org.apache.ibatis.annotations.Param;

/**
 * @author 29029
 * @Version 1.0
 * @Time 11:08
 */
public interface ProductMapper extends BaseMapper<Product> {

    /**
     * 批量删除
     * @param ids
     * @return
     */
    Integer batchDel(@Param("ids")Long[] ids);
}
