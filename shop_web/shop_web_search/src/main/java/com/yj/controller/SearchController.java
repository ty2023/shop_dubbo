package com.yj.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.yj.entity.Product;
import com.yj.pojo.PageResultBean;
import com.yj.pojo.ResultBean;
import com.yj.service.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 29029
 * @Version 1.0
 * @Time 15:49
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @Reference
    private SearchService searchService;

    @RequestMapping("/searchByKeyWord")
    public String getKeyWord(PageResultBean<Product> page, Model model){
        PageResultBean<Product> pageBean = searchService.queryByKeyWord(page);
        System.out.println(page.getKeyWord());
        //关键字在页面中的回显
        model.addAttribute("keyWord",pageBean.getKeyWord());
        //商品集合
        model.addAttribute("productList",pageBean.getList());
        //分页的相关信息
        model.addAttribute("page",pageBean);
        return "product_List";
    }


    @CrossOrigin
    @RequestMapping("/AllDataSolr")
    @ResponseBody
    public String AllDataSolr(){
        ResultBean resultBean = searchService.synAllData();
        return goBack(resultBean.getStatusCode(),resultBean.getData()+"");
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
