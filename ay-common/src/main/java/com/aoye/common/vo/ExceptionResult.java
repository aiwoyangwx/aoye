package com.aoye.common.vo;

import com.aoye.common.enums.ExceptionEnum;
import lombok.Data;

/**
* @Description:    作用描述
* @Author:         Alex
* @CreateDate:     2019/5/5
*/
@Data
public class ExceptionResult {
    private int status;
    private String message;
    private Long timestamp;

    public ExceptionResult(ExceptionEnum exceptionEnum) {
        this.status = exceptionEnum.getCode();
        this.message = exceptionEnum.getMsg();
        this.timestamp = System.currentTimeMillis();
    }
}
