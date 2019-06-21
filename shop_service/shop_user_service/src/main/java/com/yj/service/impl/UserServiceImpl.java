package com.yj.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yj.base.BaseMapper;
import com.yj.base.BaseServiceImpl;
import com.yj.constant.RabbitMqConstant;
import com.yj.dao.UserMapper;
import com.yj.entity.User;
import com.yj.service.UserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author 29029
 * @version 1.0
 * @time 11:50
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    protected BaseMapper<User> getBaseDao() {
        return userMapper;
    }

    @Override
    public Long doInsert(User user){
        Long id = userMapper.doInsert(user);
        user.setId(id);
        System.out.println(user);
        rabbitTemplate.convertAndSend(RabbitMqConstant.USER_EXCHANGE, "", user);
        return id;
    }

    @Override
    public User getByUserName(String userName) {
        return userMapper.getByUserName(userName);
    }

    @Override
    public User getByEmail(String email) {
        return userMapper.getByEmail(email);
    }

    @Override
    public User getByPhone(String phone) {
        return userMapper.getByPhone(phone);
    }

    @Override
    public Integer upUserFlag(Boolean flag,Long id) {
        Integer integer = userMapper.upUserFlag(flag, id);
        if (integer == 0) {
            userMapper.doDelete(id);
        }
        return integer;
    }

    @Override
    public User toLogin(String userName, String passWord) {
        return userMapper.toLogin(userName,passWord);
    }

    @Override
    public PageInfo<User> page(Integer pageIndex, Integer pageSize) {
        //1.设置分页参数
        PageHelper.startPage(pageIndex,pageSize);
        //2.获取数据
        List<User> list = userMapper.getList();
        //3.构建一个分页对象
        PageInfo<User> pageInfo = new PageInfo<User>(list,10);
        return pageInfo;
    }
}
