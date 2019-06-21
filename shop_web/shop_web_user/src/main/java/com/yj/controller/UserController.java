package com.yj.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.yj.entity.User;
import com.yj.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 29029
 * @version 1.0
 * @time 15:26
 */
@Controller
@RequestMapping("/user")
public class UserController extends IndexController {

    @Reference
    private UserService userService;

    @CrossOrigin
    @RequestMapping(value = "/UpUserFlag/{id}")
    public String upUserFlag(@PathVariable("id")Long id){
        Integer integer = userService.upUserFlag(true,id);
        if (integer > 0){
            return "redirect:/login";
        }
        return "redirect:/";
    }


    @RequestMapping(value = "getByUserName",produces = "text/html;charset=utf8")
    @ResponseBody
    public String getByUserName(String userName){
        User user = userService.getByUserName(userName);
        if (user == null){
            return goBack("true","用户名可用");
        }
        return goBack("false","用户名不可用");
    }

    @RequestMapping(value = "getByEmail",produces = "text/html;charset=utf8")
    @ResponseBody
    public String getByEmail(String email){
        User user = userService.getByEmail(email);
        if (user == null){
            return goBack("true","邮箱可用");
        }
        return goBack("false","邮箱不可用");
    }

    @RequestMapping(value = "getByPhone",produces = "text/html;charset=utf8")
    @ResponseBody
    public String getByPhone(String phone){
        User user = userService.getByPhone(phone);
        if (user == null){
            return goBack("true","手机号可用");
        }
        return goBack("false","手机号不可用");
    }

    @RequestMapping(value = "/toRegister",produces = "text/html;charset=utf8")
    @ResponseBody
    public String toRegister(User user){
        Long aLong = userService.doInsert(user);
        if (aLong != null){
            return goBack("true","注册成功，注册成功会发送激活邮件到您的邮件，请去及时邮箱查收（有效期三分钟）");
            }
        return goBack("false","注册失败");
    }

    @RequestMapping(value = "/toLogin",produces = "text/html;charset=utf8")
    @ResponseBody
    public String toLogin(String userName,String passWord){
        System.out.println(userName + "---" + passWord);
        User user = userService.toLogin(userName, passWord);
        if (user != null){
            if (user.getFlag() == false){
                return goBack("false","登录失败，您还未激活邮箱，请先激活邮箱再来登录");
            }
            return goBack("true","登录成功");
        }
        return goBack("false","登录失败，用户名密码错误");
    }


    /**
     * 用于返回数据各种提示语句
     * @param flag
     * @param msg
     * @return
     */
    public String goBack(String flag, String msg) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("fl", flag);
        map.put("msg", msg);
        return new Gson().toJson(map);
    }
}
