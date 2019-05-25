package com.aoye.product.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "tb_installer")
@Data
public class Installer {
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
    //版本名称
    private String versionCode;
    //版本序号，用来区别新旧版本
    private Integer versionNum;
    //文件名称
    private String fileName;
    //文件大小（字节）
    private Long fileSize;
    //文件完整性校验码
    private String checkCode;
}
