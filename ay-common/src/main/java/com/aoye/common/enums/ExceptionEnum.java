package com.aoye.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
* @Description:    异常枚举类
* @Author:         Alex
* @CreateDate:     2019/5/5
*/
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {
    INCORRECT_PARAMS(400, "参数不正确"),
    INFO_NOT_FOUND(400, "信息没有找到"),
    FAIl_TO_ADD(400,"新增失败"),
    FAIl_TO_DEL(400, "删除失败"),
    FAIl_TO_UPDATE(400,"修改失败" ),
    FILE_NAME_REPETITION(400,"文件名重复"),
    FILE_NOT_FOUND(400,"文件找不到"),
    INSTALLER_REPETITION(400,"当前分类下已有该版本序号");

    private int code;
    private String msg;

}
