package com.aoye.product.pojo;

import lombok.Data;

import javax.persistence.Table;

@Table(name = "tb_provinces")
@Data
public class Province {
    /**
     * 省份编号
     */
    private Integer provinceId;
    /**
     *省份名称
     */
    private String provinceName;
    /**
     *排序
     */
    private Integer sort;
}
