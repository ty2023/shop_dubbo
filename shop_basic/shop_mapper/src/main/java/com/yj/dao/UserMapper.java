package com.yj.dao;

import com.yj.base.BaseMapper;
import com.yj.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author 29029
 * @Version 1.0
 * @Time 11:25
 */
public interface UserMapper extends BaseMapper<User> {

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
    Integer upUserFlag(@Param("flag") Boolean flag,@Param("id") Long id);

    /**
     * 登录
     * @param userName
     * @param passWord
     * @return
     */
    User toLogin(@Param("userName") String userName,@Param("passWord") String passWord);


}
