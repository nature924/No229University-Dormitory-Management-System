package com.usc.lzh.doms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.usc.lzh.doms.utils.ExcelColumn;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author:lzh
 * @time:2021.4.9
 * 报修信息
 */
public class RepairInfo {
    private Integer id;

    private String stuid;

    @ExcelColumn(value = "学生姓名",col = 2)
    private String stuname;

    @ExcelColumn(value = "联系方式",col = 3)
    private String stutel;

    private String brcode;

    @ExcelColumn(value = "宿舍区",col = 4)
    private String brarea;

    @ExcelColumn(value = "楼栋",col = 5)
    private String brbid;

    @ExcelColumn(value = "寝室",col = 6)
    private String brrid;

    @ExcelColumn(value = "报修类型",col = 7)
    private String type;

    @ExcelColumn(value = "描述",col = 8)
    private String content;

    @ExcelColumn(value = "维修状态",col = 9)
    private String status;

    @ExcelColumn(value = "提交时间",col = 1)
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String subtime;

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

    public String getStutel() {
        return stutel;
    }

    public void setStutel(String stutel) {
        this.stutel = stutel;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubtime() {
        return subtime;
    }

    public void setSubtime(String subtime) {
        this.subtime = subtime;
    }

    @Override
    public String toString() {
        return "RepairInfo{" +
                "id=" + id +
                ", stuid='" + stuid + '\'' +
                ", stuname='" + stuname + '\'' +
                ", stutel='" + stutel + '\'' +
                ", brcode='" + brcode + '\'' +
                ", brarea='" + brarea + '\'' +
                ", brbid='" + brbid + '\'' +
                ", brrid='" + brrid + '\'' +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", status='" + status + '\'' +
                ", subtime='" + subtime + '\'' +
                '}';
    }
}
