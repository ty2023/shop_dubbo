package com.yj.controller;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.google.gson.Gson;
import com.yj.generalbean.UploadBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 29029
 * @Version 1.0
 * @Time 19:03
 */
@Controller
public class BaseController {

    /**
     * 图片上传
     */
    @Autowired
    private FastFileStorageClient fastFileStorageClient;


    @Value("${img.server}")
    private String imageServer;


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


    /**
     * 用于跳转页面
     * @param page
     * @return
     */
    @RequestMapping("/{page}")
    public String toPage(@PathVariable("page") String page){
        return page;
    }


    /**
     * 单个图片上传
     * @param file
     * @return
     */
    @RequestMapping(value = "/uploadImg",produces = "text/html;charset=utf8")
    @ResponseBody
    public String uploadImg(MultipartFile file) {
        //获得上传的图片名称
        String fname = file.getOriginalFilename();
        //获得上传的图片大小
        long flength = file.getSize();
        //截取后缀
        int index = fname.lastIndexOf(".");
        String houzui = fname.substring(index + 1);
        //上传图片到FastDFS上
        try {
            StorePath storePath = fastFileStorageClient.uploadImageAndCrtThumbImage(
                    file.getInputStream(),
                    flength,
                    houzui,
                    null
            );
            String path = new StringBuilder(imageServer).append(storePath.getFullPath()).toString();
            return goBack("true",path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return goBack("false","上传失败");
    }


    /**
     *批量文件上传
     * @param files
     *          图片文件数组
     * @return
     */
    @RequestMapping(value = "/uploadImgList")
    @ResponseBody
    public UploadBean uploadList(MultipartFile[] files){
        String[] data =new String[files.length];
        for (int i = 0; i < files.length; i++) {
            //获得上传的图片名称
            String fname = files[i].getOriginalFilename();
            //获得上传的图片大小
            long flength = files[i].getSize();
            //截取后缀
            int index = fname.lastIndexOf(".");
            String houzui = fname.substring(index + 1);
            //上传图片到FastDFS上
            try {
                StorePath storePath = fastFileStorageClient.uploadImageAndCrtThumbImage(
                        files[i].getInputStream(),
                        flength,
                        houzui,
                        null
                );
                String path = new StringBuilder(imageServer).append(storePath.getFullPath()).toString();
                data[i] = path;
            } catch (IOException e) {
                e.printStackTrace();
                return new UploadBean("1",null);
            }
        }
        return new UploadBean("0",data);
    }

}
