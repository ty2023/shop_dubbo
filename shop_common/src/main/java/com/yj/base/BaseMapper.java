package com.yj.base;

import java.util.List;

/**
 * @author 29029
 * @Version 1.0
 * @Time 14:01
 */
public interface BaseMapper<T> {

    /**
     * 查询所有
     * @return
     */
    List<T> getList();

    /**
     * 删除对象
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
     * 修改某个信息
     * @param t
     * @return
     */
    Integer doUpdate(T t);

    /**
     * 添加信息
     * @param t
     * @return
     */
    Long doInsert(T t);
}
