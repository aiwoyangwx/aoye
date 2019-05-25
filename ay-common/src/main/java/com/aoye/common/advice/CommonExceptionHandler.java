package com.aoye.common.advice;


import com.aoye.common.exception.AyException;
import com.aoye.common.vo.ExceptionResult;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @Author: cuzz
 * @Date: 2018/10/31 19:06
 * @Description: 拦截异常
 */
@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(value = AyException.class)
    public ResponseEntity<ExceptionResult> handleException(AyException e) {
        val em = e.getExceptionEnum();
        return ResponseEntity.status(em.getCode()).body(new ExceptionResult(em));
    }
}
