package com.zhanglubin.tms.pojo;

import lombok.Data;
import java.io.Serializable;

@Data
public class Teacher implements Serializable {
    private Long tid;
    private String tname;
}
