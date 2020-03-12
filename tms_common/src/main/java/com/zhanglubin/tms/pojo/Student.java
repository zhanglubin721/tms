package com.zhanglubin.tms.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Student implements Serializable {
    private long sid;
    private String gender;
    private String class_id;
    private String sname;
    private String idNumber;
}
