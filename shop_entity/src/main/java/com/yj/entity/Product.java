package com.yj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 29029
 * @Version 1.0
 * @Time 10:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {
    /**
     * 商品ID
     */
    private Long id;

    /**
     * 商品名称
     */
    private String proName;

    /**
     * 商品价格
     */
    private Long proPrice;

    /**
     * 销售价格
     */
    private Long proSalePrice;

    /**
     * 商品图片
     */
    private String proImages;

    /**
     * 商品简介
     */
    private String proSalePoint;

    /**
     * 商品类别ID
     */
    private Long proTypeId;

    /**
     * 商品类别名
     */
    private String proTypeName;

    /**
     * 状态
     */
    private Boolean proFlag;

}
