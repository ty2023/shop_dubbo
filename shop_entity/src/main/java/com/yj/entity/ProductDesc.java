package com.yj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 29029
 * @Version 1.0
 * @Time 11:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDesc implements Serializable {
    /**
     * 描述名称
     */
    private Long proDescId;

    /**
     *商品ID
     */
    private Long proId;

    /**
     * 商品描述（大部分为图片）
     */
    private String proDesc;
}
