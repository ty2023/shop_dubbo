package com.yj.base;

/**
 * @author 29029
 * @Version 1.0
 * @Time 14:00
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    public BaseServiceImpl() {
    }

    protected abstract BaseMapper<T> getBaseDao();



    @Override
    public Integer doDelete(Long id) {
        return getBaseDao().doDelete(id);
    }

    @Override
    public T getById(Long id) {
        return getBaseDao().getById(id);
    }

    @Override
    public Integer doUpdate(T t) {
        return getBaseDao().doUpdate(t);
    }

    @Override
    public Long doInsert(T t) {
        return getBaseDao().doInsert(t);
    }
}
