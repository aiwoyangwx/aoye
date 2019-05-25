package com.aoye.product.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "tb_running_machine")
@Data
public class RunningMachine {
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
    private String country;
    private String province;
    private String city;
    private String region;
    private String address;
    //运行里程
    private Integer runtimeMileage;
    //售后到期时间
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date guaranteePeriod;
    //使用寿命到期时间
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date usePeriod;
    //当前版本号
    private String currentVersionCode;
    //当前版本编号
    private Integer currentVersionNum;
    //批次编号
    private String batchNumber;
    //客户级别
    private Integer customerLevel;
}
