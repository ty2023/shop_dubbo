package com.yj.controller;

import com.yj.entity.ProductType;
import com.yj.service.ProductTypeService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author 29029
 * @Version 1.0
 * @Time 19:04
 */
@Controller
public class IndexController {

    @Reference
    private ProductTypeService productTypeService;

    @RequestMapping("/")
    public String showHome(Model model){
        //1.调用远程服务，获取商品类别的数据
        List<ProductType> list = productTypeService.findType();
        //2.将数据保存起来，到前端页面展示
        model.addAttribute("list",list);
        return "index";
    }

}
