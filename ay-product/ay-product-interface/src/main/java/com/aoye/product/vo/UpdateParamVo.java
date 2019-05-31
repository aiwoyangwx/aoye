package com.aoye.product.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

@Data
public class UpdateParamVo {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long cid;
    /**
     * 客户端版本
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private List<Long> iids;
    /**
     * 省份
     */
    private List<Integer> provinces;
}
