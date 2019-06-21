package com.yj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 29029
 * @Version 1.0
 * @Time 10:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductType implements Serializable {
    /**
     * 类别ID
     */
    private Long typeId;

    /**
     * 类别名
     */
    private String typeName;

    /**
     * 上级类别ID
     */
    private Long parentTypeId;

    /**
     * 上级类别名
     */
    private String parentTypeName;

    /**
     * 类别状态
     */
    private Boolean typeFlag;

}

