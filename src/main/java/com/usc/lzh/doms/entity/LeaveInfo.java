package com.usc.lzh.doms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.usc.lzh.doms.utils.ExcelColumn;
import org.springframework.format.annotation.DateTimeFormat;

public class LeaveInfo {
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

    @ExcelColumn(value = "手机号", col = 6)
    private String stutel;

    private Integer got;

    @ExcelColumn(value = "去向", col = 7)
    private String gotValue;

    @ExcelColumn(value = "目的地", col = 8)
    private String dest;

    @ExcelColumn(value = "回校时间", col = 9)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String time;

    @ExcelColumn(value = "紧急联系人", col = 10)
    private String ectel;

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

    public String getStutel() {
        return stutel;
    }

    public void setStutel(String stutel) {
        this.stutel = stutel;
    }

    public Integer getGot() {
        return got;
    }

    public void setGot(Integer got) {
        this.got = got;
    }

    public String getGotValue() {
        return gotValue;
    }

    public void setGotValue(String gotValue) {
        this.gotValue = gotValue;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEctel() {
        return ectel;
    }

    public void setEctel(String ectel) {
        this.ectel = ectel;
    }

    public String getStugrade() {
        return stugrade;
    }

    public void setStugrade(String stugrade) {
        this.stugrade = stugrade;
    }

    @Override
    public String toString() {
        return "LeaveInfo{" +
                "id=" + id +
                ", stuid='" + stuid + '\'' +
                ", stuname='" + stuname + '\'' +
                ", stucollege='" + stucollege + '\'' +
                ", studept='" + studept + '\'' +
                ", stuclass='" + stuclass + '\'' +
                ", stugrade='" + stugrade + '\'' +
                ", stutel='" + stutel + '\'' +
                ", got=" + got +
                ", gotValue=" + gotValue +
                ", dest='" + dest + '\'' +
                ", time='" + time + '\'' +
                ", ectel='" + ectel + '\'' +
                '}';
    }
}
