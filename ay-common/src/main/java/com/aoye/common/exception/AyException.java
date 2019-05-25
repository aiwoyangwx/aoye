package com.aoye.common.exception;


import com.aoye.common.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author: cuzz
 * @Date: 2018/10/31 19:19
 * @Description: 自定义异常
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AyException extends RuntimeException{
    private ExceptionEnum exceptionEnum;
}