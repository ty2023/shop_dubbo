package com.yj.base;

import com.github.pagehelper.PageInfo;

/**
 * @author 29029
 * @Version 1.0
 * @Time 14:00
 */
public interface BaseService<T> {
    /**
     * 分页查询
     * @param pageIndex
     * @param pageSize
     * @return
     */
    PageInfo<T> page(Integer pageIndex, Integer pageSize);

    /**
     * 公共删除
     * @param id
     * @return
     */
    Integer doDelete(Long id);

    /**
     * 查询单个信息
     * @param id
     * @return
     */
    T getById(Long id);

    /**
     * 修改某条信息
     * @param t
     */
    Integer doUpdate(T t);

    /**
     * 添加数据
     * @param t
     * @return
     */
    Long doInsert(T t);
}
