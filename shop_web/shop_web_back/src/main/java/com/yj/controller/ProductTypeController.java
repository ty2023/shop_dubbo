package com.yj.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.yj.entity.ProductType;
import com.yj.service.ProductTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 29029
 * @Version 1.0
 * @Time 11:43
 */
@Controller
@RequestMapping("productType")
public class ProductTypeController extends BaseController {

    @Reference
    private ProductTypeService productTypeService;

    /**
     * 查询所有商品类别
     * @param map
     * @return
     */
    @RequestMapping(value = "/findType",produces = "text/html;charset=utf8")
    @ResponseBody
    public String findType(ModelMap map){
        List<ProductType> type = productTypeService.findType();
        map.put("typeList",type);
        return new Gson().toJson(map);
    }
}
