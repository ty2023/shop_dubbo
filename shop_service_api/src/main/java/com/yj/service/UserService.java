package com.yj.service;

import com.yj.base.BaseService;
import com.yj.entity.User;

/**
 * @author 29029
 * @version 1.0
 * @time 11:48
 */
public interface UserService extends BaseService<User> {

    /**
     * 验证用户名唯一
     * @param userName
     * @return
     */
    User getByUserName(String userName);

    /**
     * 验证邮箱唯一
     * @param email
     * @return
     */
    User getByEmail(String email);

    /**
     * 验证联系方式唯一
     * @param phone
     * @return
     */
    User getByPhone(String phone);

    /**
     * 修改用户状态（激活或者不激活）
     * @param flag
     * @return
     */
    Integer upUserFlag(Boolean flag,Long id);

    /**
     * 登录
     * @param userName
     * @param passWord
     * @return
     */
    User toLogin(String userName,String passWord);
}
