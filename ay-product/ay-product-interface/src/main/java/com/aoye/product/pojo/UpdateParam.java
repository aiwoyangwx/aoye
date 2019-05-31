package com.aoye.product.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "tb_update_param")
@Data
public class UpdateParam {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long cid;
    /**
     * 客户端版本
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long iid;
    /**
     * 国家
     */
    private String country;
    /**
     * 省份
     */
    private Integer provinceId;
    /**
     * 城市
     */
    private String city;
    /**
     * 地区
     */
    private String region;
    /**
     * 批次编号
     */
    private String batchNumber;
    /**
     * 客户级别
     */
    private Integer customerLevel;
}
