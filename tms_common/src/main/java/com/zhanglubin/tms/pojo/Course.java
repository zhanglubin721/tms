package com.zhanglubin.tms.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Course implements Serializable {
    private Long cid;
    private String cname;
    private Long teacher_tid;
    private String teacher_tname;
    private String course_time;
    private int type;
    private int credit;
}
