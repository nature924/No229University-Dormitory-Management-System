package com.usc.lzh.doms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.usc.lzh.doms.utils.ExcelColumn;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author:lzh
 * @time:2021.4.9 卫生检查信息
 */
public class CleanInfo {
    private Integer id;
    private String checker;
    private String uid;
    private String brcode;
    private String brarea;
    private String brbid;
    private String brrid;
    private String content;
    private Integer grade;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getBrcode() {
        return brcode;
    }

    public void setBrcode(String brcode) {
        this.brcode = brcode;
    }

    public String getBrarea() {
        return brarea;
    }

    public void setBrarea(String brarea) {
        this.brarea = brarea;
    }

    public String getBrbid() {
        return brbid;
    }

    public void setBrbid(String brbid) {
        this.brbid = brbid;
    }

    public String getBrrid() {
        return brrid;
    }

    public void setBrrid(String brrid) {
        this.brrid = brrid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "CleanInfo{" +
                "id=" + id +
                ", checker='" + checker + '\'' +
                ", uid='" + uid + '\'' +
                ", brcode='" + brcode + '\'' +
                ", brarea='" + brarea + '\'' +
                ", brbid='" + brbid + '\'' +
                ", brrid='" + brrid + '\'' +
                ", content='" + content + '\'' +
                ", grade=" + grade +
                ", time='" + time + '\'' +
                '}';
    }
}
