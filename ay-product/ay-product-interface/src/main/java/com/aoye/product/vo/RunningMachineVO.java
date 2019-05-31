package com.aoye.product.vo;

import com.aoye.product.pojo.RunningMachine;
import lombok.Data;

@Data
public class RunningMachineVO extends RunningMachine {
    private String cname;
    private String versionCode;
    private Integer versionNum;
}
