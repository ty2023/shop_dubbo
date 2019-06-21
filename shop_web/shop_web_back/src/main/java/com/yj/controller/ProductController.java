package com.yj.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.google.gson.Gson;
import com.yj.entity.Product;
import com.yj.entity.ProductDesc;
import com.yj.service.ProductDescService;
import com.yj.service.ProductService;
import com.yj.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author 29029
 * @Version 1.0
 * @Time 16:19
 */
@Controller
@RequestMapping("product")
public class ProductController extends BaseController {

    @Reference
    private ProductService productService;

    @Reference
    private ProductDescService productDescService;

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Reference
    private ProductTypeService productTypeService;

    /**
     * 分页显示
     * @param pageIndex
     * @param pageSize
     * @param model
     * @return
     */
    @RequestMapping("/page/{pageIndex}/{pageSize}")
    public String page(@PathVariable("pageIndex") Integer pageIndex, @PathVariable("pageSize") Integer pageSize, Model model){
        PageInfo<Product> page = productService.page(pageIndex, pageSize);
        model.addAttribute("page",page);
        return "product/product_Show";
    }

    /**
     * 单个删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delProduct",produces = "text/html;charset=utf8")
    @ResponseBody
    public String delProduct(Long id){
        Integer count = productService.doDelete(id);
        if (count >0){
            return goBack("true", "删除成功");
        }
        return goBack("false", "删除失败");
    }



    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "/batchDel",produces = "text/html;charset=utf8")
    @ResponseBody
    public String batchDel(@RequestParam("ids[]") Long[] ids){
        Integer count = productService.batchDel(ids);
        if (count >0){
            return goBack("true", "批量删除成功");
        }
        return goBack("false", "批量删除失败");
    }

    /**
     * 添加
     * @param product
     * @return
     */
    @RequestMapping(value = "/addProduct",produces = "text/html;charset=utf8")
    @ResponseBody
    public String addProduct(Product product,String proDesc){
        Long Id = productService.doInsert(product);
        Long aLong = productDescService.doInsert(toProductDesc(Id, proDesc));
        if (aLong >0){
            return goBack("true", "添加成功");
        }
        return goBack("false", "添加失败");
    }

    /**
     * TODO 信息回显
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/getByProductId",produces = "text/html;charset=utf8")
    @ResponseBody
    public String getByProductId(Long id,Model model){
        Product product = productService.getById(id);
        ProductDesc productDesc = productDescService.getById(id);
        model.addAttribute("product",product);
        model.addAttribute("productDesc",productDesc);
        return new Gson().toJson(model);
    }

    @RequestMapping(value = "/updateProduct",produces = "text/html;charset=utf8")
    @ResponseBody
    public String updateProduct(Product product,String proDesc,String ImagesOld){
        String imagesNew = product.getProImages();
        if (!ImagesOld.equals(imagesNew)){
            String substring = ImagesOld.substring(23);
            fastFileStorageClient.deleteFile(substring);
        }
        Integer upPro = productService.doUpdate(product);
        Integer upProDesc = productDescService.doUpdate(toProductDesc(product.getId(), proDesc));
        if (upPro > 0 && upProDesc > 0){
            return goBack("true", "修改成功");
        }
        return goBack("false", "修改失败");
    }


    /**
     *
     * TODO 添加商品时，给商品详情表添加数据
     * @param proId
     * @param proDesc
     * @return
     */
    public ProductDesc toProductDesc(Long proId,String proDesc){
        ProductDesc productDesc = new ProductDesc();
        productDesc.setProId(proId);
        productDesc.setProDesc(proDesc);
        return productDesc;
    }



}
