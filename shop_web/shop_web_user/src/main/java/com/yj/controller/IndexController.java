package com.yj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 29029
 * @version 1.0
 * @time 17:13
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public String Index(){
        return "redirect:http://localhost:8081/";
    }

    @RequestMapping("/{page}")
    public String Page(@PathVariable("page")String page){
        return page;
    }
}
