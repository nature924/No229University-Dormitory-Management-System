package com.usc.lzh.doms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.usc.lzh.doms.utils.ExcelColumn;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Arrays;

public class StayInfo {
    private Integer id;

    @ExcelColumn(value = "学号", col = 1)
    private String stuid;

    @ExcelColumn(value = "姓名", col = 2)
    private String stuname;

    @ExcelColumn(value = "学院", col = 3)
    private String stucollege;

    @ExcelColumn(value = "专业", col = 4)
    private String studept;

    @ExcelColumn(value = "班级", col = 5)
    private String stuclass;

    private String stugrade;

    private String brcode;

    @ExcelColumn(value = "宿舍区", col = 6)
    private String brarea;

    @ExcelColumn(value = "楼栋", col = 7)
    private String brbid;

    @ExcelColumn(value = "寝室", col = 8)
    private String brrid;

    @ExcelColumn(value = "留校原因", col = 9)
    private String reason;

    @ExcelColumn(value = "留校截止时间", col = 10)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String time;

    private String opinion;

    @ExcelColumn(value = "手机号", col = 11)
    private String stutel;

    @ExcelColumn(value = "家长姓名", col = 12)
    private String stututorname;

    @ExcelColumn(value = "家长联系方式", col = 13)
    private String stututortel;

    private String approvetext;

    private Integer approvetype;

    private String approveValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStuid() {
        return stuid;
    }

    public void setStuid(String stuid) {
        this.stuid = stuid;
    }

    public String getStuname() {
        return stuname;
    }

    public void setStuname(String stuname) {
        this.stuname = stuname;
    }

    public String getStucollege() {
        return stucollege;
    }

    public void setStucollege(String stucollege) {
        this.stucollege = stucollege;
    }

    public String getStudept() {
        return studept;
    }

    public void setStudept(String studept) {
        this.studept = studept;
    }

    public String getStuclass() {
        return stuclass;
    }

    public void setStuclass(String stuclass) {
        this.stuclass = stuclass;
    }

    public String getStugrade() {
        return stugrade;
    }

    public void setStugrade(String stugrade) {
        this.stugrade = stugrade;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public String getStutel() {
        return stutel;
    }

    public void setStutel(String stutel) {
        this.stutel = stutel;
    }

    public String getStututorname() {
        return stututorname;
    }

    public void setStututorname(String stututorname) {
        this.stututorname = stututorname;
    }

    public String getStututortel() {
        return stututortel;
    }

    public void setStututortel(String stututortel) {
        this.stututortel = stututortel;
    }

    public String getApprovetext() {
        return approvetext;
    }

    public void setApprovetext(String approvetext) {
        this.approvetext = approvetext;
    }

    public Integer getApprovetype() {
        return approvetype;
    }

    public void setApprovetype(Integer approvetype) {
        this.approvetype = approvetype;
    }

    public String getApproveValue() {
        return approveValue;
    }

    public void setApproveValue(String approveValue) {
        this.approveValue = approveValue;
    }

    @Override
    public String toString() {
        return "StayInfo{" +
                "id=" + id +
                ", stuid='" + stuid + '\'' +
                ", stuname='" + stuname + '\'' +
                ", stucollege='" + stucollege + '\'' +
                ", studept='" + studept + '\'' +
                ", stuclass='" + stuclass + '\'' +
                ", stugrade='" + stugrade + '\'' +
                ", brcode='" + brcode + '\'' +
                ", brarea='" + brarea + '\'' +
                ", brbid='" + brbid + '\'' +
                ", brrid='" + brrid + '\'' +
                ", reason='" + reason + '\'' +
                ", time='" + time + '\'' +
                ", stutel='" + stutel + '\'' +
                ", stututorname='" + stututorname + '\'' +
                ", stututortel='" + stututortel + '\'' +
                ", approvetext='" + approvetext + '\'' +
                ", approvetype=" + approvetype + '\'' +
                ", approveValue='" + approveValue +
                '}';
    }
}
